package keystrokesmod.command.commands;

import keystrokesmod.command.Command;
import keystrokesmod.version;
import keystrokesmod.command.Command;

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

        String latestBranch = version.getBranch();

        print("&eYour build: " + currentBranch + " build of version " + version.getCurrentVersion().replace("-", "."), 1);
        print("&aLatest version: " + latestBranch + " build of " + version.getLatestVersion().replace("-", "."), 0);

    }
}
