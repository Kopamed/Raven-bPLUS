package me.kopamed.raven.bplus.client.feature.module.modules.minigames;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.feature.module.ModuleCategory;
import me.kopamed.raven.bplus.helper.manager.ModuleManager;
import me.kopamed.raven.bplus.client.feature.setting.settings.BooleanSetting;
import me.kopamed.raven.bplus.client.feature.module.modules.world.AntiBot;
import me.kopamed.raven.bplus.helper.utils.Utils;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemSword;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MurderMystery extends Module {
   public static BooleanSetting alertMurderers;
   public static BooleanSetting searchDetectives;
   public static BooleanSetting announceMurder;
   private static final List<EntityPlayer> mur = new ArrayList();
   private static final List<EntityPlayer> det = new ArrayList();

   public MurderMystery() {
      super("Murder Mystery", ModuleCategory.Misc);
      this.registerSetting(alertMurderers = new BooleanSetting("Alert", true));
      this.registerSetting(searchDetectives = new BooleanSetting("Search detectives", true));
      this.registerSetting(announceMurder = new BooleanSetting("Announce murderer", false));
   }

   @SubscribeEvent
   public void o(RenderWorldLastEvent e) {
      if (Utils.Player.isPlayerInGame()) {
         if (ModuleManager.playerESP.isToggled()) {
            ModuleManager.playerESP.disable();
         }

         if (ModuleManager.propHunt.isToggled()) {
            ModuleManager.propHunt.disable();
         }

         if (!this.inMMGame()) {
            this.c();
         } else {
            Iterator entityPlayerIterator = mc.theWorld.playerEntities.iterator();

            while(true) {
               EntityPlayer entity;
               do {
                  do {
                     do {
                        if (!entityPlayerIterator.hasNext()) {
                           return;
                        }

                        entity = (EntityPlayer)entityPlayerIterator.next();
                     } while(entity == mc.thePlayer);
                  } while(entity.isInvisible());
               } while(AntiBot.bot(entity));
               String c4 = "&7[&cALERT&7]";
               if (entity.getHeldItem() != null && entity.getHeldItem().hasDisplayName()) {
                  Item i = entity.getHeldItem().getItem();
                  if (i instanceof ItemSword || i instanceof ItemAxe || entity.getHeldItem().getDisplayName().contains("aKnife")) {

                     if (!mur.contains(entity)) {
                        mur.add(entity);
                        String c6 = "is a murderer!";
                        if (alertMurderers.isToggled()) {
                           String c5 = "note.pling";
                           mc.thePlayer.playSound(c5, 1.0F, 1.0F);
                           Utils.Player.sendMessageToSelf(c4 + " &e" + entity.getName() + " &3" + c6);
                        }

                        if (announceMurder.isToggled()) {
                           String msg = Utils.Java.randomChoice(new String[] {entity.getName() + " " + c6, entity.getName()});
                           mc.thePlayer.sendChatMessage(msg);
                        }
                     }
                  } else if (i instanceof ItemBow && searchDetectives.isToggled() && !det.contains(entity)) {
                     det.add(entity);
                     String c7 = "has a bow!";
                     if (alertMurderers.isToggled()) {
                        Utils.Player.sendMessageToSelf(c4 + " &e" + entity.getName() + " &3" + c7);
                     }

                     if (announceMurder.isToggled()) {
                        mc.thePlayer.sendChatMessage(entity.getName() + " " + c7);
                     }

                  }
               }

               int rgb = Color.cyan.getRGB();
               if (mur.contains(entity)) {
                  rgb = Color.red.getRGB();
               } else if (det.contains(entity)) {
                  rgb = Color.green.getRGB();
               }

               Utils.HUD.drawBoxAroundEntity(entity, 2, 0.0D, 0.0D, rgb, false);
            }
         }
      }
   }

   private boolean inMMGame() {
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
