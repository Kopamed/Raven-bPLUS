package keystrokesmod.command.commands;

import keystrokesmod.command.Command;
import keystrokesmod.version;

import static keystrokesmod.CommandLine.print;

public class Version extends Command {
    public Version() {
        super("version", "tells you what build of B+ you are using", 0, 0, new String[] {}, new String[] {"v", "ver", "which", "build", "b"});
    }

    @Override
    public void onCall(String[] args) {
        String currentBranch;
        if(version.isBeta()){
            currentBranch = "beta";
        }else {
            currentBranch = "main";
        }

        String latestBranch = version.getSelfBranch();

        print("&eYour build: " + currentBranch + " build " + version.getSelfBetaVersion() + " of " + version.getCurrentVersion().replace("-", "."), 1);
        print("&aLatest version: " + latestBranch + " build " + version.getLatestBetaVersion() + " of " + version.getLatestVersion().replace("-", "."), 0);

    }
}
