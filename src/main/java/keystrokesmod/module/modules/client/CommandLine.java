package keystrokesmod.module.modules.client;

import keystrokesmod.module.Module;
import keystrokesmod.am;
import keystrokesmod.module.ModuleSetting;
import keystrokesmod.gc;

public class CommandLine extends Module {
   public static boolean a = false;
   public static boolean b = false;
   public static am an;
   public static ModuleSetting animate;

   public CommandLine() {
      super(new char[]{'C', 'o', 'm', 'm', 'a', 'n', 'd', ' ', 'l', 'i', 'n', 'e'}, Module.category.client, 0);
      this.registerSetting(animate = new ModuleSetting(new char[]{'A', 'n', 'i', 'm', 'a', 't', 'e'}, true));
   }

   public void onEnable() {
      gc.setccs();
      a = true;
      b = false;
      (an = new am(500.0F)).start();
   }

   public void onDisable() {
      b = true;
      if (an != null) {
         an.start();
      }

      gc.od();
   }
}
