package me.kopamed.lunarkeystrokes.command.commands;

import me.kopamed.lunarkeystrokes.clickgui.raven.CommandLine;
import me.kopamed.lunarkeystrokes.utils.Utils;
import me.kopamed.lunarkeystrokes.command.Command;
import me.kopamed.lunarkeystrokes.main.Ravenbplus;
import me.kopamed.lunarkeystrokes.utils.Version;

import java.net.MalformedURLException;
import java.net.URL;

public class Update extends Command {
    public Update() {
        super("update", "Assists you in updating the client", 0, 0, new String[] {}, new String[] {"upgrade"});
    }

    @Override
    public void onCall(String[] args) {
        ////////System.out.println("Called update");
        if (Ravenbplus.outdated || Version.outdated()) {
            CommandLine.print("§3Opening page...", 1);
            URL url = null;
            try {
                url = new URL(Ravenbplus.sourceLocation);
                Utils.Client.openWebpage(url);
                CommandLine.print("&aOpened page successfully!", 0);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                CommandLine.print("&cFailed to open page!", 0);
                CommandLine.print("&cPlease report this bug", 0);
                CommandLine.print("&cin Raven b+'s discord", 0);

            }
        }
        else if (Ravenbplus.beta || Version.isBeta()) {
            CommandLine.print("&aMate.", 1);
            CommandLine.print("&aYou are on a beta build,", 0);
            CommandLine.print("&aYou are all set", 0);
        } else {
            CommandLine.print("&aNo need to upgrade,", 1);
            CommandLine.print("&aYou are on the latest build", 0);
        }
    }
}
