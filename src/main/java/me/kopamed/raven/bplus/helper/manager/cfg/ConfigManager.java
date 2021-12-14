package me.kopamed.raven.bplus.helper.manager.cfg;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import me.kopamed.raven.bplus.client.Raven;
import me.kopamed.raven.bplus.client.feature.module.Module;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager {
    // File management
    private final File clientDir = new File(Raven.client.getMc().mcDataDir + File.separator + "raven");
    private final File configDir = new File(clientDir.getPath() + File.separator + "configs"); // todo: make sure that his bs does not give an errrops
    private final String configFileType = "bplus";
    private final String settingFileType = "raven";

    // Settings for the client in general
    private String apiKey;
    private int hudX, hudY;

    // Runtime config settings
    private Config currentConfig;
    private ArrayList<Config> configs = new ArrayList<>();


    /*
    TODO
    list _/
    new / create
    delete
    save
    duplicate

    api key, loaded cfg, hudxy
     */


    public ConfigManager(){
        // Making the config directories
        if(!clientDir.exists())
            clientDir.mkdir();

        if(!configDir.exists())
            configDir.mkdir();



        findConfigs();

        // todo find config.raven file and set configs
        if(configs.isEmpty()){
            createDefaultConfig();
        } else {
            for(Config c : configs){
                if(!c.isReadOnly())
                    this.currentConfig = c;
            }
            if(currentConfig == null)
                createDefaultConfig();
        }
    }

    private void createDefaultConfig() {
        Config newConfig = new Config(new File(configDir.getPath() + File.separator + "default" + "." + configFileType));
        System.out.println("WAS EMPYT IOHR FOPUEWHHO");
        this.currentConfig = newConfig;
        configs.add(newConfig);
    }

    private boolean isConfig(File f) {
        if(!f.isFile())
            return false;
        String[] bobTheBuilder = f.getName().split(".");
        if(bobTheBuilder.length == 0)
            return false;
        System.out.println(Arrays.asList(bobTheBuilder));
        String filetype = bobTheBuilder[bobTheBuilder.length - (bobTheBuilder.length == 0 ? 0 : 1)];
        return filetype.equals(configFileType);
    }

    private File[] getResourceFolderFiles(String folder) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource(folder);
        assert url != null;
        String path = url.getPath();
        return new File(path).listFiles();
    }

    public void findConfigs(){
        configs.clear();
        try {
            File[] files = getResourceFolderFiles("assets/raven/cfg");
            if (files.length > 0) {
                for (File f : files) {
                    configs.add(new Config(f, true));
                }
            }
        } catch (Exception theJ){} //fix this bs

        for(File f : configDir.listFiles()){
            if(isConfig(f))
                configs.add(new Config(f));
        }
    }

    public ArrayList<Config> getConfigs(){
        return configs;
    }

    public void applyConfig(Config config){

    }

    public void saveConfig() {
        if(currentConfig != null){
            try {
                // create a map
                Map<String, Map> map = new HashMap<>();

                //adding the config headers
                map.put("headers", currentConfig.getHeaders());

                //creating the config
                Map<Module, JsonObject> config = new HashMap<>();
                for(Module m : Raven.client.getModuleManager().getModules()){
                    config.put(m, m.getConfigAsJson());
                }

                map.put("config", config);

                // create a writer
                Writer writer = new FileWriter(currentConfig.getFile());

                // convert map to JSON File
                new GsonBuilder().setPrettyPrinting().create().toJson(map, writer);

                // close the writer
                writer.close();
                System.out.println("SAVED !!EOUY @ PUYWFHIUH EFPUIWH JWI FOIA FOIAW " + currentConfig.getFile().getPath());

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
