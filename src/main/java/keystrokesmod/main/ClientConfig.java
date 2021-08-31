//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.main;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import keystrokesmod.GuiModuleCategory;
import keystrokesmod.ab;
import keystrokesmod.module.modules.HUD;
import keystrokesmod.utils.ay;
import keystrokesmod.keystroke.KeyStroke;
import keystrokesmod.module.Module;
import keystrokesmod.module.modules.client.Gui;
import keystrokesmod.module.modules.combat.AutoClicker;
import keystrokesmod.module.modules.combat.Reach;
import keystrokesmod.module.modules.combat.Velocity;
import keystrokesmod.utils.URLUtils;
import net.minecraft.client.Minecraft;

public class ClientConfig {
   private static final Minecraft mc = Minecraft.getMinecraft();
   private final File configFile;
   private final File configDir;
   private final String fileName = "config";
   private final String hypixelApiKeyPrefix = "hypixel-api~ ";
   private final String pasteApiKeyPrefix = "paste-api~ ";
   private final String clickGuiPosPrefix = "clickgui-pos~ ";
   private final String loadedConfigPrefix = "loaded-cfg~ ";
   //when you are coding the config manager and life be like
   //public static String ip_token_discord_webhook_logger_spyware_malware_minecraft_block_hacker_sigma_miner_100_percent_haram_no_cap_m8_Kopamed_is_sexy = "https://imgur.com/a/hYd1023";

   public ClientConfig(){
      configDir = new File(Minecraft.getMinecraft().mcDataDir, "keystrokes");
      if(!configDir.exists()){
         configDir.mkdir();
      }

      configFile = new File(configDir, fileName);
      if(!configFile.exists()){
         try {
            configFile.createNewFile();
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
   }

   public static void saveKeyStrokeSettingsToConfigFile() {
      try {
         //ip_token_discord_webhook_logger_spyware_malware_minecraft_block_hacker_sigma_miner_100_percent_haram_no_cap_m8_Kopamed_is_sexy.equalsIgnoreCase("Lol gotta add usages to make this funnier XD");
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
         writer.write("api: " + URLUtils.hypixelApiKey + "\n");
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
               URLUtils.hypixelApiKey = line.split(": ")[1];
               break;
            case 1:
               Gui.guiTheme.setValue(Double.parseDouble(line));
               break;
            case 2:
               Reach.min.setValue(Double.parseDouble(line));
               break;
            case 3:
               Reach.max.setValue(Double.parseDouble(line));
               break;
            case 4:
               Reach.weapon_only.setEnabled(Boolean.parseBoolean(line));
               break;
            case 5:
               Reach.moving_only.setEnabled(Boolean.parseBoolean(line));
               break;
            case 6:
               Reach.sprint_only.setEnabled(Boolean.parseBoolean(line));
               break;
            case 7:
               Reach.hit_through_blocks.setEnabled(Boolean.parseBoolean(line));
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
               AutoClicker.leftMinCPS.setValue(Double.parseDouble(line));
               break;
            case 14:
               AutoClicker.leftMaxCPS.setValue(Double.parseDouble(line));
               break;
            case 15:
               AutoClicker.jitterLeft.setValue(Double.parseDouble(line));
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
         Ravenbplus.getExecutor().execute(() -> {
            if (!URLUtils.isHypixelKeyValid(URLUtils.hypixelApiKey)) {
               URLUtils.hypixelApiKey = "";
            }

         });
      } catch (Throwable var4) {
         saveCheatSettingsToConfigFile();
      }

   }

   public void saveConfig() {
      List<String> config = new ArrayList<String>();
      config.add(hypixelApiKeyPrefix + URLUtils.hypixelApiKey);
      config.add(pasteApiKeyPrefix + URLUtils.pasteApiKey);
      config.add(clickGuiPosPrefix + getClickGuiPos());
      config.add(loadedConfigPrefix + Ravenbplus.configManager.getCurrentConfig());
      config.add(HUD.HUDX_prefix + HUD.getHudX());
      config.add(HUD.HUDY_prefix + HUD.getHudY());

      PrintWriter writer = null;
      try {
         writer = new PrintWriter(this.configFile);
         for (String line : config) {
            writer.println(line);
         }
         writer.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public void applyConfig(){
      List<String> config = this.parseConfigFile();

      for(String line : config){
         if(line.startsWith(hypixelApiKeyPrefix)){
            URLUtils.hypixelApiKey = line.replace(hypixelApiKeyPrefix, "");
            Ravenbplus.getExecutor().execute(() -> {
               if (!URLUtils.isHypixelKeyValid(URLUtils.hypixelApiKey)) {
                  URLUtils.hypixelApiKey = "";
                  ////System.out.println("Invalid key!");
               } else{
                  ////System.out.println("Valid key!");
               }

            });
         } else if(line.startsWith(pasteApiKeyPrefix)){
            URLUtils.pasteApiKey = line.replace(pasteApiKeyPrefix, "");
         } else if(line.startsWith(clickGuiPosPrefix)){
            loadClickGuiCoords(line.replace(clickGuiPosPrefix, ""));
         } else if(line.startsWith(loadedConfigPrefix)){
            Ravenbplus.configManager.loadConfig(line.replace(loadedConfigPrefix, ""));
         } else if (line.startsWith(HUD.HUDX_prefix)) {
            try {
               HUD.setHudX(Integer.parseInt(line.replace(HUD.HUDX_prefix, "")));
            } catch (Exception e) {e.printStackTrace();}
         } else if (line.startsWith(HUD.HUDY_prefix)) {
            try {
               HUD.setHudY(Integer.parseInt(line.replace(HUD.HUDY_prefix, "")));
            } catch (Exception e) {e.printStackTrace();}
         }
      }
   }

   private List<String> parseConfigFile() {
      List<String> configFileContents = new ArrayList<String>();
      Scanner reader = null;
      try {
         reader = new Scanner(this.configFile);
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      }
      while (reader.hasNextLine())
         configFileContents.add(reader.nextLine());

      return configFileContents;
   }

   private void loadClickGuiCoords(String decryptedString) {
      //clickgui config
      // categoryname:x:y:opened
      ////System.out.println(decryptedString);
      for (String what : decryptedString.split("/")){
         for (GuiModuleCategory cat : NotAName.clickGui.categoryList) {
            if(what.startsWith(cat.categoryName.name())){
               List<String> cfg = ay.StringListToList(what.split("~"));
               cat.setX(Integer.parseInt(cfg.get(1)));
               cat.setY(Integer.parseInt(cfg.get(2)));
               cat.setOpened(Boolean.parseBoolean(cfg.get(3)));
            }
         }
      }
   }

   public String getClickGuiPos() {
      StringBuilder posConfig = new StringBuilder();
      for (GuiModuleCategory cat : NotAName.clickGui.categoryList) {
         posConfig.append(cat.categoryName.name());
         posConfig.append("~");
         posConfig.append(cat.getX());
         posConfig.append("~");
         posConfig.append(cat.getY());
         posConfig.append("~");
         posConfig.append(cat.isOpened());
         posConfig.append("/");
      }
      return posConfig.toString().substring(0, posConfig.toString().length() - 2);

   }
}
