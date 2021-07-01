//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import keystrokesmod.ab;
import keystrokesmod.keystroke.KeyStroke;
import keystrokesmod.module.Module;
import keystrokesmod.module.modules.client.Gui;
import keystrokesmod.module.modules.combat.AutoClicker;
import keystrokesmod.module.modules.combat.Reach;
import keystrokesmod.module.modules.combat.Velocity;
import keystrokesmod.URLUtils;
import net.minecraft.client.Minecraft;

public class ConfigManager {
   private static Minecraft mc = Minecraft.getMinecraft();

   public static void saveKeyStrokeSettingsToConfigFile() {
      try {
         File file = new File(mc.mcDataDir + File.separator + "keystrokesmod", "config");
         if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
         }

         FileWriter writer = new FileWriter(file, false);
         writer.write(KeyStroke.x + "\n" + KeyStroke.y + "\n" + KeyStroke.currentColorNumber + "\n" + KeyStroke.d + "\n" + KeyStroke.e + "\n" + KeyStroke.f);
         writer.close();
      } catch (Throwable var2) {
         var2.printStackTrace();
      }

   }

   public static void applyKeyStrokeSettingsFromConfigFile() {
      try {
         File file = new File(mc.mcDataDir + File.separator + "keystrokesmod", "config");
         if (!file.exists()) {
            return;
         }

         BufferedReader reader = new BufferedReader(new FileReader(file));
         int i = 0;

         String line;
         while((line = reader.readLine()) != null) {
            ++i;
            switch(i) {
            case 1:
               KeyStroke.x = Integer.parseInt(line);
               break;
            case 2:
               KeyStroke.y = Integer.parseInt(line);
               break;
            case 3:
               KeyStroke.currentColorNumber = Integer.parseInt(line);
               break;
            case 4:
               KeyStroke.d = Boolean.parseBoolean(line);
               break;
            case 5:
               KeyStroke.e = Boolean.parseBoolean(line);
               break;
            case 6:
               KeyStroke.f = Boolean.parseBoolean(line);
            }
         }

         reader.close();
      } catch (Throwable var4) {
         var4.printStackTrace();
      }

   }

   public static void saveCheatSettingsToConfigFile() {
      try {
         File file = new File(mc.mcDataDir + File.separator + "keystrokes", "config");
         if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
         }

         FileWriter writer = new FileWriter(file, false);
         writer.write("api: " + URLUtils.k + "\n");
         writer.write(ab.theme + "\n" + ab.r1 + "\n" + ab.r2 + "\n" + ab.r3 + "\n" + ab.r4 + "\n" + ab.r5 + "\n" + ab.r6 + "\n" + ab.r7 + "\n" + ab.v1 + "\n" + ab.v2 + "\n" + ab.v3 + "\n" + ab.v4 + "\n" + ab.au1 + "\n" + ab.au2 + "\n" + ab.au3 + "\n" + ab.au4 + "\n" + ab.au5 + "\n" + ab.au6 + "\n" + ab.au7 + "\n" + ab.au8 + "\n" + ab.au9);
         writer.close();
      } catch (Throwable var2) {
      }

   }

   public static void applyCheatSettingsFromConfigFile() {
      try {
         File file = new File(mc.mcDataDir + File.separator + "keystrokes", "config");
         if (!file.exists()) {
            return;
         }

         BufferedReader reader = new BufferedReader(new FileReader(file));
         int i = -1;

         String line;
         while((line = reader.readLine()) != null) {
            ++i;
            switch(i) {
            case 0:
               URLUtils.k = line.split(": ")[1];
               break;
            case 1:
               Gui.a.setValue(Double.parseDouble(line));
               break;
            case 2:
               Reach.a.setValue(Double.parseDouble(line));
               break;
            case 3:
               Reach.b.setValue(Double.parseDouble(line));
               break;
            case 4:
               Reach.c.setEnabled(Boolean.parseBoolean(line));
               break;
            case 5:
               Reach.d.setEnabled(Boolean.parseBoolean(line));
               break;
            case 6:
               Reach.e.setEnabled(Boolean.parseBoolean(line));
               break;
            case 7:
               Reach.f.setEnabled(Boolean.parseBoolean(line));
               break;
            case 8:
               Module.getModule(Reach.class).setbind(Integer.parseInt(line));
               break;
            case 9:
               Velocity.a.setValue(Double.parseDouble(line));
               break;
            case 10:
               Velocity.b.setValue(Double.parseDouble(line));
               break;
            case 11:
               Velocity.c.setValue(Double.parseDouble(line));
               break;
            case 12:
               Module.getModule(Velocity.class).setbind(Integer.parseInt(line));
               break;
            case 13:
               AutoClicker.minCPS.setValue(Double.parseDouble(line));
               break;
            case 14:
               AutoClicker.maxCPS.setValue(Double.parseDouble(line));
               break;
            case 15:
               AutoClicker.jitter.setValue(Double.parseDouble(line));
               break;
            case 16:
               AutoClicker.weaponOnly.setEnabled(Boolean.parseBoolean(line));
               break;
            case 17:
               AutoClicker.breakBlocks.setEnabled(Boolean.parseBoolean(line));
               break;
            case 18:
               AutoClicker.inventoryFill.setEnabled(Boolean.parseBoolean(line));
               break;
            case 19:
               AutoClicker.leftClick.setEnabled(Boolean.parseBoolean(line));
               break;
            case 20:
               AutoClicker.rightClick.setEnabled(Boolean.parseBoolean(line));
               break;
            case 21:
               Module.getModule(AutoClicker.class).setbind(Integer.parseInt(line));
            }
         }

         reader.close();
         Ravenb3.getExecutor().execute(() -> {
            if (!URLUtils.isHypixelKeyValid(URLUtils.k)) {
               URLUtils.k = "";
            }

         });
      } catch (Throwable var4) {
         saveCheatSettingsToConfigFile();
      }

   }
}
