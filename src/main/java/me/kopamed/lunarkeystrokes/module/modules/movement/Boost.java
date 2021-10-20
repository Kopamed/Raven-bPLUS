//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package me.kopamed.lunarkeystrokes.module.modules.movement;

import me.kopamed.lunarkeystrokes.module.Module;
import me.kopamed.lunarkeystrokes.module.setting.settings.Description;
import me.kopamed.lunarkeystrokes.module.ModuleManager;
import me.kopamed.lunarkeystrokes.module.setting.settings.Slider;
import me.kopamed.lunarkeystrokes.utils.Utils;

public class Boost extends Module {
   public static Description c;
   public static Slider a;
   public static Slider b;
   private int i = 0;
   private boolean t = false;

   public Boost() {
      super("Boost", Module.category.movement, 0);
      this.registerSetting(c = new Description("20 ticks are in 1 second"));
      this.registerSetting(a = new Slider("Multiplier", 2.0D, 1.0D, 3.0D, 0.05D));
      this.registerSetting(b = new Slider("Time (ticks)", 15.0D, 1.0D, 80.0D, 1.0D));
   }

   public void onEnable() {
      if (ModuleManager.timer.isEnabled()) {
         this.t = true;
         ModuleManager.timer.disable();
      }

   }

   public void onDisable() {
      this.i = 0;
      if (Utils.Client.getTimer().timerSpeed != 1.0F) {
         Utils.Client.resetTimer();
      }

      if (this.t) {
         ModuleManager.timer.enable();
      }

      this.t = false;
   }

   public void update() {
      if (this.i == 0) {
         this.i = mc.thePlayer.ticksExisted;
      }

      Utils.Client.getTimer().timerSpeed = (float)a.getInput();
      if ((double)this.i == (double)mc.thePlayer.ticksExisted - b.getInput()) {
         Utils.Client.resetTimer();
         this.disable();
      }

   }
}
