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
      super("Velocity", Module.category.combat, 0);
      this.registerSetting(a = new ModuleSetting2("Horizontal", 90.0D, 0.0D, 100.0D, 1.0D));
      this.registerSetting(b = new ModuleSetting2("Vertical", 100.0D, 0.0D, 100.0D, 1.0D));
      this.registerSetting(c = new ModuleSetting2("Chance", 100.0D, 0.0D, 100.0D, 1.0D));
      this.registerSetting(d = new ModuleSetting("Only while targeting", false));
      this.registerSetting(e = new ModuleSetting("Disable while holding S", false));
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

         if (a.getInput() != 100.0D) {
            mc.thePlayer.motionX *= a.getInput() / 100.0D;
            mc.thePlayer.motionZ *= a.getInput() / 100.0D;
         }

         if (b.getInput() != 100.0D) {
            mc.thePlayer.motionY *= b.getInput() / 100.0D;
         }
      }

   }
}
