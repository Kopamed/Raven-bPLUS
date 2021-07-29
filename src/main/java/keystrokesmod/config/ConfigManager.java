package keystrokesmod.config;

import keystrokesmod.main.NotAName;
import keystrokesmod.module.Module;
import keystrokesmod.module.modules.HUD;
import net.minecraft.client.Minecraft;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ConfigManager {
    private File configDirecotry;
    private File currentConfig;
    private String fileName;
    private String extension;

    public ConfigManager() {
        configDirecotry = new File(Minecraft.getMinecraft().mcDataDir, "keystrokes" + File.separator + "configs");
        if (!configDirecotry.exists()) {
            configDirecotry.mkdir();
        }
        this.fileName = "default";
        this.extension = "bplus";
        currentConfig = new File(configDirecotry, fileName + "." + extension);
        if (!currentConfig.exists()) {
            try {
                currentConfig.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            this.load();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        ArrayList<String> finalString = new ArrayList<String>();
        for(Module clientModule : NotAName.moduleManager.listofmods()){
            StringBuilder moduleSettings = new StringBuilder();
            moduleSettings.append("module:");
            moduleSettings.append(clientModule.getName() + ":");
            moduleSettings.append(clientModule.isEnabled() + ":");
            moduleSettings.append(clientModule.getKeycode());

            finalString.add(moduleSettings.toString());
        }


        PrintWriter writer = null;
        try {
            writer = new PrintWriter(this.currentConfig);
            for (String line : finalString) {
                writer.println(line);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void load() throws FileNotFoundException {
        Scanner reader = new Scanner(this.currentConfig);
        while (reader.hasNextLine()) {
            String current = reader.nextLine();
            if(current.startsWith("module:")){
                String[] currentModule = current.split(":");

                for (Module module : NotAName.moduleManager.listofmods()) {
                    if (module.getName().equalsIgnoreCase(currentModule[1])){
                        boolean toggled = Boolean.parseBoolean(currentModule[2]);
                        int keyBind = Integer.parseInt(currentModule[3]);
                        if (module.getName().equalsIgnoreCase("hud") && toggled){
                            Module hud = (HUD) module;
                            hud.enable();
                        }
                        module.setbind(keyBind);
                        if (toggled) {
                            module.enable();
                            module.onEnable();
                        }
                    }
                }
            }
        }
    }
}
