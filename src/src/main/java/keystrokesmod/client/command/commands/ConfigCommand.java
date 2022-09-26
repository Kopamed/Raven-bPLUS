package keystrokesmod.client.command.commands;

import keystrokesmod.client.clickgui.raven.Terminal;
import keystrokesmod.client.command.Command;
import keystrokesmod.client.config.Config;
import keystrokesmod.client.main.Raven;

public class ConfigCommand extends Command {
    public ConfigCommand() {
        super("config", "Manages configs", 0, 3, new String[] { "load,save,list,remove,clear", "config's name" },
                new String[] { "cfg", "profiles" });
    }

    @Override
    public void onCall(String[] args) {
        if (Raven.clientConfig != null) {
            Raven.clientConfig.saveConfig();
            Raven.configManager.save(); // as now configs only save upon exiting the gui, this is required
        }

        if (args.length == 0) {
            Terminal.print("Current config: " + Raven.configManager.getConfig().getName());
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("list")) {
                this.listConfigs();
            } else if (args[0].equalsIgnoreCase("clear")) {
                Terminal.print("Are you sure you want to reset the config " + Raven.configManager.getConfig().getName()
                        + "? If so, run \"config clear confirm\"");
            } else {
                this.incorrectArgs();
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("list")) {
                this.listConfigs();
            } else if (args[0].equalsIgnoreCase("load")) {
                boolean found = false;
                for (Config config : Raven.configManager.getConfigs()) {
                    if (config.getName().equalsIgnoreCase(args[1])) {
                        found = true;
                        Terminal.print("Found config with the name " + args[1] + "!");
                        Raven.configManager.setConfig(config);
                        Terminal.print("Loaded config!");
                    }
                }

                if (!found) {
                    Terminal.print("Unable to find a config with the name " + args[1]);
                }

            } else if (args[0].equalsIgnoreCase("save")) {
                Terminal.print("Saving...");
                Raven.configManager.copyConfig(Raven.configManager.getConfig(), args[1] + ".bplus");
                Terminal.print("Saved as \"" + args[1] + "\"! To load the config, run \"config load " + args[1] + "\"");
                Raven.configManager.discoverConfigs();
            } else if (args[0].equalsIgnoreCase("remove")) {
                boolean found = false;
                Terminal.print("Removing " + args[1] + "...");
                for (Config config : Raven.configManager.getConfigs()) {
                    if (config.getName().equalsIgnoreCase(args[1])) {
                        Raven.configManager.deleteConfig(config);
                        found = true;
                        Terminal.print("Removed " + args[1] + " successfully! Current config: "
                                + Raven.configManager.getConfig().getName());
                        break;
                    }
                }

                if (!found) {
                    Terminal.print("Failed to delete " + args[1]
                            + ". Unable to find a config with the name or an error occurred during removal");
                }

            } else if (args[0].equalsIgnoreCase("clear")) {
                if (args[1].equalsIgnoreCase("confirm")) {
                    Raven.configManager.resetConfig();
                    Raven.configManager.save();
                    Terminal.print("Cleared config!");
                } else {
                    Terminal.print("It is confirm, not " + args[1]);
                }

            } else {
                this.incorrectArgs();
            }
        }
    }

    public void listConfigs() {
        Terminal.print("Available configs: ");
        for (Config config : Raven.configManager.getConfigs()) {
            if (Raven.configManager.getConfig().getName().equals(config.getName()))
                Terminal.print("Current config: " + config.getName());
            else
                Terminal.print(config.getName());
        }
    }
}
