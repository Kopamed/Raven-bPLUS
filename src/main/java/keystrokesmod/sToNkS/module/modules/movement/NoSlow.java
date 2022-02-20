package keystrokesmod.sToNkS.module.modules.movement;

import keystrokesmod.sToNkS.module.Module;
import keystrokesmod.sToNkS.module.ModuleDesc;
import keystrokesmod.sToNkS.module.ModuleSettingSlider;

public class NoSlow extends Module {
   public static ModuleDesc a;
   public static ModuleDesc c;
   public static ModuleSettingSlider b;

   public NoSlow() {
      super("NoSlow", Module.category.movement, 0);
      this.registerSetting(a = new ModuleDesc("Default is 80% motion reduction."));
      this.registerSetting(c = new ModuleDesc("Hypixel max: 22%"));
      this.registerSetting(b = new ModuleSettingSlider("Slow %", 80.0D, 0.0D, 80.0D, 1.0D));
   }

   public static void sl() {
      float val = (100.0F - (float)b.getInput()) / 100.0F;
      mc.thePlayer.movementInput.moveStrafe *= val;
      mc.thePlayer.movementInput.moveForward *= val;
   }
}
