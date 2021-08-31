//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.module.modules.minigames;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleManager;
import keystrokesmod.module.ModuleSettingTick;
import keystrokesmod.module.modules.world.AntiBot;
import keystrokesmod.utils.HUDUtils;
import keystrokesmod.utils.ay;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemSword;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MurderMystery extends Module {
   public static ModuleSettingTick a;
   public static ModuleSettingTick b;
   public static ModuleSettingTick c;
   private static final List<EntityPlayer> mur = new ArrayList();
   private static final List<EntityPlayer> det = new ArrayList();

   public MurderMystery() {
      super("Murder Mystery", Module.category.minigames, 0);
      this.registerSetting(a = new ModuleSettingTick("Alert", true));
      this.registerSetting(b = new ModuleSettingTick("Search detectives", true));
      this.registerSetting(c = new ModuleSettingTick("Announce murderer", false));
   }

   @SubscribeEvent
   public void o(RenderWorldLastEvent e) {
      if (ay.isPlayerInGame()) {
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
                           ay.sendMessageToSelf(c4 + " &e" + en.getName() + " &3" + c6);
                        }

                        if (c.isToggled()) {
                           mc.thePlayer.sendChatMessage(en.getName() + " " + c6);
                        }
                     } else if (i instanceof ItemBow && b.isToggled() && !det.contains(en)) {
                        det.add(en);
                        String c7 = "has a bow!";
                        if (a.isToggled()) {
                           ay.sendMessageToSelf(c4 + " &e" + en.getName() + " &3" + c7);
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

               HUDUtils.ee(en, 2, 0.0D, 0.0D, rgb, false);
            }
         }
      }
   }

   private boolean imm() {
      if (ay.isHyp()) {
         if (mc.thePlayer.getWorldScoreboard() == null || mc.thePlayer.getWorldScoreboard().getObjectiveInDisplaySlot(1) == null) {
            return false;
         }

         String d = mc.thePlayer.getWorldScoreboard().getObjectiveInDisplaySlot(1).getDisplayName();
         String c2 = "MYSTERY";
         String c1 = "MURDER";
         if (!d.contains(c1) && !d.contains(c2)) {
            return false;
         }

         Iterator var2 = ay.gsl().iterator();

         while(var2.hasNext()) {
            String l = (String)var2.next();
            String s = ay.str(l);
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
