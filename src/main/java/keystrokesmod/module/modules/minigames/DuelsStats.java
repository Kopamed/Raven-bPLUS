//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.module.modules.minigames;

import java.util.ArrayList;
import java.util.List;

import keystrokesmod.*;
import keystrokesmod.main.Ravenb3;
import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleDesc;
import keystrokesmod.module.ModuleSetting;
import keystrokesmod.module.ModuleSetting2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DuelsStats extends Module {
   public static ModuleSetting2 value;
   public static ModuleDesc moduleDesc;
   public static ModuleSetting a;
   public static ModuleSetting threatLevel;
   public static String nk = "";
   private String ign = "";
   private String en = "";
   private static final String[] thr_lvl;
   private List<String> q = new ArrayList();

   public DuelsStats() {
      super("Duels Stats", Module.category.minigames, 0);
      this.registerSetting(value = new ModuleSetting2("Value", 1.0D, 1.0D, 7.0D, 1.0D));
      this.registerSetting(moduleDesc = new ModuleDesc("Mode: OVERALL"));
      this.registerSetting(a = new ModuleSetting("Send ign on join", false));
      this.registerSetting(threatLevel = new ModuleSetting("Threat Level", true));
   }

   public void onEnable() {
      if (mc.thePlayer != null) {
         this.ign = mc.thePlayer.getName();
      } else {
         this.disable();
      }

   }

   public void onDisable() {
      this.en = "";
      this.q.clear();
   }

   public void guiUpdate() {
      moduleDesc.setDesc(ay.md + ProfileUtils.DM.values()[(int)(value.getInput() - 1.0D)].name());
   }

   public void update() {
      if (this.id() && this.en.isEmpty()) {
         List<EntityPlayer> pl = mc.theWorld.playerEntities;
         pl.remove(mc.thePlayer);

         for (EntityPlayer p : pl) {
            String n = p.getName();
            if (!n.equals(this.ign) && !n.equals(nk) && !this.q.contains(n) && p.getDisplayName().getUnformattedText().contains("§k")) {
               this.ef(n);
               break;
            }
         }
      }

   }

   @SubscribeEvent
   public void onMessageRecieved(ClientChatReceivedEvent c) {
      if (ay.isPlayerInGame() && this.id()) {
         String s = ay.str(c.message.getUnformattedText());
         if (s.contains(" ")) {
            String[] sp = s.split(" ");
            String n;
            if (sp.length == 4 && sp[1].equals("has") && sp[2].equals("joined") && sp[3].equals("(2/2)!")) {
               n = sp[0];
               if (!n.equals(this.ign) && !n.equals(nk) && this.en.isEmpty()) {
                  this.q.remove(n);
                  this.ef(n);
               }
            } else if (sp.length == 3 && sp[1].equals("has") && sp[2].equals("quit!")) {
               n = sp[0];
               if (this.en.equals(n)) {
                  this.en = "";
               }

               this.q.add(n);
            }
         }

      }
   }

   @SubscribeEvent
   public void onEntityJoin(EntityJoinWorldEvent j) {
      if (j.entity == mc.thePlayer) {
         this.en = "";
         this.q.clear();
      }

   }

   private void ef(String n) {
      this.en = n;
      if (a.isToggled()) {
         ay.sm("&eOpponent found: " + "&3" + n);
      }

      if (URLUtils.k.isEmpty()) {
         ay.sm("&cAPI Key is empty!");
      } else {
         ProfileUtils.DM dm = ProfileUtils.DM.values()[(int)(value.getInput() - 1.0D)];
         Ravenb3.getExecutor().execute(() -> {
            int[] s = ProfileUtils.getHypixelStats(n, dm);
            if (s != null) {
               if (s[0] == -1) {
                  ay.sm("&3" + n + " " + "&eis nicked!");
                  return;
               }

               double wlr = s[1] != 0 ? ay.rnd((double)s[0] / (double)s[1], 2) : (double)s[0];
               ay.sm("&7&m-------------------------");
               if (dm != ProfileUtils.DM.OVERALL) {
                  ay.sm("&e" + ay.md + "&3" + dm.name());
               }

               ay.sm("&eOpponent: &3" + n);
               ay.sm("&eWins: &3" + s[0]);
               ay.sm("&eLosses: &3" + s[1]);
               ay.sm("&eWLR: &3" + wlr);
               ay.sm("&eWS: &3" + s[2]);
               if (threatLevel.isToggled()) {
                  ay.sm("&eThreat: &3" + gtl(s[0], s[1], wlr, s[2]));
               }

               ay.sm("&7&m-------------------------");
            } else {
               ay.sm("&cThere was an error.");
            }

         });
      }
   }

   private boolean id() {
      if (ay.isHyp()) {
         int l = 0;

         for (String s : ay.gsl()) {
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

   public static String gtl(int w, int l, double wlr, int ws) {
      int t = 0;
      int m = w + l;
      if (m <= 13) {
         t += 2;
      }

      if (ws >= 30) {
         t += 9;
      } else if (ws >= 15) {
         t += 7;
      } else if (ws >= 8) {
         t += 5;
      } else if (ws >= 4) {
         t += 3;
      } else if (ws >= 1) {
         ++t;
      }

      if (wlr >= 20.0D) {
         t += 8;
      } else if (wlr >= 10.0D) {
         t += 5;
      } else if (wlr >= 5.0D) {
         t += 4;
      } else if (wlr >= 3.0D) {
         t += 2;
      } else if (wlr >= 0.8D) {
         ++t;
      }

      if (w >= 20000) {
         t += 4;
      } else if (w >= 10000) {
         t += 3;
      } else if (w >= 5000) {
         t += 2;
      } else if (w >= 1000) {
         ++t;
      }

      if (l == 0) {
         if (w == 0) {
            t += 3;
         } else {
            t += 4;
         }
      } else if (l <= 10 && wlr >= 4.0D) {
         t += 2;
      }

      String thr;
      if (t == 0) {
         thr = thr_lvl[4];
      } else if (t <= 3) {
         thr = thr_lvl[3];
      } else if (t <= 6) {
         thr = thr_lvl[2];
      } else if (t <= 10) {
         thr = thr_lvl[1];
      } else {
         thr = thr_lvl[0];
      }

      return thr;
   }

   static {
      thr_lvl = new String[]{"&4VERY HIGH", "&cHIGH", "&6MODERATE", "&aLOW", "&2VERY LOW"};
   }
}
