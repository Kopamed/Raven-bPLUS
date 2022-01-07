package me.kopamed.raven.bplus.client.feature.module.modules.client;

import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.feature.module.ModuleCategory;
import me.kopamed.raven.bplus.helper.utils.Transition;
import me.kopamed.raven.bplus.client.feature.setting.settings.BooleanSetting;

public class CommandLine extends Module {
   public static boolean a = false;
   public static boolean b = false;
   public static Transition an;
   public static BooleanSetting animate;

   public CommandLine() {
      super("Command line", ModuleCategory.Misc, 0);
      this.registerSetting(animate = new BooleanSetting("Animate", true));
   }

   public void onEnable() {
      me.kopamed.raven.bplus.client.visual.clickgui.raven.CommandLine.setccs();
      a = true;
      b = false;
      (an = new Transition(500.0F)).start();
   }

   public void onDisable() {
      b = true;
      if (an != null) {
         an.start();
      }

      me.kopamed.raven.bplus.client.visual.clickgui.raven.CommandLine.od();
   }
}
