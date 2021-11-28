package me.kopamed.raven.bplus.client.feature.module.modules.movement;

import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.feature.module.ModuleCategory;
import me.kopamed.raven.bplus.client.feature.setting.settings.DescriptionSetting;
import me.kopamed.raven.bplus.client.feature.setting.settings.NumberSetting;

public class NoSlow extends Module {
   public static DescriptionSetting a;
   public static DescriptionSetting c;
   public static NumberSetting b;

   public NoSlow() {
      super("NoSlow", ModuleCategory.Movement, 0);
      this.registerSetting(a = new DescriptionSetting("Default is 80% motion reduction."));
      this.registerSetting(c = new DescriptionSetting("Hypixel max: 22%"));
      this.registerSetting(b = new NumberSetting("Slow %", 80.0D, 0.0D, 80.0D, 1.0D));
   }

   public static void sl() {
      float val = (100.0F - (float)b.getInput()) / 100.0F;
      mc.thePlayer.movementInput.moveStrafe *= val;
      mc.thePlayer.movementInput.moveForward *= val;
   }
}
