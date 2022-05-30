package keystrokesmod.client.module.modules.minigames;

import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.ModuleManager;
import keystrokesmod.client.module.TickSetting;
import keystrokesmod.client.module.modules.render.PlayerESP;
import keystrokesmod.client.module.modules.world.AntiBot;
import keystrokesmod.client.utils.Utils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemSword;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import keystrokesmod.client.lib.fr.jmraich.rax.event.FMLEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MurderMystery extends Module {
   public static TickSetting a;
   public static TickSetting b;
   public static TickSetting c;
   private static final List<EntityPlayer> mur = new ArrayList<>();
   private static final List<EntityPlayer> det = new ArrayList<>();

   public MurderMystery() {
      super("Murder Mystery", Module.category.minigames, 0);
      this.registerSetting(a = new TickSetting("Alert", true));
      this.registerSetting(b = new TickSetting("Search detectives", true));
      this.registerSetting(c = new TickSetting("Announce murderer", false));
   }

   @FMLEvent
   public void onRenderWorldLast(RenderWorldLastEvent e) {
      if (Utils.Player.isPlayerInGame()) {
         Module playerESP = ModuleManager.getModuleByClazz(PlayerESP.class);
         if (playerESP != null && playerESP.isEnabled()) {
            playerESP.disable();
         }

         if (!this.imm()) {
            this.c();
         } else {
            Iterator var2 = mc.theWorld.playerEntities.iterator();

            while(true) {
               EntityPlayer en;
               do {
                  do {
                     do {
                        if (!var2.hasNext()) {
                           return;
                        }

                        en = (EntityPlayer)var2.next();
                     } while(en == mc.thePlayer);
                  } while(en.isInvisible());
               } while(AntiBot.bot(en));

               if (en.getHeldItem() != null && en.getHeldItem().hasDisplayName()) {
                  Item i = en.getHeldItem().getItem();
                  if (i instanceof ItemSword || i instanceof ItemAxe || en.getHeldItem().getDisplayName().contains("aKnife")) {
                     String c4 = "&7[&cALERT&7]";
                     if (!mur.contains(en)) {
                        mur.add(en);
                        String c6 = "is a murderer!";
                        if (a.isToggled()) {
                           String c5 = "note.pling";
                           mc.thePlayer.playSound(c5, 1.0F, 1.0F);
                           Utils.Player.sendMessageToSelf(c4 + " &e" + en.getName() + " &3" + c6);
                        }

                        if (c.isToggled()) {
                           mc.thePlayer.sendChatMessage(en.getName() + " " + c6);
                        }
                     } else if (i instanceof ItemBow && b.isToggled() && !det.contains(en)) {
                        det.add(en);
                        String c7 = "has a bow!";
                        if (a.isToggled()) {
                           Utils.Player.sendMessageToSelf(c4 + " &e" + en.getName() + " &3" + c7);
                        }

                        if (c.isToggled()) {
                           mc.thePlayer.sendChatMessage(en.getName() + " " + c7);
                        }
                     }
                  }
               }

               int rgb = Color.green.getRGB();
               if (mur.contains(en)) {
                  rgb = Color.red.getRGB();
               } else if (det.contains(en)) {
                  rgb = Color.orange.getRGB();
               }

               Utils.HUD.ee(en, 2, 0.0D, 0.0D, rgb, false);
            }
         }
      }
   }

   private boolean imm() {
      if (Utils.Client.isHyp()) {
         if (mc.thePlayer.getWorldScoreboard() == null || mc.thePlayer.getWorldScoreboard().getObjectiveInDisplaySlot(1) == null) {
            return false;
         }

         String d = mc.thePlayer.getWorldScoreboard().getObjectiveInDisplaySlot(1).getDisplayName();
         String c2 = "MYSTERY";
         String c1 = "MURDER";
         if (!d.contains(c1) && !d.contains(c2)) {
            return false;
         }

         for (String l : Utils.Client.getPlayersFromScoreboard()) {
            String s = Utils.Java.str(l);
            String c3 = "Role:";
            if (s.contains(c3)) {
               return true;
            }
         }
      }

      return false;
   }

   private void c() {
      mur.clear();
      det.clear();
   }
}
