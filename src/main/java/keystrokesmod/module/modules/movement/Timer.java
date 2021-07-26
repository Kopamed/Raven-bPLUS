//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.module.modules.movement;

import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleSettingSlider;
import keystrokesmod.ay;
import keystrokesmod.module.ModuleSettingTick;
import keystrokesmod.module.modules.ClickGui;

public class Timer extends Module {
   public static ModuleSettingSlider a;
   public static ModuleSettingTick b;

   public Timer() {
      super("Timer", Module.category.movement, 0);
      a = new ModuleSettingSlider("Speed", 1.0D, 0.5D, 2.5D, 0.01D);
      b = new ModuleSettingTick("Strafe only", false);
      this.registerSetting(a);
      this.registerSetting(b);
   }

   public void update() {
      if (!(mc.currentScreen instanceof ClickGui)) {
         if (b.isToggled() && mc.thePlayer.moveStrafing == 0.0F) {
            ay.rt();
            return;
         }

         ay.gt().timerSpeed = (float)a.getInput();
      } else {
         ay.rt();
      }

   }

   public void onDisable() {
      ay.rt();
   }
}
