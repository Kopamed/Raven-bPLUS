//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.module.modules.player;

import keystrokesmod.module.*;

public class FallSpeed extends Module {
   public static ModuleDesc dc;
   public static ModuleSetting2 a;
   public static ModuleSetting b;

   public FallSpeed() {
      super(new char[]{'F', 'a', 'l', 'l', 'S', 'p', 'e', 'e', 'd'}, Module.category.player, 0);
      this.registerSetting(dc = new ModuleDesc(new String(new char[]{'V', 'a', 'n', 'i', 'l', 'l', 'a', ' ', 'm', 'a', 'x', ':', ' ', '3', '.', '9', '2'})));
      this.registerSetting(a = new ModuleSetting2(new char[]{'M', 'o', 't', 'i', 'o', 'n'}, 5.0D, 0.0D, 8.0D, 0.1D));
      this.registerSetting(b = new ModuleSetting(new char[]{'D', 'i', 's', 'a', 'b', 'l', 'e', ' ', 'X', 'Z', ' ', 'm', 'o', 't', 'i', 'o', 'n'}, true));
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
