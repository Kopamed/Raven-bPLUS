//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package me.kopamed.lunarkeystrokes.module.modules.minigames;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.kopamed.lunarkeystrokes.module.Module;
import me.kopamed.lunarkeystrokes.module.ModuleManager;
import me.kopamed.lunarkeystrokes.module.setting.settings.Tick;
import me.kopamed.lunarkeystrokes.module.modules.world.AntiBot;
import me.kopamed.lunarkeystrokes.utils.Utils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemSword;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MurderMystery extends Module {
   public static Tick a;
   public static Tick b;
   public static Tick c;
   private static final List<EntityPlayer> mur = new ArrayList();
   private static final List<EntityPlayer> det = new ArrayList();

   public MurderMystery() {
      super("Murder Mystery", Module.category.minigames, 0);
      this.registerSetting(a = new Tick("Alert", true));
      this.registerSetting(b = new Tick("Search detectives", true));
      this.registerSetting(c = new Tick("Announce murderer", false));
   }

   @SubscribeEvent
   public void o(RenderWorldLastEvent e) {
      if (Utils.Player.isPlayerInGame()) {
         if (ModuleManager.playerESP.isEnabled()) {
            ModuleManager.playerESP.disable();
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

         Iterator var2 = Utils.Client.getPlayersFromScoreboard().iterator();

         while(var2.hasNext()) {
            String l = (String)var2.next();
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
