//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.module.modules.movement;

import keystrokesmod.*;
import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleDesc;
import keystrokesmod.module.ModuleSetting;
import keystrokesmod.module.ModuleSetting2;
import org.lwjgl.input.Keyboard;

public class Speed extends Module {
   public static ModuleDesc dc;
   public static ModuleSetting2 a;
   public static ModuleSetting b;

   public Speed() {
      super(new char[]{'S', 'p', 'e', 'e', 'd'}, Module.category.movement, 0);
      this.registerSetting(dc = new ModuleDesc(new String(new char[]{'H', 'y', 'p', 'i', 'x', 'e', 'l', ' ', 'm', 'a', 'x', ':', ' ', '1', '.', '1', '3'})));
      this.registerSetting(a = new ModuleSetting2(new char[]{'S', 'p', 'e', 'e', 'd'}, 1.2D, 1.0D, 1.5D, 0.01D));
      this.registerSetting(b = new ModuleSetting(new char[]{'S', 't', 'r', 'a', 'f', 'e', ' ', 'o', 'n', 'l', 'y'}, false));
   }

   public void update() {
      double csp = ay.gs();
      if (csp != 0.0D) {
         if (mc.thePlayer.onGround && !mc.thePlayer.capabilities.isFlying) {
            if (!b.isToggled() || mc.thePlayer.moveStrafing != 0.0F) {
               if (mc.thePlayer.hurtTime != mc.thePlayer.maxHurtTime || mc.thePlayer.maxHurtTime <= 0) {
                  if (!Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode())) {
                     double val = a.getInput() - (a.getInput() - 1.0D) * 0.5D;
                     ay.ss(csp * val, true);
                  }
               }
            }
         }
      }
   }
}
