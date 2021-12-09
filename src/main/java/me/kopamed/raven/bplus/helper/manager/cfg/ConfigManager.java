package me.kopamed.raven.bplus.helper.manager.cfg;

import me.kopamed.raven.bplus.client.Raven;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class ConfigManager {
    private final File clientDir = new File(Raven.client.getMc().mcDataDir + File.separator + "raven");
    private final File configDir = new File(clientDir + File.separator + "configs"); // todo: make sure that his bs does not give an errrops
    private final String fileType = "bplus";

    private Config currentConfig;

    private ArrayList<Config> configs = new ArrayList<>();


    /*
    list _/
    new / create
    delete
    save
    duplicate
     */

    public ConfigManager(){
        if(!clientDir.exists())
            clientDir.mkdir();

        if(!configDir.exists())
            configDir.mkdir();

        findConfigs();

        // todo find config.raven file and set configs
        this.currentConfig = configs.get(0);
    }

    private boolean isConfig(File f) {
        if(!f.isFile())
            return false;
        String[] bobTheBuilder = f.getName().split(".");
        String filetype = bobTheBuilder[bobTheBuilder.length - 1];
        return filetype.equals(fileType);
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
        for (File f : getResourceFolderFiles("assets/raven/cfg")) {
            configs.add(new Config(f, true));
        }

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
}
