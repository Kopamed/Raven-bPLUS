//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package me.kopamed.raven.bplus.client.feature.module.modules.player;

import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.feature.module.ModuleCategory;
import me.kopamed.raven.bplus.helper.manager.ModuleManager;
import me.kopamed.raven.bplus.client.feature.setting.settings.DescriptionSetting;
import me.kopamed.raven.bplus.client.feature.setting.settings.NumberSetting;
import me.kopamed.raven.bplus.client.feature.setting.settings.BooleanSetting;

public class FallSpeed extends Module {
   public static DescriptionSetting dc;
   public static NumberSetting a;
   public static BooleanSetting b;

   public FallSpeed() {
      super("FallSpeed", ModuleCategory.Player, 0);
      this.registerSetting(dc = new DescriptionSetting("Vanilla max: 3.92"));
      this.registerSetting(a = new NumberSetting("Motion", 5.0D, 0.0D, 8.0D, 0.1D));
      this.registerSetting(b = new BooleanSetting("Disable XZ motion", true));
   }

   public void update() {
      if ((double)mc.thePlayer.fallDistance >= 2.5D) {
         if (ModuleManager.fly.isToggled() || ModuleManager.noFall.isToggled()) {
            return;
         }

         if (mc.thePlayer.capabilities.isCreativeMode || mc.thePlayer.capabilities.isFlying) {
            return;
         }

         if (mc.thePlayer.isOnLadder() || mc.thePlayer.isInWater() || mc.thePlayer.isInLava()) {
            return;
         }

         mc.thePlayer.motionY = -a.getInput();
         if (b.isToggled()) {
            mc.thePlayer.motionX = mc.thePlayer.motionZ = 0.0D;
         }
      }

   }
}
