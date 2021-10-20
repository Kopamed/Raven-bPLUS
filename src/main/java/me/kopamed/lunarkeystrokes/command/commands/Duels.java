package me.kopamed.lunarkeystrokes.command.commands;

import me.kopamed.lunarkeystrokes.clickgui.raven.CommandLine;
import me.kopamed.lunarkeystrokes.utils.Utils;
import me.kopamed.lunarkeystrokes.command.Command;
import me.kopamed.lunarkeystrokes.main.Ravenbplus;
import me.kopamed.lunarkeystrokes.module.modules.minigames.DuelsStats;

public class Duels extends Command {
    public Duels()  {
        super("duels", "Fetches a player's stats", 1, 1,  new String[] {"Player name"},  new String[] {"d", "duel", "stat", "stats", "check"});
    }

    @Override
    public void onCall(String[] args) {
        if (Utils.URLS.hypixelApiKey.isEmpty()) {
            CommandLine.print("&cAPI Key is empty!", 1);
            CommandLine.print("Use \"setkey [api_key]\".", 0);
            return;
        }
        if(args == null) {
            this.incorrectArgs();
            return;
        }
        String n;
        n = args[1];
        CommandLine.print("Retrieving data...", 1);
        Ravenbplus.getExecutor().execute(() -> {
            int[] s = Utils.Profiles.getHypixelStats(n, Utils.Profiles.DM.OVERALL);
            if (s != null) {
                if (s[0] == -1) {
                    CommandLine.print("&c" + (n.length() > 16 ? n.substring(0, 16) + "..." : n) + " does not exist!", 0);
                } else {
                    double wlr = s[1] != 0 ? Utils.Java.round((double)s[0] / (double)s[1], 2) : (double)s[0];
                    CommandLine.print("&e" + n + " stats:", 1);
                    CommandLine.print("Wins: " + s[0], 0);
                    CommandLine.print("Losses: " + s[1], 0);
                    CommandLine.print("WLR: " + wlr, 0);
                    CommandLine.print("Winstreak: " + s[2], 0);
                    CommandLine.print("Threat: " + DuelsStats.gtl(s[0], s[1], wlr, s[2]).substring(2), 0);
                }
            } else {
                CommandLine.print("&cThere was an error.", 0);
            }

        });
    }
}
