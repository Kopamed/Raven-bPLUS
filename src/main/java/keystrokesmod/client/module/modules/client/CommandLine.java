package keystrokesmod.client.module.modules.client;

import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.Timer;

public class CommandLine extends Module {
   public static boolean a = false;
   public static boolean b = false;
   public static Timer an;
   public static TickSetting animate;

   public CommandLine() {
      super("Command line", ModuleCategory.client, 0);
      this.registerSetting(animate = new TickSetting("Animate", true));
   }

   public void onEnable() {
      keystrokesmod.client.clickgui.raven.CommandLine.setccs();
      a = true;
      b = false;
      (an = new Timer(500.0F)).start();
   }

   public void onDisable() {
      b = true;
      if (an != null) {
         an.start();
      }

      keystrokesmod.client.clickgui.raven.CommandLine.od();
   }
}
