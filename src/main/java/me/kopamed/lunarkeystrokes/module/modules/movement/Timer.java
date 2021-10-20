//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package me.kopamed.lunarkeystrokes.module.modules.movement;

import me.kopamed.lunarkeystrokes.module.Module;
import me.kopamed.lunarkeystrokes.module.setting.settings.Slider;
import me.kopamed.lunarkeystrokes.utils.Utils;
import me.kopamed.lunarkeystrokes.module.setting.settings.Tick;
import me.kopamed.lunarkeystrokes.clickgui.raven.ClickGui;

public class Timer extends Module {
   public static Slider a;
   public static Tick b;

   public Timer() {
      super("Timer", Module.category.movement, 0);
      a = new Slider("Speed", 1.0D, 0.5D, 2.5D, 0.01D);
      b = new Tick("Strafe only", false);
      this.registerSetting(a);
      this.registerSetting(b);
   }

   public void update() {
      if (!(mc.currentScreen instanceof ClickGui)) {
         if (b.isToggled() && mc.thePlayer.moveStrafing == 0.0F) {
            Utils.Client.resetTimer();
            return;
         }

         Utils.Client.getTimer().timerSpeed = (float)a.getInput();
      } else {
         Utils.Client.resetTimer();
      }

   }

   public void onDisable() {
      Utils.Client.resetTimer();
   }
}
