package keystrokesmod.client.module.modules.movement;

import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.DescriptionSetting;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.Utils;
import org.lwjgl.input.Keyboard;

public class Speed extends Module {
   public static DescriptionSetting dc;
   public static SliderSetting a;
   public static TickSetting b;

   public Speed() {
      super("Speed", ModuleCategory.movement);
      this.registerSetting(dc = new DescriptionSetting("Hypixel max: 1.13"));
      this.registerSetting(a = new SliderSetting("Speed", 1.2D, 1.0D, 1.5D, 0.01D));
      this.registerSetting(b = new TickSetting("Strafe only", false));
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
