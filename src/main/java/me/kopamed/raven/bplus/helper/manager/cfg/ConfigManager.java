package me.kopamed.raven.bplus.helper.manager.cfg;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import me.kopamed.raven.bplus.client.Raven;
import me.kopamed.raven.bplus.client.feature.module.Module;

import java.io.*;
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

        Map<String, ArrayList<Config>> configs = findConfigs();

        // todo find config.raven file and set configs
        if (configs.get("user").isEmpty()){
            createDefaultConfig();
        } else {
            for(Config c : configs.get("user")){
                if (!c.isReadOnly())
                    this.currentConfig = c;
            }
            if (currentConfig == null)
                createDefaultConfig();
        }
    }

    private void createDefaultConfig() {
        Config newConfig = new Config(new File(configDir.getPath() + File.separator + "default" + "." + configFileType));
        System.out.println("WAS EMPYT IOHR FOPUEWHHO");
        this.currentConfig = newConfig;
    }

    private boolean isConfig(File f) {
        if(!f.isFile())
            return false;
        String[] bobTheBuilder = f.getName().split("\\.");
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


    public Map<String, ArrayList<Config>> findConfigs() {
        Map<String, ArrayList<Config>> map = new HashMap<>();
        map.put("user", findUserConfigs());
        map.put("default", findDefaultConfigs());
        return map;
    }

    public ArrayList<Config> findUserConfigs(){
        ArrayList<Config> cfgs = new ArrayList<>();
        for(File f : configDir.listFiles()){
            if(isConfig(f))
                cfgs.add(new Config(f));
        }

        return cfgs;
    }

    public ArrayList<Config> findDefaultConfigs(){
        ArrayList<Config> cfgs = new ArrayList<>();

        try {
            File[] files = getResourceFolderFiles("assets/raven/cfg");
            if (files.length > 0) {
                for (File f : files) {
                    cfgs.add(new Config(f, true));
                }
            }
        } catch (Exception theJ){} //fix this bs

        return cfgs;
    }

    public ArrayList<Config> getConfigs(){
        Map<String, ArrayList<Config>> _configs = findConfigs();
        ArrayList<Config> configs = new ArrayList<>();

        configs.addAll(_configs.get("user"));
        configs.addAll(_configs.get("default"));

        return configs;
    }

    public void applyConfig(Config config) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(config.getFile()));
            Gson gson = new Gson();


            Map<?, ?> map = gson.fromJson(bufferedReader, Map.class);

            Map<String, Map<String, Object>> configs = ((Map<String, Map<String, Object>>)map.get("config"));

            configs.forEach((moduleName, settings) -> {
                Module m = Raven.client.getModuleManager().getModuleByName(moduleName);
                if (m != null) {
                    m.setConfigFromJson(settings);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Config load exception, loading default...");
            createDefaultConfig();
            saveConfig();
            applyConfig(findConfigs().get("default").get(0));
        }
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
