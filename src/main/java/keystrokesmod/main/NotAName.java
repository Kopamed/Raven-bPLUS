package keystrokesmod.main;

import keystrokesmod.config.ConfigManager;
import keystrokesmod.module.ModuleManager;
import keystrokesmod.module.modules.ClickGui;

public class NotAName {
   public static ModuleManager moduleManager;
   public static ClickGui clickGui;
   private static int c = -1;

   public NotAName() {
      moduleManager = new ModuleManager();
   }

   public ModuleManager getm0dmanager() {
      return moduleManager;
   }

   public static int pF() {
      return c;
   }
}
