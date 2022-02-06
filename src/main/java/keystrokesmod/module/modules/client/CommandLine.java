package keystrokesmod.module.modules.client;

import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleSettingTick;
import keystrokesmod.utils.Timer;

public class CommandLine extends Module {
   public static boolean a = false;
   public static boolean b = false;
   public static Timer an;
   public static ModuleSettingTick animate;

   public CommandLine() {
      super("Command line", Module.category.client, 0);
      this.registerSetting(animate = new ModuleSettingTick("Animate", true));
   }

   public void onEnable() {
      keystrokesmod.clickgui.raven.CommandLine.setccs();
      a = true;
      b = false;
      (an = new Timer(500.0F)).start();
   }

   public void onDisable() {
      b = true;
      if (an != null) {
         an.start();
      }

      keystrokesmod.clickgui.raven.CommandLine.od();
   }
}
