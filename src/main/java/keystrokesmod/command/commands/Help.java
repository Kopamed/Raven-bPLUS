package keystrokesmod.command.commands;

import keystrokesmod.CommandLine;
import keystrokesmod.command.Command;
import keystrokesmod.main.Ravenbplus;

public class Help extends Command {
    public Help() {
        super("help", "Shows you different<br>command usages", 0, 1, new String[] {"name of module"}, new String[] {"?", "wtf", "what"});
    }

    @Override
    public void onCall(String[] args) {
        if (args == null) {
            Ravenbplus.commandManager.sort();

            CommandLine.print("Available commands:", 1);
            int index = 1;
            for (Command command : Ravenbplus.commandManager.getCommandList()) {
                if(command.getName().equalsIgnoreCase("help"))
                    continue;

                CommandLine.print(index + ". " + command.getName(), 0);
                index++;
            }

            CommandLine.print("&aRun 'help commandname' for more", 1);
            CommandLine.print("&ainformation about the command", 0);
        } else if (args.length == 2) {
            Command command = Ravenbplus.commandManager.getCommandByName(args[1]);
            if (command == null) {
                CommandLine.print("&cUnable to find a command with the", 1);
                CommandLine.print("&cname or alias with '" + args[1] + "'", 0);
                return;
            }

            CommandLine.print("&a" + command.getName() + "'s info:", 1);
            if(command.getAliases() != null || command.getAliases().length != 0) {
                CommandLine.print(command.getName() + "'s aliases:", 0);
                for (String alias : command.getAliases()) {
                    CommandLine.print("§3" + alias, 0);
                }
            }

            if(!command.getHelp().isEmpty()) {
                CommandLine.print(command.getName() + "'s description:", 1);
                for (String helpText : command.getHelp().split("<br>"))
                    CommandLine.print("§3" + helpText, 0);
            }

            if(command.getArgs() != null) {
                CommandLine.print(command.getName() + "'s argument description:", 1);
                CommandLine.print("§3Min args: " + command.getMinArgs() + ", max args: " + command.getMaxArgs(), 0);
                int argIndex = 1;
                for (String argText : command.getArgs()){
                    CommandLine.print("§3Argument " + argIndex + ": " + argText, 0);
                    argIndex++;
                }
            }

        }
    }
}
