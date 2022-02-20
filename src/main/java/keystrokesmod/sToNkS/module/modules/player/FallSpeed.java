package keystrokesmod.sToNkS.module.modules.player;

import keystrokesmod.sToNkS.module.*;
import keystrokesmod.sToNkS.module.modules.movement.Fly;

public class FallSpeed extends Module {
   public static ModuleDesc dc;
   public static ModuleSettingSlider a;
   public static ModuleSettingTick b;

   public FallSpeed() {
      super("FallSpeed", Module.category.player, 0);
      this.registerSetting(dc = new ModuleDesc("Vanilla max: 3.92"));
      this.registerSetting(a = new ModuleSettingSlider("Motion", 5.0D, 0.0D, 8.0D, 0.1D));
      this.registerSetting(b = new ModuleSettingTick("Disable XZ motion", true));
   }

   public void update() {
      if ((double)mc.thePlayer.fallDistance >= 2.5D) {
         Module fly = ModuleManager.getModuleByClazz(Fly.class);
         Module noFall = ModuleManager.getModuleByClazz(NoFall.class);

         if (
              (fly != null && fly.isEnabled()) ||
              (noFall != null && noFall.isEnabled())
         ) {
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
