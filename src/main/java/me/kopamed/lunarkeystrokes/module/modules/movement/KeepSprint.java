//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package me.kopamed.lunarkeystrokes.module.modules.movement;

import me.kopamed.lunarkeystrokes.module.*;
import me.kopamed.lunarkeystrokes.module.Module;
import me.kopamed.lunarkeystrokes.module.setting.settings.Description;
import me.kopamed.lunarkeystrokes.module.setting.settings.Slider;
import me.kopamed.lunarkeystrokes.module.setting.settings.Tick;
import net.minecraft.entity.Entity;

public class KeepSprint extends Module {
   public static Description a;
   public static Slider b;
   public static Tick c;

   public KeepSprint() {
      super("KeepSprint", Module.category.movement, 0);
      this.registerSetting(a = new Description("Default is 40% motion reduction."));
      this.registerSetting(b = new Slider("Slow %", 40.0D, 0.0D, 100.0D, 1.0D));
      this.registerSetting(c = new Tick("Only reduce reach hits", false));
   }

   public static void sl(Entity en) {
      double dist;
      if (c.isToggled() && ModuleManager.reach.isEnabled() && !mc.thePlayer.capabilities.isCreativeMode) {
         dist = mc.objectMouseOver.hitVec.distanceTo(mc.getRenderViewEntity().getPositionEyes(1.0F));
         double val;
         if (dist > 3.0D) {
            val = (100.0D - (double)((float)b.getInput())) / 100.0D;
         } else {
            val = 0.6D;
         }

         mc.thePlayer.motionX *= val;
         mc.thePlayer.motionZ *= val;
      } else {
         dist = (100.0D - (double)((float)b.getInput())) / 100.0D;
         mc.thePlayer.motionX *= dist;
         mc.thePlayer.motionZ *= dist;
      }
   }
}
