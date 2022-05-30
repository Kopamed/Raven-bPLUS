package keystrokesmod.client.command.commands;

import keystrokesmod.client.clickgui.raven.CommandLine;
import keystrokesmod.client.command.Command;
import keystrokesmod.client.main.Raven;
import keystrokesmod.client.utils.Utils;
import keystrokesmod.client.utils.version.Version;

import java.net.MalformedURLException;
import java.net.URL;

public class Update extends Command {
    public Update() {
        super("update", "Assists you in updating the client", 0, 0, new String[] {}, new String[] {"upgrade"});
    }

    @Override
    public void onCall(String[] args) {
        Version clientVersion = Raven.versionManager.getClientVersion();
        Version latestVersion = Raven.versionManager.getLatestVersion();

        if (latestVersion.isNewerThan(clientVersion)) {
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
        } else {
            CommandLine.print("&aNo need to upgrade,", 1);
            CommandLine.print("&aYou are on the latest build", 0);
        }
    }
}
