package keystrokesmod.client.command.commands;


import keystrokesmod.client.clickgui.raven.Terminal;
import keystrokesmod.client.command.Command;
import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.modules.minigames.DuelsStats;
import keystrokesmod.client.utils.Utils;

public class Duels extends Command {
    public Duels()  {
        super("duels", "Fetches a player's stats", 1, 1,  new String[] {"Player name"},  new String[] {"d", "duel", "stat", "stats", "check"});
    }

    @Override
    public void onCall(String[] args) {
        if (Utils.URLS.hypixelApiKey.isEmpty()) {
            Terminal.print("API Key is empty! Run \"setkey api_key\".");
            return;
        }
        if(args.length == 0) {
            this.incorrectArgs();
            return;
        }
        String n;
        n = args[0];
        Terminal.print("Retrieving data...");
        Raven.getExecutor().execute(() -> {
            int[] s = Utils.Profiles.getHypixelStats(n, Utils.Profiles.DuelsStatsMode.OVERALL);
            if (s != null) {
                if (s[0] == -1) {
                    Terminal.print((n.length() > 16 ? n.substring(0, 16) + "..." : n) + " does not exist!");
                } else {
                    double wlr = s[1] != 0 ? Utils.Java.round((double)s[0] / (double)s[1], 2) : (double)s[0];
                    Terminal.print(n + " stats:");
                    Terminal.print("Wins: " + s[0]);
                    Terminal.print("Losses: " + s[1]);
                    Terminal.print("WLR: " + wlr);
                    Terminal.print("Winstreak: " + s[2]);
                }
            } else {
                Terminal.print("There was an error.");
            }

        });
    }
}
