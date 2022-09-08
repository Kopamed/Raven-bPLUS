package keystrokesmod.client.command.commands;

import keystrokesmod.client.clickgui.raven.Terminal;
import keystrokesmod.client.command.Command;
import keystrokesmod.client.main.Raven;
import keystrokesmod.client.utils.Utils;

public class Discord extends Command {
    public Discord() {
        super("discord", "Allows you to join the Raven B++ discord", 0, 3, new String[] { "copy", "open", "print" },
                new String[] { "dc", "chat" });
    }

    @Override
    public void onCall(String[] args) {
        boolean opened = false;
        boolean copied = false;
        boolean showed = false;
        int argCurrent = 0;
        if (args.length == 0) {
            Terminal.print("§3Opening " + Raven.discord);
            Utils.Client.openWebpage(Raven.discord);
            opened = true;
            return;
        }

        for (String argument : args) {
            if (argument.equalsIgnoreCase("copy")) {
                if (!copied) {
                    Utils.Client.copyToClipboard(Raven.discord);
                    copied = true;
                    Terminal.print("Copied " + Raven.discord + " to clipboard!");
                }
            } else if (argument.equalsIgnoreCase("open")) {
                if (!opened) {
                    Utils.Client.openWebpage(Raven.discord);
                    opened = true;
                    Terminal.print("Opened invite link!");
                }
            } else if (argument.equalsIgnoreCase("print")) {
                if (!showed) {
                    Terminal.print(Raven.discord);
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
