package keystrokesmod.module.modules.movement;

import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleSettingSlider;

public class VClip extends Module {
   public static ModuleSettingSlider a;

   public VClip() {
      super("VClip", Module.category.movement, 0);
      this.registerSetting(a = new ModuleSettingSlider("Distace", 2.0D, -10.0D, 10.0D, 0.5D));
   }

   public void onEnable() {
      if (a.getInput() != 0.0D) {
         mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + a.getInput(), mc.thePlayer.posZ);
      }

      this.disable();
   }
}
