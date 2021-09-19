package keystrokesmod.config;

import keystrokesmod.main.NotAName;
import keystrokesmod.module.*;
import net.minecraft.client.Minecraft;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConfigManager {
    private final File configDirecotry;
    private File currentConfig;
    private String fileName;
    private final String extension;
    private final String defaultConfigLocation;
    public boolean loading;
    public static String seperator ="~";
    private boolean firstRun = false;
    public List<String> defaultConfig;

    public ConfigManager() {
        while (NotAName.moduleManager.arrayLength < 1){
            //System.out.println("waiting");
        }
        this.loading = false;
        configDirecotry = new File(Minecraft.getMinecraft().mcDataDir, "keystrokes" + File.separator + "configs" + File.separator);
        if (!configDirecotry.exists()) {
            configDirecotry.mkdir();
        }
        this.fileName = "default";
        this.extension = "bplus";
        this.defaultConfigLocation = "/assets/keystrokes/default.bplus";

        currentConfig = new File(configDirecotry, fileName + "." + extension);
        if (!currentConfig.exists()) {
            try {
                currentConfig.createNewFile();
                this.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        defaultConfig = this.getCurrentLoadedConfig();

        try {
            this.load();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private List<String> getCurrentLoadedConfig() {
        List<String> finalString = new ArrayList<String>();

        for(Module clientModule : NotAName.moduleManager.listofmods()){

            String moduleAttributes = "module" + seperator +
                    clientModule.getName() + seperator +
                    clientModule.isEnabled() + seperator +
                    clientModule.getKeycode();
            finalString.add(moduleAttributes);

            for (ModuleSettingsList moduleSetting : clientModule.getSettings()) {
                StringBuilder settingString = new StringBuilder();
                String base = "setting" + seperator + clientModule.getName() + seperator + moduleSetting.getName();
                settingString.append(base);
                if (moduleSetting.mode.equalsIgnoreCase("slider")) {
                    ModuleSettingSlider setting = (ModuleSettingSlider) moduleSetting;

                    settingString.append(seperator).append(moduleSetting.mode);
                    settingString.append(seperator).append(setting.getInput());
                }
                else if (moduleSetting.mode.equalsIgnoreCase("tick")) {
                    ModuleSettingTick setting = (ModuleSettingTick) moduleSetting;

                    settingString.append(seperator).append(moduleSetting.mode);
                    settingString.append(seperator).append(setting.isToggled());
                } else if (moduleSetting.mode.equalsIgnoreCase("desc")) {
                    ModuleDesc setting = (ModuleDesc) moduleSetting;

                    settingString.append(seperator).append(moduleSetting.mode);
                    settingString.append(seperator).append(setting.getDesc());
                }

                if (settingString.length() > base.length())
                    finalString.add(settingString.toString());
            }
        }

        return finalString;
    }

    public void save() {
        //////System.out.println("i save ");
        ArrayList<String> finalString = new ArrayList<String>();

        for(Module clientModule : NotAName.moduleManager.listofmods()){

            String moduleAttributes = "module" + seperator +
                    clientModule.getName() + seperator +
                    clientModule.isEnabled() + seperator +
                    clientModule.getKeycode();
            finalString.add(moduleAttributes);

            for (ModuleSettingsList moduleSetting : clientModule.getSettings()) {
                StringBuilder settingString = new StringBuilder();
                String base = "setting" + seperator + clientModule.getName() + seperator + moduleSetting.getName();
                settingString.append(base);
                if (moduleSetting.mode.equalsIgnoreCase("slider")) {
                    ModuleSettingSlider setting = (ModuleSettingSlider) moduleSetting;

                    settingString.append(seperator).append(moduleSetting.mode);
                    settingString.append(seperator).append(setting.getInput());
                } else if (moduleSetting.mode.equalsIgnoreCase("tick")) {
                    ModuleSettingTick setting = (ModuleSettingTick) moduleSetting;

                    settingString.append(seperator).append(moduleSetting.mode);
                    settingString.append(seperator).append(setting.isToggled());
                } else if (moduleSetting.mode.equalsIgnoreCase("desc")) {
                    ModuleDesc setting = (ModuleDesc) moduleSetting;

                    settingString.append(seperator).append(moduleSetting.mode);
                    settingString.append(seperator).append(setting.getDesc());
                }

                if (settingString.length() > base.length())
                    finalString.add(settingString.toString());
            }
        }


        PrintWriter writer;
        try {
            if (!this.currentConfig.exists()){
                if (!this.configDirecotry.exists()) {
                    this.configDirecotry.mkdirs();
                }
                this.currentConfig.createNewFile();
            }

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
        ////////System.out.println("iLOAD from " + this.currentConfig.getName());
        boolean error = false;
        Scanner reader = new Scanner(this.currentConfig);
        while (reader.hasNextLine()) {
            String current = reader.nextLine();
            ////System.out.println("parsing lin "+ current);
            error = false;
            if(current.split("~").length == 0){
                this.updateConfig(true);
                return;
            }
            if(current.contains("::")){
                ////System.out.println("Error detected! - " + current);
                error = true;
                this.updateConfig(true);
                return;
            }
            if(current.startsWith("module" + seperator)){

                if(!error && current.split(":").length > 4){
                    ////System.out.println("Module error! - " + current);
                    this.updateConfig(true);
                    return;
                }

                String[] currentModule = current.split(seperator);
                Module module = NotAName.moduleManager.getModuleByName(currentModule[1]);

                if (module == null)
                    continue;
                if(module.getName().equalsIgnoreCase("watp") || module.getName().equalsIgnoreCase("HUD")){
                    //System.out.println("Loadin wtap/hud ufhwe8ufh herfiuhwerghiuwehghwerughwerhg");
                }
                try{
                    boolean toggled = Boolean.parseBoolean(currentModule[2]);
                    //System.out.println("it is " + toggled);
                    int keyBind = Integer.parseInt(currentModule[3]);
                    if (module.getName().equalsIgnoreCase("hud") && toggled){
                        Module hud = module;
                        hud.enable();
                    }

                    if (module.getName().equalsIgnoreCase("Command line") && toggled){
                        if (module.isEnabled()) {
                            module.disable();
                        }
                        // module.onDisable();   ~~~~   called in Module.disable()V;
                        continue;
                    }
                    module.setbind(keyBind);
                    if (toggled) {
                        //System.out.println("en");
                        if (!module.isEnabled()) {
                            module.enable();
                        }
                        // module.onEnable();   ~~~~   called in Module.enable()V;
                    } else {
                        //System.out.println("dis");
                        if (module.isEnabled()) {
                            module.disable();
                        }
                        // module.onDisable();   ~~~~   called in Module.disable()V;
                    }
                } catch (Exception hnfsaofsh) {
                    //hnfsaofsh.printStackTrace();
                }

            }

            else if (current.startsWith("setting" + seperator)){

                if(!error && current.split(":").length > 5){
                    ////System.out.println("Setting error! - " + current);
                    this.updateConfig(true);
                    return;
                }

                String[] currentSetting = current.split(seperator);
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
                    //System.out.println("set slider " + value);
                    setting.setValue(value);
                } else if (currentSetting[3].equalsIgnoreCase("desc")) {
                    ModuleDesc setting = (ModuleDesc) settingList;

                    setting.setDesc(currentSetting[4]);
                }
            }
        }
    }

    public void updateConfig(boolean reboot) {
        List<String> config = this.parseConfigFile();
        List<String> newConfig = new ArrayList<String>();
        String newSep = "~";

        for(String line : config){
            if(line.startsWith("setting") && line.split(":").length > 5){
                String settingName = line.split(":")[2] + ":" + line.split(":")[3];
                StringBuilder newline = new StringBuilder();
                String[] currentLine = line.split(":");
                newline.append(currentLine[0]).append(newSep).append(currentLine[1]).append(newSep).append(settingName).append(newSep).append(currentLine[4]).append(newSep).append(currentLine[5]);
                newConfig.add(newline.toString());
                        //0145
            }else {
                newConfig.add(line.replace(":", newSep));
            }
        }

        try (PrintWriter writer = new PrintWriter(this.currentConfig);) {
            for (String line : newConfig) {
                ////System.out.println(line);
                writer.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(reboot) {
            try {
                this.load();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public List<String> parseConfigFile() {
        List<String> configFileContents = new ArrayList<String>();

        try (Scanner reader = new Scanner(this.currentConfig)){
            while (reader.hasNextLine())
                configFileContents.add(reader.nextLine());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return configFileContents;
    }

    public List<String> parseConfigFile(String cfg) {
        List<String> configFileContents = new ArrayList<String>();

        try (Scanner reader = new Scanner(new File(this.configDirecotry, cfg + this.getExtension()));) {
            while (reader.hasNextLine())
                configFileContents.add(reader.nextLine());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return configFileContents;
    }

    private void updateConfig() {
        updateConfig(false);
    }

    public String getExtension() {
        return "." + extension;
    }

    public String getCurrentConfig() {
        return fileName;
    }

    public File getConfigDirecotry() {
        return configDirecotry;
    }

    public void loadConfig(String fileName) {
        this.loading = true;
        ////////System.out.println("no saving");
        this.fileName = fileName;
        this.currentConfig = new File(this.configDirecotry, fileName + "." + this.extension);
        try {
            this.load();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        this.loading = false;
        ////////System.out.println("yes saving");
    }

    public void saveConfig(String fileName) {
        List<String> configToSave = this.parseConfigFile();

        File newConfigFile = new File(configDirecotry, fileName + "." + extension);
        if (!newConfigFile.exists()) {
            try {
                newConfigFile.createNewFile();
                this.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        try (PrintWriter writer = new PrintWriter(newConfigFile);){
            for (String line : configToSave) {
                writer.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean removeConfig(String fileName) {
        boolean found = false;
        for (String config : this.listConfigs()) {
            if (config.equalsIgnoreCase(fileName)){
                found = true;
                File toDelete = new File(this.configDirecotry, config + this.getExtension());
                if(toDelete.delete()) {
                    this.loadConfig("default");
                    return true;
                }
                else
                    return false;
            }
        }
        return false;
    }

    public ArrayList<String> listConfigs() {
        ArrayList<String> proBlockGameCheater = new ArrayList<String>();
        for (File config : this.configDirecotry.listFiles()) {
            if (config.getName().endsWith(this.extension)) {
                proBlockGameCheater.add(config.getName().replace(this.getExtension(), ""));
            }
        }

        return proBlockGameCheater;
    }

    public void clearConfig() {
        try (PrintWriter writer = new PrintWriter(this.currentConfig)){
            for (String line : defaultConfig) {
                writer.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.loadConfig(this.getCurrentConfig());
    }

    public void saveNewConfig(List<String> config, String configName){
        File newFile = new File(this.configDirecotry, configName + this.getExtension());

        try (PrintWriter writer = new PrintWriter(newFile);){
            for (String line : config) {
                ////System.out.println(line);
                writer.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
