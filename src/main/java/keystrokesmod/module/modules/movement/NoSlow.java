//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.module.modules.movement;

import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleDesc;
import keystrokesmod.module.ModuleSetting2;
import net.minecraft.util.MovementInput;

public class NoSlow extends Module {
   public static ModuleDesc a;
   public static ModuleDesc c;
   public static ModuleSetting2 b;

   public NoSlow() {
      super(new char[]{'N', 'o', 'S', 'l', 'o', 'w'}, Module.category.movement, 0);
      this.registerSetting(a = new ModuleDesc(new String(new char[]{'D', 'e', 'f', 'a', 'u', 'l', 't', ' ', 'i', 's', ' ', '8', '0', '%', ' ', 'm', 'o', 't', 'i', 'o', 'n', ' ', 'r', 'e', 'd', 'u', 'c', 't', 'i', 'o', 'n', '.'})));
      this.registerSetting(c = new ModuleDesc(new String(new char[]{'H', 'y', 'p', 'i', 'x', 'e', 'l', ' ', 'm', 'a', 'x', ':', ' ', '2', '2', '%'})));
      this.registerSetting(b = new ModuleSetting2(new char[]{'S', 'l', 'o', 'w', ' ', '%'}, 80.0D, 0.0D, 80.0D, 1.0D));
   }

   public static void sl() {
      float val = (100.0F - (float)b.getInput()) / 100.0F;
      MovementInput var10000 = mc.thePlayer.movementInput;
      var10000.moveStrafe *= val;
      var10000 = mc.thePlayer.movementInput;
      var10000.moveForward *= val;
   }
}
