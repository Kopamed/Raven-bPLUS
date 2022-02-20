package keystrokesmod.sToNkS.command.commands;

import keystrokesmod.sToNkS.command.Command;

import static keystrokesmod.sToNkS.clickgui.raven.CommandLine.print;

public class Version extends Command {
    public Version() {
        super("version", "tells you what build of B+ you are using", 0, 0, new String[] {}, new String[] {"v", "ver", "which", "build", "b"});
    }

    @Override
    public void onCall(String[] args) {
        String currentBranch;
        if(keystrokesmod.sToNkS.utils.Version.isBeta()){
            currentBranch = "beta";
        }else {
            currentBranch = "main";
        }

        String latestBranch = keystrokesmod.sToNkS.utils.Version.getSelfBranch();

        print("&eYour build: " + currentBranch + " build " + keystrokesmod.sToNkS.utils.Version.getSelfBetaVersion() + " of " + keystrokesmod.sToNkS.utils.Version.getCurrentVersion().replace("-", "."), 1);
        print("&aLatest version: " + latestBranch + " build " + keystrokesmod.sToNkS.utils.Version.getLatestBetaVersion() + " of " + keystrokesmod.sToNkS.utils.Version.getLatestVersion().replace("-", "."), 0);

    }
}
