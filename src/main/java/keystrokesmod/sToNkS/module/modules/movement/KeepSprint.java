package keystrokesmod.sToNkS.module.modules.movement;

import keystrokesmod.sToNkS.module.*;
import keystrokesmod.sToNkS.module.modules.combat.Reach;
import net.minecraft.entity.Entity;

public class KeepSprint extends Module {
   public static ModuleDesc a;
   public static ModuleSettingSlider b;
   public static ModuleSettingTick c;

   public KeepSprint() {
      super("KeepSprint", Module.category.movement, 0);
      this.registerSetting(a = new ModuleDesc("Default is 40% motion reduction."));
      this.registerSetting(b = new ModuleSettingSlider("Slow %", 40.0D, 0.0D, 100.0D, 1.0D));
      this.registerSetting(c = new ModuleSettingTick("Only reduce reach hits", false));
   }

   public static void sl(Entity en) {
      double dist;
      Module reach = ModuleManager.getModuleByClazz(Reach.class);
      if (c.isToggled() && reach != null && reach.isEnabled() && !mc.thePlayer.capabilities.isCreativeMode) {
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
