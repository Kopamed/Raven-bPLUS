package keystrokesmod.config;

import keystrokesmod.CommandLine;
import keystrokesmod.GuiModuleCategory;
import keystrokesmod.URLUtils;
import keystrokesmod.ay;
import keystrokesmod.main.NotAName;
import keystrokesmod.main.Ravenbplus;
import keystrokesmod.module.*;
import keystrokesmod.module.modules.HUD;
import net.minecraft.client.Minecraft;

import java.io.*;
import java.util.*;

public class Config {
    private final File configFile;
    private final File cfgDirectory;
    private final String configFileName = "config";
    private final String configPrefix = "config-";
    private final String clickGUIPosPrefix = "cgui-pos~";
    private final String hypixelApiPrefix = "hypixel-api~";
    private final String pasteApiPrefix = "paste-api~";
    private final String savedConfig = "saved-config~";
    private final String moduleOptionPrefix = "module~";
    private final String settingOptionPrefix = "setting~";
    private final String defaultConfigEncrypted;
    private String currentConfig;
    private int currentConfigLine;

    //module setup:
    //module:modulename:isToggled:keycode

    //setting setup:
    //setting:modulename:settingname:settingtype:settingvalue


    public Config() {
        this.cfgDirectory = new File(Minecraft.getMinecraft().mcDataDir, "keystrokes");
        if (!this.cfgDirectory.exists()) {
            this.cfgDirectory.mkdir();
        }

        this.configFile = new File(this.cfgDirectory, this.configFileName);
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.loadHypixelApiKey(this.parseHypixelApiKey());
        this.loadPasteApiKey(this.parsePasteApiKey());
        this.loadClickGuiCoords(this.decrypt(this.parseClickGuiCoords()));
        //todo: make default config uneditable
        this.defaultConfigEncrypted = this.encrypt(this.getCurrentConfig());
        this.parseLoadedConfigSetting();
        if(!doWeHaveThisCfg(currentConfig)){
            saveCurrentConfig(this.encrypt(this.getCurrentConfig()));
        }
        this.setCurrentConfigLine(this.getConfigLine(this.parseLoadedConfigSetting()));
        System.out.println(currentConfig + " " + currentConfigLine );
        this.loadFromConfigLine();
    }

    public void loadConfig(String name){
        if(this.getConfigList().contains(name)){
            this.setCurrentConfigLine(this.getConfigLine(name));
            this.loadFromConfigLine();
        }
    }

    public boolean doWeHaveThisCfg(String configName){
        for (String iamconfused : this.parseConfigFile()){
            if(iamconfused.replace(configPrefix, "").equalsIgnoreCase(configName)){
                return true;
            }
        }

        return false;
    }

    public void clearCurrentConfig(){
        List<String> config = this.parseConfigFile();
        for(int i = 0; i < config.size(); i++){
            String line = config.get(i);
            if(line.replace(configPrefix, "").equalsIgnoreCase(currentConfig)){
                config.set(i+1, this.defaultConfigEncrypted);
            }
        }
        this.updateConfigFile(config);
        this.loadFromConfigLine();
    }

    public void setHypixelApiKey(String n) {
        List<String> configFile = this.parseConfigFile();
        for(int i = 0; i < configFile.size(); i++){
            String line = configFile.get(i);
            if(line.startsWith(hypixelApiPrefix)){
                configFile.set(i, hypixelApiPrefix + n);
            }
        }
        this.updateConfigFile(configFile);
    }

    public void updateConfigFile(List<String> configList) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(this.configFile);
            for(String what : configList){
                writer.println(what);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void updateConfigFile() {
        List<String> finalConfig = new ArrayList<String>();
        finalConfig.add(hypixelApiPrefix + URLUtils.hypixelApiKey);
        finalConfig.add(pasteApiPrefix + URLUtils.pasteApiKey);
        finalConfig.add(savedConfig + currentConfig);
        finalConfig.add(clickGUIPosPrefix + encrypt(this.getClickGuiCoords()));
        finalConfig = this.addConfigsToList(finalConfig);
        this.updateConfigFile(finalConfig);
        //try {
        // writer = new FileWriter(this.configFile, false);

        // writer.write("api: " + URLUtils.hypixelApiKey + "\n");
        // writer.write(ab.theme + "\n" + ab.r1 + "\n" + ab.r2 + "\n" + ab.r3 + "\n" + ab.r4 + "\n" + ab.r5 + "\n" + ab.r6 + "\n" + ab.r7 + "\n" + ab.v1 + "\n" + ab.v2 + "\n" + ab.v3 + "\n" + ab.v4 + "\n" + ab.au1 + "\n" + ab.au2 + "\n" + ab.au3 + "\n" + ab.au4 + "\n" + ab.au5 + "\n" + ab.au6 + "\n" + ab.au7 + "\n" + ab.au8 + "\n" + ab.au9);
        //   writer.close();
        //} catch (IOException e) {
        //e.printStackTrace();
        //}
    }

    public List<String> getConfigList(){
        List<String> configs = new ArrayList<String>();
        for (String line : this.parseConfigFile()){
            if(line.startsWith(configPrefix)){
                configs.add(line.replace(configPrefix, ""));
            }
        }

        return configs;
    }

    private List<String> addConfigsToList(List<String> finalConfig) {
        this.saveCurrentConfig(this.encrypt(this.getCurrentConfig()));
        List<String> forallmyhomies = this.parseConfigFile();
        for(int i = 0; i < forallmyhomies.size(); i++){
            String line = forallmyhomies.get(i);
            if(line.startsWith(configPrefix)){
                finalConfig.add(line);
                finalConfig.add(forallmyhomies.get(i + 1));
            }
        }
        return finalConfig;
    }

    private String getCurrentConfig() {
        StringBuilder config = new StringBuilder();
        for(Module module : NotAName.moduleManager.listofmods()){
            //module setup:
            //module:modulename:isToggled:keycode

            //setting setup:
            //setting:modulename:settingname:settingtype:settingvalue
            config.append(moduleOptionPrefix);
            config.append(module.getName());
            config.append("~");
            config.append(module.isEnabled());
            config.append("~");
            config.append(module.getKeycode());
            config.append("/");
            for(ModuleSettingsList setting : module.getSettings()){
                config.append(settingOptionPrefix).append(module.getName()).append("~").append(setting.getName());
                if (setting.mode.equalsIgnoreCase("slider")) {
                    ModuleSettingSlider sliderSetting = (ModuleSettingSlider) setting;

                    config.append("~").append(sliderSetting.mode);
                    config.append("~").append(sliderSetting.getInput());
                }
                else if (setting.mode.equalsIgnoreCase("tick")) {
                    ModuleSettingTick settingTick = (ModuleSettingTick) setting;

                    config.append("~").append(settingTick.mode);
                    config.append("~").append(settingTick.isToggled());
                } else if (setting.mode.equalsIgnoreCase("desc")) {
                    ModuleDesc settingDesc = (ModuleDesc) setting;

                    config.append("~").append(settingDesc.mode);
                    config.append("~").append(settingDesc.getDesc());
                }

                config.append("/");
            }
        }

        System.out.println(config.toString().substring(0, config.toString().length()-1) );
        return config.toString().substring(0, config.toString().length()-1);
    }

    public String getCurrentConfigName(){
        return this.currentConfig;
    }

    private void saveCurrentConfig(String encryptedConfig) {
        List<String> configFileList = this.parseConfigFile();
        boolean found = false;
        for(int i = 0; i < configFileList.size(); i++){
            String line = configFileList.get(i);
            if(line.startsWith(configPrefix) && line.replace(configPrefix, "").equalsIgnoreCase(this.currentConfig)){
                configFileList.set(i+1, encryptedConfig);
                found = true;
            }
        }

        if(!found){
            configFileList.add(configPrefix + currentConfig);
            configFileList.add(encryptedConfig);
        }

        updateConfigFile(configFileList);
    }

    private String getClickGuiCoords() {
        StringBuilder posConfig = new StringBuilder();
        for (GuiModuleCategory cat : NotAName.clickGui.categoryList) {
                posConfig.append(cat.categoryName.name());
                posConfig.append("~");
                posConfig.append(cat.getX());
                posConfig.append("~");
                posConfig.append(cat.getY());
                posConfig.append("~");
                posConfig.append(cat.isOpened());
                posConfig.append("/");
        }
        return posConfig.toString().substring(0, posConfig.toString().length() - 2);
    }


    private void loadClickGuiCoords(String decryptedString) {
        //clickgui config
        // categoryname:x:y:opened
        System.out.println(decryptedString);
        for (String what : decryptedString.split("/")){
            for (GuiModuleCategory cat : NotAName.clickGui.categoryList) {
                if(what.startsWith(cat.categoryName.name())){
                    List<String> cfg = ay.StringListToList(what.split("~"));
                    cat.setX(Integer.parseInt(cfg.get(1)));
                    cat.setY(Integer.parseInt(cfg.get(2)));
                    cat.setCategoryOpened(Boolean.parseBoolean(cfg.get(3)));
                }
            }
        }
    }

    private void loadPasteApiKey(String parsePasteApiKey) {
        URLUtils.pasteApiKey = parsePasteApiKey;
    }

    private void loadHypixelApiKey(String parseHypixelApiKey) {
        //System.out.println(parseHypixelApiKey);
        URLUtils.hypixelApiKey = parseHypixelApiKey;
        Ravenbplus.getExecutor().execute(() -> {
            if (!URLUtils.isHypixelKeyValid(URLUtils.hypixelApiKey)) {
                URLUtils.hypixelApiKey = "";
                ////System.out.println("Invalid key!");
            } else{
                ////System.out.println("Valid key!");
            }

        });
    }

    private String parseClickGuiCoords() {
        List<String> configFile = this.parseConfigFile();
        for (String line : configFile) {
            if (line.startsWith(clickGUIPosPrefix)){
                return line.replace(clickGUIPosPrefix, "");
            }
        }
        return "";
    }

    private String parsePasteApiKey() {
        List<String> configFile = this.parseConfigFile();
        for (String line : configFile) {
            if (line.startsWith(pasteApiPrefix)){
                return line.replace(pasteApiPrefix, "");
            }
        }
        return "";
    }

    private String parseHypixelApiKey() {
        List<String> configFile = this.parseConfigFile();
        for (String line : configFile) {
            //System.out.println(line);
            if (line.startsWith(hypixelApiPrefix)){
                return line.replace(hypixelApiPrefix, "");
            }
        }
        return "";
    }

    private void loadFromConfigLine(int line) {
        if(line < 0){
            CommandLine.print("&cNo config found...", 1);
            return;
        }
        List<String> configFile = this.parseConfigFile();
        List<String> config = ay.StringListToList(decrypt(configFile.get(line)).split("/"));
        for(String whyyyyy : config){
            System.out.println(whyyyyy);
        }

        for (String option : config) {
            if (option.startsWith(moduleOptionPrefix)) {
                try{
                    this.setModuleConfig(option);
                } catch (Exception e){
                    e.printStackTrace();
                }

            } else if (option.startsWith(settingOptionPrefix)) {
                try{
                    this.setSettingConfig(option);
                } catch (IndexOutOfBoundsException e){
                    e.printStackTrace();
                }
            }
        }
    }

    private void setSettingConfig(String option) {
        //setting setup:
        //setting:modulename:settingname:settingtype:settingvalue
        List<String> settingSettings = ay.StringListToList(option.replace(settingOptionPrefix, "").split("~"));
        for(String what : settingSettings){
            System.out.println(what);
        }
        switch (settingSettings.get(2)) {
            case "slider":
                this.setSettingForSlider(settingSettings);
                break;
            case "tick":
                this.setSettingForTick(settingSettings);
                break;
            case "desc":
                this.setSettingForDesc(settingSettings);
        }
    }

    private void setSettingForDesc(List<String> settingSettings) {
        Module module = NotAName.moduleManager.getModuleByName(settingSettings.get(0));
        ModuleDesc descSettting = (ModuleDesc) module.getSettingByName(settingSettings.get(1));
        descSettting.setDesc(settingSettings.get(3));
    }

    private void setSettingForTick(List<String> settingSettings) {
        Module module = NotAName.moduleManager.getModuleByName(settingSettings.get(0));
        ModuleSettingTick tickSettting = (ModuleSettingTick) module.getSettingByName(settingSettings.get(1));
        tickSettting.setEnabled(Boolean.parseBoolean(settingSettings.get(3)));
    }

    private void setSettingForSlider(List<String> settingSettings) {
        //setting setup:
        //setting:modulename:settingname:settingtype:settingvalue
        Module module = NotAName.moduleManager.getModuleByName(settingSettings.get(0));
        ModuleSettingSlider sliderSettting = (ModuleSettingSlider) module.getSettingByName(settingSettings.get(1));
        sliderSettting.setValue(Double.parseDouble(settingSettings.get(3)));
    }

    private void setModuleConfig(String option) {
        //module setup:
        //module:modulename:isToggled:keycode
        List<String> moduleSettings = ay.StringListToList(option.replace(moduleOptionPrefix, "").split("~"));
        System.out.println(NotAName.moduleManager == null);
        Module module = NotAName.moduleManager.getModuleByName(moduleSettings.get(0));
        if (module.getName().equalsIgnoreCase("command line")) {
            module.setToggled(false);
        } else if (module.getName().equalsIgnoreCase("hud")) {
            HUD hud = (HUD) module;
            if(Boolean.parseBoolean(moduleSettings.get(1)))
                hud.enable();
        }else {
            if(Boolean.parseBoolean(moduleSettings.get(1))){
                module.onEnable();
                module.enable();
            } else{
                module.onDisable();
                module.disable();
            }
        }
        module.setbind(Integer.parseInt(moduleSettings.get(2)));
    }

    private void loadFromConfigLine() {
        this.loadFromConfigLine(currentConfigLine);
    }

    private int getConfigLine(String configName) {
        List<String> configFile = this.parseConfigFile();
        for (int i = 0; i < configFile.size(); i++) {
            String line = configFile.get(i);
            if (line.startsWith(configPrefix) && line.replace(configPrefix, "").equalsIgnoreCase(configName) && i + 1 < configFile.size()) {
                return i + 1;
            }
        }
        return -1;
    }

    private String parseLoadedConfigSetting() {
        List<String> configFile = this.parseConfigFile();
        for (String line : configFile) {
            if (line.startsWith(savedConfig)) {
                this.currentConfig = line.replace(savedConfig, "");
                return currentConfig;
            }
        }
        this.currentConfig = "default";
        return "default";
    }

    private List<String> parseConfigFile() {
        List<String> configFileContents = new ArrayList<String>();
        Scanner reader = null;
        try {
            reader = new Scanner(this.configFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (reader.hasNextLine())
            configFileContents.add(reader.nextLine());

        return configFileContents;
    }

    public static String decrypt(String encryptedConfig) {
        String decompressedString;
        try {
            decompressedString = ay.decompressString(Base64.getDecoder().decode(encryptedConfig));
        } catch (Exception e) {
            e.printStackTrace();
            decompressedString = encryptedConfig;
        }
        String decryptedString = decompressedString.toString();
        return decryptedString;
    }

    public static String encrypt(String decryptedConfig) {
        byte[] compressedString;
        try {
            compressedString = ay.compressString(decryptedConfig);
        } catch (Exception e) {
            e.printStackTrace();
            compressedString = decryptedConfig.getBytes();
        }
        String encryptedString = Base64.getEncoder().encodeToString(compressedString);
        return encryptedString;
    }

    public void setCurrentConfigLine(int currentConfigLine) {
        this.currentConfigLine = currentConfigLine;
    }

    public void cloneWithName(String arg) {
        List<String> config = this.parseConfigFile();
        boolean found = false;
        for(int i = 0; i < config.size(); i++){
            String line = config.get(i);
            if(line.startsWith(configPrefix) && line.replace(configPrefix, "").equalsIgnoreCase(arg)){
                config.set(i+1, this.encrypt(this.getCurrentConfig()));
            }
            found = true;
        }

        if(!found){
            config.add(configPrefix + arg);
            config.add(this.encrypt(this.getCurrentConfig()));
        }

        this.updateConfigFile(config);
    }

    public boolean removeConfig(String arg) {
    }
}
