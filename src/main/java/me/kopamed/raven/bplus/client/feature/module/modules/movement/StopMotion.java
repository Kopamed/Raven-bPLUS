package me.kopamed.raven.bplus.client.feature.module.modules.movement;

import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.feature.module.ModuleCategory;
import me.kopamed.raven.bplus.client.feature.setting.settings.BooleanSetting;
import me.kopamed.raven.bplus.helper.utils.Utils;

public class StopMotion extends Module {
   public static BooleanSetting a;
   public static BooleanSetting b;
   public static BooleanSetting c;

   public StopMotion() {
      super("Stop Motion", ModuleCategory.Movement, 0);
      this.registerSetting(a = new BooleanSetting("Stop X", true));
      this.registerSetting(b = new BooleanSetting("Stop Y", true));
      this.registerSetting(c = new BooleanSetting("Stop Z", true));
   }

   public void onEnable() {
      if(!Utils.Player.isPlayerInGame()){
         this.disable();
         return;
      }

      if(a.isToggled())
         mc.thePlayer.motionX = 0;

      if(b.isToggled())
         mc.thePlayer.motionY = 0;

      if(c.isToggled())
         mc.thePlayer.motionZ = 0;

      this.disable();
   }

}
