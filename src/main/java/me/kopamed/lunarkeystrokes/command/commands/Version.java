package me.kopamed.lunarkeystrokes.command.commands;

import me.kopamed.lunarkeystrokes.command.Command;

import static me.kopamed.lunarkeystrokes.clickgui.raven.CommandLine.print;

public class Version extends Command {
    public Version() {
        super("version", "tells you what build of B+ you are using", 0, 0, new String[] {}, new String[] {"v", "ver", "which", "build", "b"});
    }

    @Override
    public void onCall(String[] args) {
        String currentBranch;
        if(me.kopamed.lunarkeystrokes.utils.Version.isBeta()){
            currentBranch = "beta";
        }else {
            currentBranch = "main";
        }

        String latestBranch = me.kopamed.lunarkeystrokes.utils.Version.getSelfBranch();

        print("&eYour build: " + currentBranch + " build " + me.kopamed.lunarkeystrokes.utils.Version.getSelfBetaVersion() + " of " + me.kopamed.lunarkeystrokes.utils.Version.getCurrentVersion().replace("-", "."), 1);
        print("&aLatest version: " + latestBranch + " build " + me.kopamed.lunarkeystrokes.utils.Version.getLatestBetaVersion() + " of " + me.kopamed.lunarkeystrokes.utils.Version.getLatestVersion().replace("-", "."), 0);

    }
}
