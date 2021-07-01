//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.module.modules.movement;

import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleSetting;

public class StopMotion extends Module {
   public static ModuleSetting a;
   public static ModuleSetting b;
   public static ModuleSetting c;

   public StopMotion() {
      super(new char[]{'S', 't', 'o', 'p', ' ', 'M', 'o', 't', 'i', 'o', 'n'}, Module.category.movement, 0);
      this.registerSetting(a = new ModuleSetting(new char[]{'S', 't', 'o', 'p', ' ', 'X'}, true));
      this.registerSetting(b = new ModuleSetting(new char[]{'S', 't', 'o', 'p', ' ', 'Y'}, true));
      this.registerSetting(c = new ModuleSetting(new char[]{'S', 't', 'o', 'p', ' ', 'Z'}, true));
   }

   public void onEnable() {
      if (a.isToggled()) {
         mc.thePlayer.motionX = 0.0D;
      }

      if (b.isToggled()) {
         mc.thePlayer.motionY = 0.0D;
      }

      if (c.isToggled()) {
         mc.thePlayer.motionZ = 0.0D;
      }

      this.disable();
   }
}
