package keystrokesmod.client.module.modules.movement;

import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.DescriptionSetting;
import keystrokesmod.client.module.setting.impl.SliderSetting;

public class NoSlow extends Module {
   public static DescriptionSetting a;
   public static DescriptionSetting c;
   public static SliderSetting b;

   public NoSlow() {
      super("NoSlow", ModuleCategory.movement);
      this.registerSetting(a = new DescriptionSetting("Default is 80% motion reduction."));
      this.registerSetting(c = new DescriptionSetting("Hypixel max: 22%"));
      this.registerSetting(b = new SliderSetting("Slow %", 80.0D, 0.0D, 80.0D, 1.0D));
   }

   public static void sl() {
      float val = (100.0F - (float)b.getInput()) / 100.0F;
      mc.thePlayer.movementInput.moveStrafe *= val;
      mc.thePlayer.movementInput.moveForward *= val;
   }
}
