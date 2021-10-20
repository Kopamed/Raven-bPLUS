//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package me.kopamed.lunarkeystrokes.module.modules.movement;

import me.kopamed.lunarkeystrokes.module.Module;
import me.kopamed.lunarkeystrokes.module.ModuleManager;
import me.kopamed.lunarkeystrokes.module.setting.settings.Slider;
import me.kopamed.lunarkeystrokes.utils.Utils;
import net.minecraft.client.settings.KeyBinding;

public class BHop extends Module {
   public static Slider a;
   private final double bspd = 0.0025D;

   public BHop() {
      super("Bhop", Module.category.movement, 0);
      this.registerSetting(a = new Slider("Speed", 2.0D, 1.0D, 15.0D, 0.2D));
   }

   public void update() {
      if (!ModuleManager.fly.isEnabled() && Utils.Player.isMoving() && !mc.thePlayer.isInWater()) {
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), false);
         mc.thePlayer.noClip = true;
         if (mc.thePlayer.onGround) {
            mc.thePlayer.jump();
         }

         mc.thePlayer.setSprinting(true);
         double spd = 0.0025D * a.getInput();
         double m = (float)(Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ) + spd);
         Utils.Player.bop(m);
      }
   }
}
