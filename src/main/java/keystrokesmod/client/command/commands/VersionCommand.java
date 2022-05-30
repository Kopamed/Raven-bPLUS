package keystrokesmod.client.command.commands;

import keystrokesmod.client.command.Command;
import keystrokesmod.client.main.Raven;
import keystrokesmod.client.utils.version.Version;

import static keystrokesmod.client.clickgui.raven.CommandLine.print;

public class VersionCommand extends Command {
    public VersionCommand() {
        super("version", "tells you what build of B+ you are using", 0, 0, new String[] {}, new String[] {"v", "ver", "which", "build", "b"});
    }

    @Override
    public void onCall(String[] args) {
        Version clientVersion = Raven.versionManager.getClientVersion();
        Version latestVersion = Raven.versionManager.getLatestVersion();

        print("&eYour build: " + clientVersion, 1);
        print("&aLatest version: " + latestVersion, 0);

    }
}
