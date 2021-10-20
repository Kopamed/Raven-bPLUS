//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package me.kopamed.lunarkeystrokes.module.modules.movement;

import me.kopamed.lunarkeystrokes.module.Module;
import me.kopamed.lunarkeystrokes.module.setting.settings.Description;
import me.kopamed.lunarkeystrokes.module.setting.settings.Slider;

public class NoSlow extends Module {
   public static Description a;
   public static Description c;
   public static Slider b;

   public NoSlow() {
      super("NoSlow", Module.category.movement, 0);
      this.registerSetting(a = new Description("Default is 80% motion reduction."));
      this.registerSetting(c = new Description("Hypixel max: 22%"));
      this.registerSetting(b = new Slider("Slow %", 80.0D, 0.0D, 80.0D, 1.0D));
   }

   public static void sl() {
      float val = (100.0F - (float)b.getInput()) / 100.0F;
      mc.thePlayer.movementInput.moveStrafe *= val;
      mc.thePlayer.movementInput.moveForward *= val;
   }
}
