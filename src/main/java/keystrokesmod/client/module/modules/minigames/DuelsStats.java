package keystrokesmod.client.module.modules.minigames;

import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.ComboSetting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.Utils;
import keystrokesmod.client.utils.profile.PlayerProfile;
import keystrokesmod.client.utils.profile.UUID;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class DuelsStats extends Module {
   public static ComboSetting selectedGameMode;
   public static TickSetting sendIgnOnJoin;
   public static String playerNick = "";
   private String ign = "";
   private String opponentName = "";
   private final List<String> queue = new ArrayList<>();

   public DuelsStats() {
      super("Duels Stats", ModuleCategory.minigames);

      this.registerSetting(selectedGameMode = new ComboSetting("Stats for mode:", Utils.Profiles.DuelsStatsMode.OVERALL));
      this.registerSetting(sendIgnOnJoin = new TickSetting("Send ign on join", false));
   }

   public void onEnable() {
      if (mc.thePlayer != null) {
         this.ign = mc.thePlayer.getUniqueID().toString();
      } else {
         this.disable();
      }

   }

   public void onDisable() {
      this.opponentName = "";
      this.queue.clear();
   }

   public void update() {
      if (this.isDuel() && this.opponentName.isEmpty()) {
         List<EntityPlayer> pl = mc.theWorld.playerEntities;
         pl.remove(mc.thePlayer);

         for (EntityPlayer player : pl) {
            String playerUUID = player.getUniqueID().toString();
            if (!playerUUID.equals(this.ign) && !playerUUID.equals(playerNick) && !this.queue.contains(playerUUID) && player.getDisplayName().getUnformattedText().contains("§k")) {
               this.getAndDisplayStatsForPlayer(playerUUID);
               break;
            }
         }
      }

   }

   //@SubscribeEvent
   public void onMessageReceived(ClientChatReceivedEvent c) {
      if (Utils.Player.isPlayerInGame() && this.isDuel()) {
         String s = Utils.Java.str(c.message.getUnformattedText());
         if (s.contains(" ")) {
            String[] sp = s.split(" ");
            String n;
            if (sp.length == 4 && sp[1].equals("has") && sp[2].equals("joined") && sp[3].equals("(2/2)!")) {
               n = sp[0];
               if (!n.equals(this.ign) && !n.equals(playerNick) && this.opponentName.isEmpty()) {
                  this.queue.remove(n);
                  this.getAndDisplayStatsForPlayer("onmessage");
               }
            } else if (sp.length == 3 && sp[1].equals("has") && sp[2].equals("quit!")) {
               n = sp[0];
               if (this.opponentName.equals(n)) {
                  this.opponentName = "";
               }

               this.queue.add(n);
            }
         }

      }
   }

   @SubscribeEvent
   public void onEntityJoin(EntityJoinWorldEvent j) {
      if (j.entity == mc.thePlayer) {
         this.opponentName = "";
         this.queue.clear();
      }

   }

   private void getAndDisplayStatsForPlayer(String uuid) {
      this.opponentName = uuid;

      if (Utils.URLS.hypixelApiKey.isEmpty()) {
         Utils.Player.sendMessageToSelf("&cAPI Key is empty!");
      } else {
         Utils.Profiles.DuelsStatsMode dm = (Utils.Profiles.DuelsStatsMode) selectedGameMode.getMode();
         Raven.getExecutor().execute(() -> {
            PlayerProfile playerProfile = new PlayerProfile(new UUID(uuid), (Utils.Profiles.DuelsStatsMode) selectedGameMode.getMode());
            playerProfile.populateStats();

            if (playerProfile.nicked) {
               //Utils.Player.sendMessageToSelf("&3" + playerProfile.uuid + " " + "&eis nicked!");
               Utils.Player.sendMessageToSelf("&3Hypixel patched this. This will be fixed in future versions");
               return;
            }

            if (sendIgnOnJoin.isToggled()) {
               Utils.Player.sendMessageToSelf("&eOpponent found: " + "&3" + playerProfile.inGameName);
            }

            double wlr = playerProfile.losses != 0 ? Utils.Java.round((double)playerProfile.wins / (double)playerProfile.losses, 2) : (double)playerProfile.wins;
            Utils.Player.sendMessageToSelf("&7&m-------------------------");
            if (dm != Utils.Profiles.DuelsStatsMode.OVERALL) {
               Utils.Player.sendMessageToSelf("&e" + Utils.md + "&3" + dm.name());
            }

            Utils.Player.sendMessageToSelf("&eOpponent: &3" + uuid);
            Utils.Player.sendMessageToSelf("&eWins: &3" + playerProfile.wins);
            Utils.Player.sendMessageToSelf("&eLosses: &3" + playerProfile.losses);
            Utils.Player.sendMessageToSelf("&eWLR: &3" + wlr);
            Utils.Player.sendMessageToSelf("&eWS: &3" + playerProfile.winStreak);

            Utils.Player.sendMessageToSelf("&7&m-------------------------");

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
}
