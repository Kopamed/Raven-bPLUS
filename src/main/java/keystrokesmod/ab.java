package keystrokesmod;

import keystrokesmod.main.ConfigManager;
import keystrokesmod.module.Module;
import keystrokesmod.module.modules.client.Gui;
import keystrokesmod.module.modules.combat.AutoClicker;
import keystrokesmod.module.modules.combat.Reach;
import keystrokesmod.module.modules.combat.Velocity;

public class ab {
   public static double theme = 1.0D;
   public static double r1 = 3.1D;
   public static double r2 = 3.3D;
   public static boolean r3 = false;
   public static boolean r4 = false;
   public static boolean r5 = false;
   public static boolean r6 = false;
   public static int r7 = 0;
   public static double v1 = 96.0D;
   public static double v2 = 100.0D;
   public static double v3 = 100.0D;
   public static int v4 = 0;
   public static double au1 = 9.0D;
   public static double au2 = 12.0D;
   public static double au3 = 0.0D;
   public static boolean au4 = false;
   public static boolean au5 = false;
   public static boolean au6 = false;
   public static boolean au7 = true;
   public static boolean au8 = false;
   public static int au9 = 0;

   public static void ss() {
      theme = Gui.a.getInput();
      r1 = Reach.a.getInput();
      r2 = Reach.b.getInput();
      r3 = Reach.c.isToggled();
      r4 = Reach.d.isToggled();
      r5 = Reach.e.isToggled();
      r6 = Reach.f.isToggled();
      r7 = Module.getModule(Reach.class).getKeycode();
      v1 = Velocity.a.getInput();
      v2 = Velocity.b.getInput();
      v3 = Velocity.c.getInput();
      v4 = Module.getModule(Velocity.class).getKeycode();
      au1 = AutoClicker.leftMinCPS.getInput();
      au2 = AutoClicker.leftMaxCPS.getInput();
      au3 = AutoClicker.jitter.getInput();
      au4 = AutoClicker.weaponOnly.isToggled();
      au5 = AutoClicker.breakBlocks.isToggled();
      au6 = AutoClicker.inventoryFill.isToggled();
      au7 = AutoClicker.leftClick.isToggled();
      au8 = AutoClicker.rightClick.isToggled();
      au9 = Module.getModule(AutoClicker.class).getKeycode();
      ConfigManager.saveCheatSettingsToConfigFile();
   }
}
