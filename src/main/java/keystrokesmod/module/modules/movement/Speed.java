package keystrokesmod.module.modules.movement;

import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleDesc;
import keystrokesmod.module.ModuleSettingTick;
import keystrokesmod.module.ModuleSettingSlider;
import keystrokesmod.utils.Utils;
import org.lwjgl.input.Keyboard;

public class Speed extends Module {
   public static ModuleDesc dc;
   public static ModuleSettingSlider a;
   public static ModuleSettingTick b;

   public Speed() {
      super("Speed", Module.category.movement, 0);
      this.registerSetting(dc = new ModuleDesc("Hypixel max: 1.13"));
      this.registerSetting(a = new ModuleSettingSlider("Speed", 1.2D, 1.0D, 1.5D, 0.01D));
      this.registerSetting(b = new ModuleSettingTick("Strafe only", false));
   }

   public void update() {
      double csp = Utils.Player.pythagorasMovement();
      if (csp != 0.0D) {
         if (mc.thePlayer.onGround && !mc.thePlayer.capabilities.isFlying) {
            if (!b.isToggled() || mc.thePlayer.moveStrafing != 0.0F) {
               if (mc.thePlayer.hurtTime != mc.thePlayer.maxHurtTime || mc.thePlayer.maxHurtTime <= 0) {
                  if (!Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode())) {
                     double val = a.getInput() - (a.getInput() - 1.0D) * 0.5D;
                     Utils.Player.fixMovementSpeed(csp * val, true);
                  }
               }
            }
         }
      }
   }
}
