package keystrokesmod.client.utils.profile;

import com.google.gson.JsonObject;
import keystrokesmod.client.utils.Utils;

import static keystrokesmod.client.utils.Utils.Profiles.getValueAsInt;
import static keystrokesmod.client.utils.Utils.Profiles.parseJson;

public class PlayerProfile {
    public boolean isPlayer = true;
    public boolean nicked;
    public int wins;
    public int losses;
    public int winStreak;
    public String inGameName;
    public String uuid;
    private final Utils.Profiles.DuelsStatsMode statsMode;

    public PlayerProfile(UUID uuid, Utils.Profiles.DuelsStatsMode mode) {
        this.uuid = uuid.uuid;
        this.statsMode = mode;
    }

    public PlayerProfile(String name, Utils.Profiles.DuelsStatsMode mode) {
        this.inGameName = name;
        this.statsMode = mode;
    }

    public void populateStats() {
        if (uuid == null) {
            this.uuid = Utils.Profiles.getUUIDFromName(inGameName);
            if (uuid.isEmpty()) {
                this.isPlayer = false;
                return;
            }
        }

        String textFromURL = Utils.URLS
                .getTextFromURL("https://api.hypixel.net/player?key=" + Utils.URLS.hypixelApiKey + "&uuid=" + uuid);
        if (textFromURL.isEmpty()) {
            this.nicked = true;
        } else if (textFromURL.equals("{\"success\":true,\"player\":null}")) {
            this.nicked = true;
        } else {
            JsonObject d;
            try {
                JsonObject pr = parseJson(textFromURL).getAsJsonObject("player");
                this.inGameName = pr.get("displayname").getAsString();
                d = pr.getAsJsonObject("stats").getAsJsonObject("Duels");
            } catch (NullPointerException var8) {
                return;
            }

            switch (statsMode) {
            case OVERALL:
                this.wins = getValueAsInt(d, "wins");
                this.losses = getValueAsInt(d, "losses");
                this.winStreak = getValueAsInt(d, "current_winstreak");
                break;
            case BRIDGE:
                this.wins = getValueAsInt(d, "bridge_duel_wins");
                this.losses = getValueAsInt(d, "bridge_duel_losses");
                this.winStreak = getValueAsInt(d, "current_winstreak_mode_bridge_duel");
                break;
            case UHC:
                this.wins = getValueAsInt(d, "uhc_duel_wins");
                this.losses = getValueAsInt(d, "uhc_duel_losses");
                this.winStreak = getValueAsInt(d, "current_winstreak_mode_uhc_duel");
                break;
            case SKYWARS:
                this.wins = getValueAsInt(d, "sw_duel_wins");
                this.losses = getValueAsInt(d, "sw_duel_losses");
                this.winStreak = getValueAsInt(d, "current_winstreak_mode_sw_duel");
                break;
            case CLASSIC:
                this.wins = getValueAsInt(d, "classic_duel_wins");
                this.losses = getValueAsInt(d, "classic_duel_losses");
                this.winStreak = getValueAsInt(d, "current_winstreak_mode_classic_duel");
                break;
            case SUMO:
                this.wins = getValueAsInt(d, "sumo_duel_wins");
                this.losses = getValueAsInt(d, "sumo_duel_losses");
                this.winStreak = getValueAsInt(d, "current_winstreak_mode_sumo_duel");
                break;
            case OP:
                this.wins = getValueAsInt(d, "op_duel_wins");
                this.losses = getValueAsInt(d, "op_duel_losses");
                this.winStreak = getValueAsInt(d, "current_winstreak_mode_op_duel");
            }
        }
    }
}
