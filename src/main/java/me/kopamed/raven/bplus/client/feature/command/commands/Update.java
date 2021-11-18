package me.kopamed.raven.bplus.client.feature.command.commands;

import me.kopamed.raven.bplus.client.visual.clickgui.raven.CommandLine;
import me.kopamed.raven.bplus.client.Raven;
import me.kopamed.raven.bplus.helper.utils.Utils;
import me.kopamed.raven.bplus.client.feature.command.Command;

import java.net.MalformedURLException;
import java.net.URL;

public class Update extends Command {
    public Update() {
        super("update", "Assists you in updating the client", 0, 0, new String[] {}, new String[] {"upgrade"});
    }

    @Override
    public void onCall(String[] args) {
        ////////System.out.println("Called update");/
        //
        /*
        if (Raven.outdated || me.kopamed.raven.bplus.helper.utils.Version.outdated()) {
            CommandLine.print("§3Opening page...", 1);
            URL url = null;
            try {
                url = new URL(Raven.sourceLocation);
                Utils.Client.openWebpage(url);
                CommandLine.print("&aOpened page successfully!", 0);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                CommandLine.print("&cFailed to open page!", 0);
                CommandLine.print("&cPlease report this bug", 0);
                CommandLine.print("&cin Raven b+'s discord", 0);

            }
        }
        else if (Raven.beta || Version.isBeta()) {
            CommandLine.print("&aMate.", 1);
            CommandLine.print("&aYou are on a beta build,", 0);
            CommandLine.print("&aYou are all set", 0);
        } else {
            CommandLine.print("&aNo need to upgrade,", 1);
            CommandLine.print("&aYou are on the latest build", 0);
        }*/
    }
}
