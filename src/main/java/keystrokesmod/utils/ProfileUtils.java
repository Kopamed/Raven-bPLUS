package keystrokesmod.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ProfileUtils {
   public static String getMojangProfile(String n) {
      String u = "";
      String r = URLUtils.getTextFromURL("https://api.mojang.com/users/profiles/minecraft/" + n);
      if (!r.isEmpty()) {
         try {
            u = r.split("d\":\"")[1].split("\"")[0];
         } catch (ArrayIndexOutOfBoundsException var4) {
         }
      }

      return u;
   }

   public static int[] getHypixelStats(String playerName, ProfileUtils.DM dm) {
      int[] s = new int[]{0, 0, 0};
      String u = getMojangProfile(playerName);
      if (u.isEmpty()) {
         s[0] = -1;
         return s;
      } else {
         String c = URLUtils.getTextFromURL("https://api.hypixel.net/player?key=" + URLUtils.hypixelApiKey + "&uuid=" + u);
         if (c.isEmpty()) {
            return null;
         } else if (c.equals("{\"success\":true,\"player\":null}")) {
            s[0] = -1;
            return s;
         } else {
            JsonObject d;
            try {
               JsonObject pr = parseJson(c).getAsJsonObject("player");
               d = pr.getAsJsonObject("stats").getAsJsonObject("Duels");
            } catch (NullPointerException var8) {
               return s;
            }

            switch(dm) {
            case OVERALL:
               s[0] = getValueAsInt(d, "wins");
               s[1] = getValueAsInt(d, "losses");
               s[2] = getValueAsInt(d, "current_winstreak");
               break;
            case BRIDGE:
               s[0] = getValueAsInt(d, "bridge_duel_wins");
               s[1] = getValueAsInt(d, "bridge_duel_losses");
               s[2] = getValueAsInt(d, "current_winstreak_mode_bridge_duel");
               break;
            case UHC:
               s[0] = getValueAsInt(d, "uhc_duel_wins");
               s[1] = getValueAsInt(d, "uhc_duel_losses");
               s[2] = getValueAsInt(d, "current_winstreak_mode_uhc_duel");
               break;
            case SKYWARS:
               s[0] = getValueAsInt(d, "sw_duel_wins");
               s[1] = getValueAsInt(d, "sw_duel_losses");
               s[2] = getValueAsInt(d, "current_winstreak_mode_sw_duel");
               break;
            case CLASSIC:
               s[0] = getValueAsInt(d, "classic_duel_wins");
               s[1] = getValueAsInt(d, "classic_duel_losses");
               s[2] = getValueAsInt(d, "current_winstreak_mode_classic_duel");
               break;
            case SUMO:
               s[0] = getValueAsInt(d, "sumo_duel_wins");
               s[1] = getValueAsInt(d, "sumo_duel_losses");
               s[2] = getValueAsInt(d, "current_winstreak_mode_sumo_duel");
               break;
            case OP:
               s[0] = getValueAsInt(d, "op_duel_wins");
               s[1] = getValueAsInt(d, "op_duel_losses");
               s[2] = getValueAsInt(d, "current_winstreak_mode_op_duel");
            }

            return s;
         }
      }
   }

   public static JsonObject parseJson(String json) {
      return (new JsonParser()).parse(json).getAsJsonObject();
   }

   private static int getValueAsInt(JsonObject jsonObject, String key) {
      try {
         return jsonObject.get(key).getAsInt();
      } catch (NullPointerException var3) {
         return 0;
      }
   }

   public enum DM {
      OVERALL,
      BRIDGE,
      UHC,
      SKYWARS,
      CLASSIC,
      SUMO,
      OP
   }
}
