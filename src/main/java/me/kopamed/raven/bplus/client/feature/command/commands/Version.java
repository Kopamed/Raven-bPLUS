package me.kopamed.raven.bplus.client.feature.command.commands;

import me.kopamed.raven.bplus.client.feature.command.Command;
import me.kopamed.raven.bplus.client.visual.clickgui.raven.CommandLine;

public class Version extends Command {
    public Version() {
        super("version", "tells you what build of B+ you are using", 0, 0, new String[] {}, new String[] {"v", "ver", "which", "build", "b"});
    }

    @Override
    public void onCall(String[] args) {/*
        String currentBranch;
        if(me.kopamed.raven.bplus.helper.utils.Version.isBeta()){
            currentBranch = "beta";
        }else {
            currentBranch = "main";
        }

        String latestBranch = me.kopamed.raven.bplus.helper.utils.Version.getSelfBranch();

        CommandLine.print("&eYour build: " + currentBranch + " build " + me.kopamed.raven.bplus.helper.utils.Version.getSelfBetaVersion() + " of " + me.kopamed.raven.bplus.helper.utils.Version.getCurrentVersion().replace("-", "."), 1);
        CommandLine.print("&aLatest version: " + latestBranch + " build " + me.kopamed.raven.bplus.helper.utils.Version.getLatestBetaVersion() + " of " + me.kopamed.raven.bplus.helper.utils.Version.getLatestVersion().replace("-", "."), 0);
*/
    }
}
