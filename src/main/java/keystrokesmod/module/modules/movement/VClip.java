//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.module.modules.movement;

import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleSetting2;

public class VClip extends Module {
   public static ModuleSetting2 a;

   public VClip() {
      super(new char[]{'V', 'C', 'l', 'i', 'p'}, Module.category.movement, 0);
      this.registerSetting(a = new ModuleSetting2(new char[]{'D', 'i', 's', 't', 'a', 'c', 'e'}, 2.0D, -10.0D, 10.0D, 0.5D));
   }

   public void onEnable() {
      if (a.getInput() != 0.0D) {
         mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + a.getInput(), mc.thePlayer.posZ);
      }

      this.disable();
   }
}
