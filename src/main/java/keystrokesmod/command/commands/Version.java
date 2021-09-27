package keystrokesmod.command.commands;

import keystrokesmod.command.Command;

import static keystrokesmod.clickgui.raven.CommandLine.print;

public class Version extends Command {
    public Version() {
        super("version", "tells you what build of B+ you are using", 0, 0, new String[] {}, new String[] {"v", "ver", "which", "build", "b"});
    }

    @Override
    public void onCall(String[] args) {
        String currentBranch;
        if(keystrokesmod.utils.Version.isBeta()){
            currentBranch = "beta";
        }else {
            currentBranch = "main";
        }

        String latestBranch = keystrokesmod.utils.Version.getSelfBranch();

        print("&eYour build: " + currentBranch + " build " + keystrokesmod.utils.Version.getSelfBetaVersion() + " of " + keystrokesmod.utils.Version.getCurrentVersion().replace("-", "."), 1);
        print("&aLatest version: " + latestBranch + " build " + keystrokesmod.utils.Version.getLatestBetaVersion() + " of " + keystrokesmod.utils.Version.getLatestVersion().replace("-", "."), 0);

    }
}
