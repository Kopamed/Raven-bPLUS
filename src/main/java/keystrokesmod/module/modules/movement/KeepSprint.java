//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.module.modules.movement;

import keystrokesmod.module.*;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;

public class KeepSprint extends Module {
   public static ModuleDesc a;
   public static ModuleSetting2 b;
   public static ModuleSetting c;

   public KeepSprint() {
      super(new char[]{'K', 'e', 'e', 'p', 'S', 'p', 'r', 'i', 'n', 't'}, Module.category.movement, 0);
      this.registerSetting(a = new ModuleDesc(new String(new char[]{'D', 'e', 'f', 'a', 'u', 'l', 't', ' ', 'i', 's', ' ', '4', '0', '%', ' ', 'm', 'o', 't', 'i', 'o', 'n', ' ', 'r', 'e', 'd', 'u', 'c', 't', 'i', 'o', 'n', '.'})));
      this.registerSetting(b = new ModuleSetting2(new char[]{'S', 'l', 'o', 'w', ' ', '%'}, 40.0D, 0.0D, 40.0D, 1.0D));
      this.registerSetting(c = new ModuleSetting(new char[]{'O', 'n', 'l', 'y', ' ', 'r', 'e', 'd', 'u', 'c', 'e', ' ', 'r', 'e', 'a', 'c', 'h', ' ', 'h', 'i', 't', 's'}, false));
   }

   public static void sl(Entity en) {
      EntityPlayerSP var10000;
      double dist;
      if (c.isToggled() && ModuleManager.reach.isEnabled() && !mc.thePlayer.capabilities.isCreativeMode) {
         dist = mc.objectMouseOver.hitVec.distanceTo(mc.getRenderViewEntity().getPositionEyes(1.0F));
         double val;
         if (dist > 3.0D) {
            val = (100.0D - (double)((float)b.getInput())) / 100.0D;
         } else {
            val = 0.6D;
         }

         var10000 = mc.thePlayer;
         var10000.motionX *= val;
         var10000 = mc.thePlayer;
         var10000.motionZ *= val;
      } else {
         dist = (100.0D - (double)((float)b.getInput())) / 100.0D;
         var10000 = mc.thePlayer;
         var10000.motionX *= dist;
         var10000 = mc.thePlayer;
         var10000.motionZ *= dist;
      }
   }
}
