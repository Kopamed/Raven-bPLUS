package keystrokesmod.command.commands;

import keystrokesmod.CommandLine;
import keystrokesmod.command.Command;
import keystrokesmod.main.Ravenb3;

import java.io.File;
import java.io.IOException;

public class Config extends Command {
    public Config() {
        super("config", "Manages configs", 0, 3, new String[] {"load / save / list", "filename (e.g. hypixelConfig)"}, new String[] {"cfg"});
    }

    @Override
    public void onCall(String[] args){
        if (args == null) {
            CommandLine.print("&aCurrent config: ", 1);
            CommandLine.print("§3" + Ravenb3.configManager.getCurrentConfig(), 0);
        }

        else if (args.length == 2) {
            if (args[1].equalsIgnoreCase("list")) {
                this.listConfigs();
            } else {
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
                boolean found = false;
                for (File config : Ravenb3.configManager.listConfigs()) {
                    if (config.getName().startsWith(args[2])) {
                        found = true;
                        CommandLine.print("&aFound config with the name", 1);
                        CommandLine.print("&a" + args[2], 0);
                        // FUCKING LOAD args[2]
                        Ravenb3.configManager.loadConfig(args[2]);
                        CommandLine.print("&aLoaded config!", 0);
                    }
                }
                if (!found) {
                    CommandLine.print("&cUnable to find a config with the name", 1);
                    CommandLine.print("&c" + args[2], 0);
                }
            }
            else if (args[1].equalsIgnoreCase("save")) {
                //me coding the save command be like https://imgur.com/u1EJ4op
                CommandLine.print("&aSaving...", 1);
                //FUCKING SAVE TO A FILE
                Ravenb3.configManager.saveConfig(args[2]);
                CommandLine.print("&aSaved as '" + args[2] + "'", 0);
                CommandLine.print("&aTo transition to config " + args[2] + " run", 0);
                CommandLine.print("§3'config load " + args[2]+ "'", 0);
            }

        }
    }

    public void listConfigs() {
        CommandLine.print("&aAvailable configs: ", 1);
        for (File config : Ravenb3.configManager.listConfigs()) {
            if (config.getName().endsWith(Ravenb3.configManager.getExtension())) {
                if (Ravenb3.configManager.getCurrentConfig().equalsIgnoreCase(config.getName().replace(Ravenb3.configManager.getExtension(), "")))
                    CommandLine.print("§aCurrent config: " + config.getName().replace(Ravenb3.configManager.getExtension(), ""), 0);
                else
                    CommandLine.print("§3" + config.getName().replace(Ravenb3.configManager.getExtension(), ""), 0);
            }
        }
    }
}
