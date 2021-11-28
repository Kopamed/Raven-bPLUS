package me.kopamed.raven.bplus.client.feature.module.modules.combat;

import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.feature.module.ModuleCategory;
import me.kopamed.raven.bplus.client.feature.setting.settings.NumberSetting;
import me.kopamed.raven.bplus.helper.utils.Utils;
import me.kopamed.raven.bplus.client.feature.setting.settings.BooleanSetting;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class Velocity extends Module {
   public static NumberSetting a;
   public static NumberSetting b;
   public static NumberSetting c;
   public static BooleanSetting d;
   public static BooleanSetting e;

   public Velocity() {
      super("Velocity", ModuleCategory.Combat, 0);
      this.registerSetting(a = new NumberSetting("Horizontal", 90.0D, 0.0D, 100.0D, 1.0D));
      this.registerSetting(b = new NumberSetting("Vertical", 100.0D, 0.0D, 100.0D, 1.0D));
      this.registerSetting(c = new NumberSetting("Chance", 100.0D, 0.0D, 100.0D, 1.0D));
      this.registerSetting(d = new BooleanSetting("Only while targeting", false));
      this.registerSetting(e = new BooleanSetting("Disable while holding S", false));
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
