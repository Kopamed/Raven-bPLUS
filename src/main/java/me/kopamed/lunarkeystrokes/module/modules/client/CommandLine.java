package me.kopamed.lunarkeystrokes.module.modules.client;

import me.kopamed.lunarkeystrokes.module.Module;
import me.kopamed.lunarkeystrokes.utils.Timer;
import me.kopamed.lunarkeystrokes.module.setting.settings.Tick;

public class CommandLine extends Module {
   public static boolean a = false;
   public static boolean b = false;
   public static Timer an;
   public static Tick animate;

   public CommandLine() {
      super("Command line", Module.category.client, 0);
      this.registerSetting(animate = new Tick("Animate", true));
   }

   public void onEnable() {
      me.kopamed.lunarkeystrokes.clickgui.raven.CommandLine.setccs();
      a = true;
      b = false;
      (an = new Timer(500.0F)).start();
   }

   public void onDisable() {
      b = true;
      if (an != null) {
         an.start();
      }

      me.kopamed.lunarkeystrokes.clickgui.raven.CommandLine.od();
   }
}
