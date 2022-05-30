package keystrokesmod.client.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.*;
import net.minecraft.client.Minecraft;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Class responsible for choosing which config is currently being used
 */
public class ConfigManager {
    private final File configDirectory = new File(Minecraft.getMinecraft().mcDataDir + File.separator + "keystrokes" + File.separator + "configs");

    private Config config;
    private ArrayList<Config> configs = new ArrayList<>();

    public ConfigManager() {
        if(!configDirectory.isDirectory()){
            configDirectory.mkdirs();
        }

        discoverConfigs();
        File defaultFile = new File(configDirectory, "default.bplus");
        this.config = new Config(defaultFile);

        if(!defaultFile.exists()) {
            save();
        }

    }

    /**
     * Function that checks if the config in a given file can be loaded
     * Check is done by simply trying to parse the file as if it contains json data
     * @param file
     * @return
     */
    public static boolean isOutdated(File file) {
        JsonParser jsonParser = new JsonParser();
        try (FileReader reader = new FileReader(file))
        {
            Object obj = jsonParser.parse(reader);
            JsonObject data = (JsonObject) obj;
            return false;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Parses through all the files in the cfg dir and creates a new config class for each one
     */
    public void discoverConfigs(){
        configs.clear();
        if(configDirectory.listFiles() == null || !(configDirectory.listFiles().length > 0))
            return;  // nothing to discover if there are no files in the directory

        for(File file : configDirectory.listFiles()){
            if(file.getName().endsWith(".bplus")){
                if(!isOutdated(file)){
                    configs.add(new Config(
                            new File(file.getPath())
                    ));
                }
            }
        }
    }

    public Config getConfig(){
        return config;
    }

    public void save(){
        System.out.println("Saving");
        JsonObject data = new JsonObject();
        data.addProperty("version", Raven.versionManager.getClientVersion().getVersion());
        data.addProperty("author", "Unknown");
        data.addProperty("notes", "");
        data.addProperty("intendedServer", "");
        data.addProperty("usedFor", 0);
        data.addProperty("lastEditTime", System.currentTimeMillis());

        JsonObject modules = new JsonObject();
        for(Module module : ModuleManager.getModules()){
            modules.add(module.getName(), module.getConfigAsJson());
        }
        data.add("modules", modules);

        config.save(data);
    }

    public void setConfig(Config config){
        JsonObject data = config.getData().get("modules").getAsJsonObject();
        for(Map.Entry moduleEntry : data.entrySet()){
            Module module = ModuleManager.getModuleByName((String) moduleEntry.getKey());
            if(module == null) {
                continue;
            }

            JsonObject moduleData = ((JsonElement) moduleEntry.getValue()).getAsJsonObject();
            boolean enabled = moduleData.get("enabled").getAsBoolean();
            int keycode = moduleData.get("keycode").getAsInt();
            module.setToggled(enabled);
            module.setbind(keycode);

            for(Map.Entry SettingEntry : moduleData.get("settings").getAsJsonObject().entrySet()){
                JsonObject settingData = (JsonObject) SettingEntry.getValue();

                String settingName = (String) SettingEntry.getKey();
                String settingType = settingData.get("type").getAsString();
                Setting moduleSetting = module.getSettingByName(settingName);

                switch (settingType) {
                    case DescriptionSetting.settingType:
                        System.out.println("1");
                        DescriptionSetting descriptionSetting = (DescriptionSetting) moduleSetting;
                        descriptionSetting.setDesc(settingData.get("value").getAsString());
                        break;
                    case DoubleSliderSetting.settingType:
                        System.out.println("2");
                        DoubleSliderSetting doubleSliderSetting = (DoubleSliderSetting) moduleSetting;
                        doubleSliderSetting.setValueMax(settingData.get("valueMax").getAsDouble());
                        doubleSliderSetting.setValueMin(settingData.get("valueMin").getAsDouble());
                        doubleSliderSetting.setValueMin(settingData.get("valueMin").getAsDouble());
                        System.out.println("set " + settingData.get("valueMin").getAsDouble() + " " + doubleSliderSetting.getInputMin());

                        break;
                    case SliderSetting.settingType:
                        System.out.println("3");
                        SliderSetting sliderSetting = (SliderSetting) moduleSetting;
                        sliderSetting.setValue(settingData.get("value").getAsDouble());
                        break;
                    case TickSetting.settingType:
                        System.out.println("4");
                        TickSetting tickSetting = (TickSetting) moduleSetting;
                        tickSetting.setEnabled(settingData.get("value").getAsBoolean());
                        break;
                    default:
                        System.out.println("5");
                        break;
                }
            }
        }
    }

    public boolean loadConfigByName(String replace) {
        discoverConfigs(); // re-parsing the config folder to make sure we know which configs exist
        for(Config config: configs) {
            if(config.getName().equals(replace))
                setConfig(config);
            return true;
        }

        return false;
    }

    public ArrayList<Config> getConfigs() {
        discoverConfigs();
        return configs;
    }

    public void copyConfig(Config config, String s) {
        File file = new File(configDirectory, s);
        Config newConfig = new Config(file);
        newConfig.save(config.getData());
    }

    public void resetConfig() {
        for(Module module : ModuleManager.getModules())
            module.resetToDefaults();
    }

    public void deleteConfig(Config config) {
        config.file.delete();
        if(config.getName().equals(this.config.getName())){
            discoverConfigs();
            if(this.configs.size() < 2){
                this.resetConfig();
                File defaultFile = new File(configDirectory, "default.bplus");
                this.config = new Config(defaultFile);
                save();
            } else {
                this.config = this.configs.get(0);
            }

            this.save();
        }
    }
}
