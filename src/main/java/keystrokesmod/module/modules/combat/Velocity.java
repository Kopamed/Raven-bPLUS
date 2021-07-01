//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.module.modules.combat;

import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleSetting2;
import keystrokesmod.ay;
import keystrokesmod.module.ModuleSetting;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class Velocity extends Module {
   public static ModuleSetting2 a;
   public static ModuleSetting2 b;
   public static ModuleSetting2 c;
   public static ModuleSetting d;
   public static ModuleSetting e;

   public Velocity() {
      super(new char[]{'V', 'e', 'l', 'o', 'c', 'i', 't', 'y'}, Module.category.combat, 0);
      this.registerSetting(a = new ModuleSetting2(new char[]{'H', 'o', 'r', 'i', 'z', 'o', 'n', 't', 'a', 'l'}, 90.0D, 0.0D, 100.0D, 1.0D));
      this.registerSetting(b = new ModuleSetting2(new char[]{'V', 'e', 'r', 't', 'i', 'c', 'a', 'l'}, 100.0D, 0.0D, 100.0D, 1.0D));
      this.registerSetting(c = new ModuleSetting2(new char[]{'C', 'h', 'a', 'n', 'c', 'e'}, 100.0D, 0.0D, 100.0D, 1.0D));
      this.registerSetting(d = new ModuleSetting(new char[]{'O', 'n', 'l', 'y', ' ', 'w', 'h', 'i', 'l', 'e', ' ', 't', 'a', 'r', 'g', 'e', 't', 'i', 'n', 'g'}, false));
      this.registerSetting(e = new ModuleSetting(new char[]{'D', 'i', 's', 'a', 'b', 'l', 'e', ' ', 'w', 'h', 'i', 'l', 'e', ' ', 'h', 'o', 'l', 'd', 'i', 'n', 'g', ' ', 'S'}, false));
   }

   @SubscribeEvent
   public void c(LivingUpdateEvent ev) {
      if (ay.isPlayerInGame() && mc.thePlayer.maxHurtTime > 0 && mc.thePlayer.hurtTime == mc.thePlayer.maxHurtTime) {
         if (d.isToggled() && (mc.objectMouseOver == null || mc.objectMouseOver.entityHit == null)) {
            return;
         }

         if (e.isToggled() && Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode())) {
            return;
         }

         if (c.getInput() != 100.0D) {
            double ch = Math.random();
            if (ch >= c.getInput() / 100.0D) {
               return;
            }
         }

         EntityPlayerSP var10000;
         if (a.getInput() != 100.0D) {
            var10000 = mc.thePlayer;
            var10000.motionX *= a.getInput() / 100.0D;
            var10000 = mc.thePlayer;
            var10000.motionZ *= a.getInput() / 100.0D;
         }

         if (b.getInput() != 100.0D) {
            var10000 = mc.thePlayer;
            var10000.motionY *= b.getInput() / 100.0D;
         }
      }

   }
}
