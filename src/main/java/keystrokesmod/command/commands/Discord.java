package keystrokesmod.command.commands;

import keystrokesmod.CommandLine;
import keystrokesmod.URLUtils;
import keystrokesmod.ay;
import keystrokesmod.command.Command;
import keystrokesmod.main.BlowsyConfigManager;
import keystrokesmod.main.Ravenb3;

public class Discord extends Command {
    public Discord() {
        super("discord", "Allows you to join the Raven B+ discord", 0, 3, new String[] {"copy", "open", "print"}, new String[] {"dc", "chat"});
    }

    @Override
    public void onCall(String[] args) {
        boolean opened = false;
        boolean copied = false;
        boolean showed = false;
        int argCurrent = 0;
        if(args == null) {
            CommandLine.print("§3Opening Discord...", 1);
            CommandLine.print("§a" + Ravenb3.discord, 0);
            ay.openWebpage(Ravenb3.discord);
            opened = true;
            return;
        }

        for (String argument : args) {
            if(argument.equalsIgnoreCase("copy")){
                if (!copied) {
                    ay.copyToClipboard(Ravenb3.discord);
                    copied = true;
                    CommandLine.print("§aCopied to clipboard!", 1);
                }
            }
            else if(argument.equalsIgnoreCase("open")){
                if (!opened) {
                    ay.openWebpage(Ravenb3.discord);
                    opened = true;
                    CommandLine.print("§aOpened invite link!", 1);
                }
            }
            else if(argument.equalsIgnoreCase("print")){
                if (!showed){
                    CommandLine.print("§a" + Ravenb3.discord, 1);
                    showed = true;
                }
            } else {
                if (argCurrent != 0)
                    this.incorrectArgs();
            }
            argCurrent++;
        }
    }
}
