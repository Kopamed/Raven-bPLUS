package keystrokesmod.client.module.modules.minigames.Sumo;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.ComboSetting;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.Utils;
import keystrokesmod.client.utils.Utils.Profiles.DuelsStatsMode;
import keystrokesmod.client.utils.profile.PlayerProfile;
import keystrokesmod.client.utils.profile.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.ScorePlayerTeam;

public class SumoStats extends Module {
   public SliderSetting ws, wlr;
   public String playerNick = "";
   private final List<String> queue = new ArrayList<>();
   
   //idk where this came from it just appeared in the old src so like /shrug its legit just a copy of duels stats
   //it also doesnt requeue LOL

   public SumoStats() {
      super("Sumo Stats", ModuleCategory.sumo);

      this.registerSetting(ws = new SliderSetting("wlr", 2, 0, 10, 0.1));
      this.registerSetting(ws = new SliderSetting("ws", 4, 0, 30, 1));
   }

   public void onEnable() {
      if (mc.thePlayer != null) {
      } else {
         this.disable();
      }

   }

   public void onDisable() {
      this.queue.clear();
   }

   public void update() {
      if (!this.isDuel()) return;

      // Thanks to https://github.com/Scherso for the code from https://github.com/Scherso/Seraph

      for (ScorePlayerTeam team : Minecraft.getMinecraft().theWorld.getScoreboard().getTeams()) {
         for (String playerName : team.getMembershipCollection()) {
            if (!queue.contains(playerName) && team.getColorPrefix().equals("§7§k") && !playerName.equalsIgnoreCase(Minecraft.getMinecraft().thePlayer.getDisplayNameString())) {
               this.queue.add(playerName);
               Raven.getExecutor().execute(() -> {
                  String id = getPlayerUUID(playerName);
                  if(!id.isEmpty()){
                     getAndDisplayStatsForPlayer(id, playerName);
                  }
               });

            }
         }
      }
   }

   private void getAndDisplayStatsForPlayer(String uuid, String playerName) {

      if (Utils.URLS.hypixelApiKey.isEmpty()) {
         Utils.Player.sendMessageToSelf("&cAPI Key is empty!");
      } else {
         Utils.Profiles.DuelsStatsMode dm = (Utils.Profiles.DuelsStatsMode) DuelsStatsMode.SUMO;
         Raven.getExecutor().execute(() -> {
            PlayerProfile playerProfile = new PlayerProfile(new UUID(uuid), (Utils.Profiles.DuelsStatsMode) DuelsStatsMode.SUMO);
            playerProfile.populateStats();

            if(!playerProfile.isPlayer)return;

            if (playerProfile.nicked) {
               Utils.Player.sendMessageToSelf("&3" + playerName + " " + "&eis nicked!");
               return;
            }

            double wlr = playerProfile.losses != 0 ? Utils.Java.round((double)playerProfile.wins / (double)playerProfile.losses, 2) : (double)playerProfile.wins;
            Utils.Player.sendMessageToSelf("&7&m-------------------------");
            if (dm != Utils.Profiles.DuelsStatsMode.OVERALL) {
               Utils.Player.sendMessageToSelf("&e" + Utils.md + "&3" + dm.name());
            }

            Utils.Player.sendMessageToSelf("&eOpponent: &3" + playerName);
            Utils.Player.sendMessageToSelf("&eWins: &3" + playerProfile.wins);
            Utils.Player.sendMessageToSelf("&eLosses: &3" + playerProfile.losses);
            Utils.Player.sendMessageToSelf("&eWLR: &3" + wlr);
            Utils.Player.sendMessageToSelf("&eWS: &3" + playerProfile.winStreak);
            Utils.Player.sendMessageToSelf("&7&m-------------------------");
            if(wlr > this.wlr.getInput() || playerProfile.winStreak > this.ws.getInput()) {
            	SumoBot sb = (SumoBot) Raven.moduleManager.getModuleByName("Sumo Bot");
            	sb.reQueue();
            }
         });
      }
   }

   private boolean isDuel() {
      if (Utils.Client.isHyp()) {
         int l = 0;

         for (String s : Utils.Client.getPlayersFromScoreboard()) {
            if (s.contains("Map:")) {
               ++l;
            } else if (s.contains("Players:") && s.contains("/2")) {
               ++l;
            }
         }

         return l == 2;
      } else {
         return false;
      }
   }

   private String getPlayerUUID(String username) {
      String playerUUID = "";
      try (CloseableHttpClient client = HttpClients.createDefault()) {
         HttpGet request = new HttpGet(String.format("https://api.mojang.com/users/profiles/minecraft/%s", username));
         try (InputStream is = client.execute(request).getEntity().getContent()) {
            JsonParser parser = new JsonParser();
            JsonObject object = parser.parse(new InputStreamReader(is, StandardCharsets.UTF_8)).getAsJsonObject();
            playerUUID = object.get("id").getAsString();

         } catch (NullPointerException ex) {
            System.out.println("Null or invalid player provided by the server.");
         }
      } catch (IOException ex) {
         ex.printStackTrace();
      }

      return playerUUID;
   }
}