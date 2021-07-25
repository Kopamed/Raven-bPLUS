//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.module.modules.player;

import keystrokesmod.module.*;
import keystrokesmod.module.Module;

public class FallSpeed extends Module {
   public static ModuleDesc dc;
   public static ModuleSetting2 a;
   public static ModuleSetting b;

   public FallSpeed() {
      super("FallSpeed", Module.category.player, 0);
      this.registerSetting(dc = new ModuleDesc("Vanilla max: 3.92"));
      this.registerSetting(a = new ModuleSetting2("Motion", 5.0D, 0.0D, 8.0D, 0.1D));
      this.registerSetting(b = new ModuleSetting("Disable XZ motion", true));
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
