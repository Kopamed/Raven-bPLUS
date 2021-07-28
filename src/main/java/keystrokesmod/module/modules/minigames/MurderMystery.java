//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.module.modules.minigames;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import keystrokesmod.*;
import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleManager;
import keystrokesmod.module.ModuleSettingTick;
import keystrokesmod.module.modules.world.AntiBot;
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
   private final String c1 = "MURDER";
   private final String c2 = "MYSTERY";
   private final String c3 = "Role:";
   private final String c4 = "&7[&cALERT&7]";
   private final String c5 = "note.pling";
   private final String c6 = "is a murderer!";
   private final String c7 = "has a bow!";

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
                     if (!mur.contains(en)) {
                        mur.add(en);
                        if (a.isToggled()) {
                           mc.thePlayer.playSound(this.c5, 1.0F, 1.0F);
                           ay.sendMessageToSelf(this.c4 + " &e" + en.getName() + " &3" + this.c6);
                        }

                        if (c.isToggled()) {
                           mc.thePlayer.sendChatMessage(en.getName() + " " + this.c6);
                        }
                     } else if (i instanceof ItemBow && b.isToggled() && !det.contains(en)) {
                        det.add(en);
                        if (a.isToggled()) {
                           ay.sendMessageToSelf(this.c4 + " &e" + en.getName() + " &3" + this.c7);
                        }

                        if (c.isToggled()) {
                           mc.thePlayer.sendChatMessage(en.getName() + " " + this.c7);
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

               ru.ee(en, 2, 0.0D, 0.0D, rgb, false);
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
         if (!d.contains(this.c1) && !d.contains(this.c2)) {
            return false;
         }

         Iterator var2 = ay.gsl().iterator();

         while(var2.hasNext()) {
            String l = (String)var2.next();
            String s = ay.str(l);
            if (s.contains(this.c3)) {
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
