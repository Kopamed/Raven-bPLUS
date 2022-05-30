package keystrokesmod.client.command.commands;

import keystrokesmod.client.clickgui.raven.CommandLine;
import keystrokesmod.client.command.Command;
import keystrokesmod.client.config.Config;
import keystrokesmod.client.main.Raven;

public class ConfigCommand extends Command {
    public ConfigCommand() {
        super("config", "Manages configs", 0, 3, new String[] {"load,save,list,remove,clear", "config's name"}, new String[] {"cfg", "profiles"});
    }

    @Override
    public void onCall(String[] args){
        if(Raven.clientConfig != null){
            Raven.clientConfig.saveConfig();
            Raven.configManager.save(); // as now configs only save upon exiting the gui, this is required
        }

        if (args == null) {
            CommandLine.print("&aCurrent config: ", 1);
            CommandLine.print("§3" + Raven.configManager.getConfig().getName(), 0);
        }

        else if (args.length == 2) {
            if (args[1].equalsIgnoreCase("list")) {
                this.listConfigs();
            } else if(args[1].equalsIgnoreCase("clear")){
                CommandLine.print("&eAre you sure you want to", 1);
                CommandLine.print("&ereset the config", 0);
                CommandLine.print("§3" + Raven.configManager.getConfig().getName(), 0);
                CommandLine.print("&eif so, enter", 0);
                CommandLine.print("§3'config clear confirm'", 0);
            }
            else {
                this.incorrectArgs();
            }
        }

        else if (args.length == 3) {
            if (args[1].equalsIgnoreCase("list")) {
                this.listConfigs();
            }

            else if (args[1].equalsIgnoreCase("load")) {
                boolean found = false;
                for (Config config : Raven.configManager.getConfigs()) {
                    if (config.getName().equalsIgnoreCase(args[2])) {
                        found = true;
                        CommandLine.print("&aFound config with the name", 1);
                        CommandLine.print("&a" + args[2], 0);
                        Raven.configManager.setConfig(config);
                        CommandLine.print("&aLoaded config!", 0);
                    }
                }

                if (!found) {
                    CommandLine.print("&cUnable to find a config with the name", 1);
                    CommandLine.print("&c" + args[2], 0);
                }

            }
            else if (args[1].equalsIgnoreCase("save")) {
                CommandLine.print("&aSaving...", 1);
                Raven.configManager.copyConfig(Raven.configManager.getConfig(), args[2] + ".bplus");
                CommandLine.print("&aSaved as '" + args[2] + "'", 0);
                CommandLine.print("&aTo transition to config " + args[2] + " run", 0);
                CommandLine.print("§3'config load " + args[2]+ "'", 0);

            }
            else if (args[1].equalsIgnoreCase("remove")) {
                boolean found = false;
                CommandLine.print("&aRemoving " + args[2] + "...", 1);
                for(Config config : Raven.configManager.getConfigs()){
                    if(config.getName().equalsIgnoreCase(args[2])){
                        Raven.configManager.deleteConfig(config);
                        found = true;
                        CommandLine.print("&aRemoved " + args[2] + " successfully!", 0);
                        CommandLine.print("§3Current config: " + Raven.configManager.getConfig().getName(), 0);
                        break;
                    }
                }

                if(!found) {
                    CommandLine.print("&cFailed to delete " + args[2], 0);
                    CommandLine.print("&cUnable to find a config with the name", 0);
                    CommandLine.print("&cOr an error occurred during removal", 0);
                }

            } else if(args[1].equalsIgnoreCase("clear")) {
                if(args[2].equalsIgnoreCase("confirm")){
                    Raven.configManager.resetConfig();
                    Raven.configManager.save();
                    CommandLine.print("&aCleared config!",1);
                } else {
                    CommandLine.print("&cIt is confirm, not " + args[2], 0);
                }

            }else {
                this.incorrectArgs();
            }
        }
    }

    public void listConfigs() {
       CommandLine.print("&aAvailable configs: ", 1);
        for (Config config : Raven.configManager.getConfigs()) {
            if (Raven.configManager.getConfig().getName().equals(config.getName()))
                CommandLine.print("§3Current config: " + config.getName(), 0);
            else
                CommandLine.print(config.getName(), 0);
        }
    }
}
