package keystrokesmod.command.commands;

import keystrokesmod.CommandLine;
import keystrokesmod.URLUtils;
import keystrokesmod.ay;
import keystrokesmod.command.Command;
import keystrokesmod.main.Ravenbplus;

import java.io.File;
import java.util.List;

public class Config extends Command {
    public Config() {
        super("config", "Manages configs", 0, 3, new String[] {"load/save/list/remove/clear", "config's name / url"}, new String[] {"cfg", "profiles"});
    }

    @Override
    public void onCall(String[] args){
        if(Ravenbplus.clientConfig != null){
            Ravenbplus.clientConfig.saveConfig();
        }
        if (args == null) {
            CommandLine.print("&aCurrent config: ", 1);
            CommandLine.print("§3" + Ravenbplus.configManager.getCurrentConfig(), 0);
        }

        else if (args.length == 2) {
            if (args[1].equalsIgnoreCase("list")) {
                this.listConfigs();
            } else if(args[1].equalsIgnoreCase("clear")){
                CommandLine.print("&eAre you sure you want to", 1);
                CommandLine.print("&ereset the config", 0);
                CommandLine.print("§3" + Ravenbplus.configManager.getCurrentConfig(), 0);
                CommandLine.print("&eif so, enter", 0);
                CommandLine.print("§3'config clear confirm'", 0);
            }else {
                this.incorrectArgs();
            }
        }

        else if (args.length == 3) {
            /////FUCKKKKKKKKKKKKK BRUHHHHHHHHH
            if (args[1].equalsIgnoreCase("list")) {
                this.listConfigs();
            }
            else if (args[1].equalsIgnoreCase("load")) {
                //time to suffer :holsum_100:
                if(URLUtils.isLink(args[2].toLowerCase())){
                    CommandLine.print("&eYou can download configs with", 1);
                    CommandLine.print("§3'config save link'", 0);
                } else{
                    boolean found = false;
                    for (String config : Ravenbplus.configManager.listConfigs()) {
                        if (config.equalsIgnoreCase(args[2])) {
                            found = true;
                            CommandLine.print("&aFound config with the name", 1);
                            CommandLine.print("&a" + args[2], 0);
                            // FUCKING LOAD args[2]
                            Ravenbplus.configManager.loadConfig(config);
                            CommandLine.print("&aLoaded config!", 0);
                        }
                    }
                    if (!found) {
                        CommandLine.print("&cUnable to find a config with the name", 1);
                        CommandLine.print("&c" + args[2], 0);
                    }
                }
            }
            else if (args[1].equalsIgnoreCase("save")) {
                //me coding the save command be like https://imgur.com/u1EJ4op
                if(URLUtils.isLink(args[2].toLowerCase())){
                    if(URLUtils.isPastebinLink(args[2])){
                        CommandLine.print("&aVerified link!", 1);
                        String link = URLUtils.makeRawPastebinPaste(args[2]);
                        CommandLine.print("§3Downloading...", 0);
                        float startTime = System.currentTimeMillis();
                        List<String> info = URLUtils.getConfigFromURL(link);
                        CommandLine.print("&aDownloaded in " + ay.roundAvoid(System.currentTimeMillis() - startTime, 3)  + "s!", 0);
                        List<String> config = info.subList(1, info.size());
                        String configName = info.get(0);
                        boolean exists = false;
                        String filename;
                        for(String cfg : Ravenbplus.configManager.listConfigs()){
                            if(cfg.equalsIgnoreCase(configName)){
                                exists = true;
                                filename = cfg;
                            }
                        }
                        if(exists){
                            CommandLine.print("&cA config with this name exists!", 0);
                            CommandLine.print("&cto overwrite the existing config, run", 0);
                            CommandLine.print("&e'cfg save link " + configName + "'", 0);
                        }else{
                            Ravenbplus.configManager.saveNewConfig(config, configName);
                            CommandLine.print("&aSaved config!", 0);
                            CommandLine.print("&aTo transition to config " + configName + " run", 0);
                            CommandLine.print("§3'cfg load " + configName+ "'", 0);
                        }
                    }
                } else{
                    CommandLine.print("&aSaving...", 1);
                    //FUCKING SAVE TO A FILE
                    Ravenbplus.configManager.saveConfig(args[2]);
                    CommandLine.print("&aSaved as '" + args[2] + "'", 0);
                    CommandLine.print("&aTo transition to config " + args[2] + " run", 0);
                    CommandLine.print("§3'config load " + args[2]+ "'", 0);
                }
            }
            else if (args[1].equalsIgnoreCase("remove")) {
                //me coding the save command be like https://imgur.com/u1EJ4op
                CommandLine.print("&aRemoving " + args[2] + "...", 1);
                //FUCKING SAVE TO A FILE
                if (Ravenbplus.configManager.removeConfig(args[2])) {
                    CommandLine.print("&aRemoved " + args[2] + " successfully!", 0);
                    CommandLine.print("§3Current config: " + Ravenbplus.configManager.getCurrentConfig(), 0);
                }
                else {
                    CommandLine.print("&cFailed to delete " + args[2], 0);
                    CommandLine.print("&cUnable to find a config with the name", 0);
                    CommandLine.print("&cOr an error occurred during removal", 0);
                }
            } else if(args[1].equalsIgnoreCase("clear")) {
                if(args[2].equalsIgnoreCase("confirm")){
                    Ravenbplus.configManager.clearConfig();
                    CommandLine.print("&aCleared config!",1);
                } else {
                    CommandLine.print("&cIt is confirm, not " + args[2], 0);
                }

            } else {
                this.incorrectArgs();
            }

        } else if(args.length == 4){
            if(args[1].equalsIgnoreCase("save")){

            }
            else{
                this.incorrectArgs();
            }
        }
    }

    public void listConfigs() {
        CommandLine.print("&aAvailable configs: ", 1);
        for (String config : Ravenbplus.configManager.listConfigs()) {
            if (Ravenbplus.configManager.getCurrentConfig().equalsIgnoreCase(config))
                CommandLine.print("§3Current config: " + config, 0);
            else
                CommandLine.print(config, 0);
        }
    }
}
