package keystrokesmod.client.module.modules.movement;

import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.*;
import keystrokesmod.client.module.modules.combat.Reach;
import keystrokesmod.client.module.setting.impl.DescriptionSetting;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import net.minecraft.entity.Entity;

public class KeepSprint extends Module {
   public static DescriptionSetting a;
   public static SliderSetting b;
   public static TickSetting c;

   public KeepSprint() {
      super("KeepSprint", ModuleCategory.movement);
      this.registerSetting(a = new DescriptionSetting("Default is 40% motion reduction."));
      this.registerSetting(b = new SliderSetting("Slow %", 40.0D, 0.0D, 100.0D, 1.0D));
      this.registerSetting(c = new TickSetting("Only reduce reach hits", false));
   }

   public static void sl(Entity en) {
      double dist;
      Module reach = Raven.moduleManager.getModuleByClazz(Reach.class);
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
