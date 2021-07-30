package keystrokesmod.config;

import keystrokesmod.main.NotAName;
import keystrokesmod.module.*;
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
            StringBuilder moduleAttributes = new StringBuilder();
            moduleAttributes.append("module:");
            moduleAttributes.append(clientModule.getName() + ":");
            moduleAttributes.append(clientModule.isEnabled() + ":");
            moduleAttributes.append(clientModule.getKeycode());

            finalString.add(moduleAttributes.toString());

            for (ModuleSettingsList moduleSetting : clientModule.getSettings()) {
                StringBuilder settingString = new StringBuilder();
                String base = "setting:" + clientModule.getName() + ":" + moduleSetting.getName();
                settingString.append(base);
                if (moduleSetting.mode.equalsIgnoreCase("slider")) {
                    ModuleSettingSlider setting = (ModuleSettingSlider) moduleSetting;

                    settingString.append(":" + moduleSetting.mode);
                    settingString.append(":" + setting.getInput());
                }
                else if (moduleSetting.mode.equalsIgnoreCase("tick")) {
                    ModuleSettingTick setting = (ModuleSettingTick) moduleSetting;

                    settingString.append(":" + moduleSetting.mode);
                    settingString.append(":" + setting.isToggled());
                } else if (moduleSetting.mode.equalsIgnoreCase("desc")) {
                    ModuleDesc setting = (ModuleDesc) moduleSetting;

                    settingString.append(":" + moduleSetting.mode);
                    settingString.append(":" + setting.getDesc());
                }

                if (settingString.length() > base.length())
                    finalString.add(settingString.toString());
            }
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
                Module module = NotAName.moduleManager.getModuleByName(currentModule[1]);

                if (module == null)
                    continue;;
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

            else if (current.startsWith("setting:")){

                String[] currentSetting = current.split(":");
                Module module = NotAName.moduleManager.getModuleByName(currentSetting[1]);

                if (module == null)
                    continue;

                ModuleSettingsList settingList = module.getSettingByName(currentSetting[2]);

                if (settingList == null)
                    continue;

                if (currentSetting[3].equalsIgnoreCase("tick")) {
                    ModuleSettingTick setting = (ModuleSettingTick) settingList;

                    boolean toggled = Boolean.parseBoolean(currentSetting[4]);
                    setting.setEnabled(toggled);
                } else if (currentSetting[3].equalsIgnoreCase("slider")) {
                    ModuleSettingSlider setting = (ModuleSettingSlider) settingList;

                    double value = Double.parseDouble(currentSetting[4]);
                    setting.setValue(value);
                } else if (currentSetting[3].equalsIgnoreCase("desc")) {
                    ModuleDesc setting = (ModuleDesc) settingList;

                    setting.setDesc(currentSetting[4]);
                }
            }
        }
    }
}
