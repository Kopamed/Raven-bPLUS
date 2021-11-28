package me.kopamed.raven.bplus.client.feature.module.modules.combat;

import java.util.Iterator;

import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.feature.module.ModuleCategory;
import me.kopamed.raven.bplus.client.feature.setting.settings.NumberSetting;
import me.kopamed.raven.bplus.helper.utils.Utils;
import me.kopamed.raven.bplus.client.feature.setting.settings.BooleanSetting;
import me.kopamed.raven.bplus.client.feature.module.modules.world.AntiBot;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFishingRod;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RodAimbot extends Module {
   public static NumberSetting a;
   public static NumberSetting b;
   public static BooleanSetting c;

   public RodAimbot() {
      super("RodAimbot", ModuleCategory.Combat, 0);
      this.registerSetting(a = new NumberSetting("FOV", 90.0D, 15.0D, 360.0D, 1.0D));
      this.registerSetting(b = new NumberSetting("Distance", 4.5D, 1.0D, 10.0D, 0.5D));
      this.registerSetting(c = new BooleanSetting("Aim invis", false));
   }

   @SubscribeEvent
   public void x(MouseEvent ev) {
      if (ev.button == 1 && ev.buttonstate && Utils.Player.isPlayerInGame() && mc.currentScreen == null) {
         if (mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemFishingRod && mc.thePlayer.fishEntity == null) {
            Entity en = this.gE();
            if (en != null) {
               ev.setCanceled(true);
               Utils.Player.aim(en, -7.0F, true);
               mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getCurrentEquippedItem());
            }
         }

      }
   }

   public Entity gE() {
      int f = (int)a.getInput();
      Iterator var2 = mc.theWorld.playerEntities.iterator();

      EntityPlayer en;
      do {
         do {
            do {
               do {
                  if (!var2.hasNext()) {
                     return null;
                  }

                  en = (EntityPlayer)var2.next();
               } while(en == mc.thePlayer);
            } while(en.deathTime != 0);
         } while(!c.isToggled() && en.isInvisible());
      } while((double)mc.thePlayer.getDistanceToEntity(en) > b.getInput() || AntiBot.bot(en) || !Utils.Player.isEntityInFOV(en, (float)f));

      return en;
   }
}
