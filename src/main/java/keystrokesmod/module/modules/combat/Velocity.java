package keystrokesmod.module.modules.combat;

import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleSettingSlider;
import keystrokesmod.utils.Utils;
import keystrokesmod.module.ModuleSettingTick;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class Velocity extends Module {
   public static ModuleSettingSlider a;
   public static ModuleSettingSlider b;
   public static ModuleSettingSlider c;
   public static ModuleSettingTick d;
   public static ModuleSettingTick e;

   public Velocity() {
      super("Velocity", Module.category.combat, 0);
      this.registerSetting(a = new ModuleSettingSlider("Horizontal", 90.0D, 0.0D, 100.0D, 1.0D));
      this.registerSetting(b = new ModuleSettingSlider("Vertical", 100.0D, 0.0D, 100.0D, 1.0D));
      this.registerSetting(c = new ModuleSettingSlider("Chance", 100.0D, 0.0D, 100.0D, 1.0D));
      this.registerSetting(d = new ModuleSettingTick("Only while targeting", false));
      this.registerSetting(e = new ModuleSettingTick("Disable while holding S", false));
   }

   @SubscribeEvent
   public void c(LivingUpdateEvent ev) {
      if (Utils.Player.isPlayerInGame() && mc.thePlayer.maxHurtTime > 0 && mc.thePlayer.hurtTime == mc.thePlayer.maxHurtTime)
      {
         //System.out.println(ev.entity.getName());
         //System.out.println(mc.objectMouseOver.hitInfo);
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
