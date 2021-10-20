//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package me.kopamed.lunarkeystrokes.module.modules.player;

import me.kopamed.lunarkeystrokes.module.*;
import me.kopamed.lunarkeystrokes.module.Module;
import me.kopamed.lunarkeystrokes.module.setting.settings.Description;
import me.kopamed.lunarkeystrokes.module.setting.settings.Slider;
import me.kopamed.lunarkeystrokes.module.setting.settings.Tick;

public class FallSpeed extends Module {
   public static Description dc;
   public static Slider a;
   public static Tick b;

   public FallSpeed() {
      super("FallSpeed", Module.category.player, 0);
      this.registerSetting(dc = new Description("Vanilla max: 3.92"));
      this.registerSetting(a = new Slider("Motion", 5.0D, 0.0D, 8.0D, 0.1D));
      this.registerSetting(b = new Tick("Disable XZ motion", true));
   }

   public void update() {
      if ((double)mc.thePlayer.fallDistance >= 2.5D) {
         if (ModuleManager.fly.isEnabled() || ModuleManager.noFall.isEnabled()) {
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
