package me.kopamed.lunarkeystrokes.command.commands;

import me.kopamed.lunarkeystrokes.clickgui.raven.CommandLine;
import me.kopamed.lunarkeystrokes.utils.Utils;
import me.kopamed.lunarkeystrokes.command.Command;
import me.kopamed.lunarkeystrokes.main.Ravenbplus;

import java.util.ArrayList;
import java.util.List;

public class Config extends Command {
    public Config() {
        super("config", "Manages configs", 0, 3, new String[] {"load,save,list,remove,clear<br>upload", "config's name / url"}, new String[] {"cfg", "profiles"});
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
            } else if(args[1].equalsIgnoreCase("upload")){
                CommandLine.print("&aUploading current config..", 1);
                StringBuilder config = new StringBuilder();
                List<String> parsedCfg = Ravenbplus.configManager.parseConfigFile();
                for(int e = 0; e<parsedCfg.size(); e++){
                    if(e == 0){
                        config.append(parsedCfg.get(e));
                    }
                    else {
                        config.append("/").append(parsedCfg.get(e));
                    }
                }
                String link = Utils.URLS.createPaste(Ravenbplus.configManager.getCurrentConfig(), config.toString());
                if(link.isEmpty()){
                    CommandLine.print("&cFailed to upload config!", 0);
                    CommandLine.print("&cMake sure api key is set!", 0);
                    CommandLine.print("§3'setkey paste <key>'", 0);
                } else {
                    CommandLine.print("&aUploaded!", 0);
                    CommandLine.print("§3" + link, 0);
                    CommandLine.print("&aCopied link to clipboard", 0);
                    Utils.Client.copyToClipboard(link);
                }
            }
            else {
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
                if(Utils.URLS.isLink(args[2].toLowerCase())){
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
                if(Utils.URLS.isLink(args[2].toLowerCase())){
                    if(Utils.URLS.isPasteeLink(args[2])){
                        CommandLine.print("&aVerified link!", 1);
                        String link = Utils.URLS.makeRawPasteePaste(args[2]);
                        CommandLine.print(link, 0);
                        //System.out.println(link);
                        CommandLine.print("§3Downloading...", 0);
                        float startTime = System.currentTimeMillis();
                        List<String> info = Utils.URLS.getConfigFromPastee(link);
                        if(! Boolean.parseBoolean(info.get(0))){
                            CommandLine.print("&cAn error occured!", 1);
                            CommandLine.print("&cThe clink did not have a config!", 0);
                            CommandLine.print("&cOr an error occurred!", 0);
                            CommandLine.print("&cMake sure you have set your paste key", 0);
                            CommandLine.print("&cWith 'setkey paste <key>'", 0);
                            return;
                        }
                        if(inConfigs(info.get(1).replace("\"", ""))){
                            CommandLine.print("&cA config with this name exists!", 0);
                            CommandLine.print("&cto overwrite the existing config, run", 0);
                            CommandLine.print("&e'cfg save link " + info.get(1).replace("\"", "") + "'", 0);
                        } else{
                            List<String> config = new ArrayList<>();
                            for(String line : info.get(2).replace("\"", "").split("/")){
                                config.add(line);
                            }
                            Ravenbplus.configManager.saveNewConfig(config, info.get(1).replace("\"", ""));
                            CommandLine.print("&aSaved config!", 0);
                            CommandLine.print("&aTo transition to config " + info.get(1).replace("\"", "") + " run", 0);
                            CommandLine.print("§3'cfg load " + info.get(1).replace("\"", "")+ "'", 0);
                        }

                    }else {
                        CommandLine.print("&cOnly pastebin links are supported!", 1);
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

            }else if(args[1].equalsIgnoreCase("upload")){
                CommandLine.print("&aUploading current config..", 1);
                String cfgName = "";
                if(!inConfigs(args[2])){
                    CommandLine.print("&cConfig " + args[2] + " does not exist!", 0);
                    return;
                }

                for(String configName : Ravenbplus.configManager.listConfigs()){
                    if(configName.equalsIgnoreCase(args[2]))
                        cfgName = configName;
                }

                StringBuilder config = new StringBuilder();
                List<String> parsedCfg = Ravenbplus.configManager.parseConfigFile(cfgName);
                for(int e = 0; e<parsedCfg.size(); e++){
                    if(e == 0){
                        config.append(parsedCfg.get(e));
                    }
                    else {
                        config.append("/").append(parsedCfg.get(e));
                    }
                }
                String link = Utils.URLS.createPaste(cfgName, config.toString());
                if(link.isEmpty()){
                    CommandLine.print("&cFailed to upload config!", 0);
                    CommandLine.print("&cMake sure api key is set!", 0);
                    CommandLine.print("§3'setkey paste <key>'", 0);
                } else {
                    CommandLine.print("&aUploaded!", 0);
                    CommandLine.print("§3" + link, 0);
                    CommandLine.print("&aCopied link to clipboard", 0);
                    Utils.Client.copyToClipboard(link);
                }
            } else {
                this.incorrectArgs();
            }

        } else if(args.length == 4){
            if(args[1].equalsIgnoreCase("save")){
                if(Utils.URLS.isLink(args[2].toLowerCase())){
                    if(Utils.URLS.isPasteeLink(args[2])){
                        CommandLine.print("&aVerified link!", 1);
                        String link = Utils.URLS.makeRawPasteePaste(args[2]);
                        CommandLine.print(link, 0);
                        //System.out.println(link);
                        CommandLine.print("§3Downloading...", 0);
                        float startTime = System.currentTimeMillis();
                        List<String> info = Utils.URLS.getConfigFromPastee(link);
                        if(! Boolean.parseBoolean(info.get(0))){
                            CommandLine.print("&cAn error occured!", 1);
                            CommandLine.print("&cThe clink did not have a config!", 0);
                            CommandLine.print("&cOr an error occurred!", 0);
                            CommandLine.print("&cMake sure you have set your paste key", 0);
                            CommandLine.print("&cWith 'setkey paste <key>'", 0);
                            return;
                        }



                            List<String> config = new ArrayList<>();
                            for(String line : info.get(2).replace("\"", "").split("/")){
                                config.add(line);
                            }
                            Ravenbplus.configManager.saveNewConfig(config, args[3]);
                            CommandLine.print("&aSaved config!", 0);
                            CommandLine.print("&aTo transition to config " + args[3] + " run", 0);
                            CommandLine.print("§3'cfg load " + args[3]+ "'", 0);

                    } else {
                        CommandLine.print("&cOnly pastebin links are supported!", 1);
                    }
                } else {
                    this.incorrectArgs();
                }
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

    public boolean inConfigs(String name){
        for (String cfg : Ravenbplus.configManager.listConfigs()){
            if(cfg.equalsIgnoreCase(name)) return true;
        }
        return false;
    }
}
