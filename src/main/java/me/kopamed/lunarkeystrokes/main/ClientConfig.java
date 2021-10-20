//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package me.kopamed.lunarkeystrokes.main;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import me.kopamed.lunarkeystrokes.clickgui.raven.components.ButtonCategory;
import me.kopamed.lunarkeystrokes.module.modules.HUD;
import me.kopamed.lunarkeystrokes.utils.Utils;
import me.kopamed.lunarkeystrokes.keystroke.KeyStroke;
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
         File file = new File(mc.mcDataDir + File.separator + "me/kopamed/lunarkeystrokes", "config");
         if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
         }

         FileWriter writer = new FileWriter(file, false);
         //writer.write(KeyStroke.x + "\n" + KeyStroke.y + "\n" + KeyStroke.currentColorNumber + "\n" + KeyStroke.d + "\n" + KeyStroke.e + "\n" + KeyStroke.f);
         writer.close();
      } catch (Throwable var2) {
         var2.printStackTrace();
      }

   }

   public static void applyKeyStrokeSettingsFromConfigFile() {
      try {
         File file = new File(mc.mcDataDir + File.separator + "me/kopamed/lunarkeystrokes", "config");
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
            /*case 3:
               KeyStroke.currentColorNumber = Integer.parseInt(line);
               break;
            case 4:
               KeyStroke.d = Boolean.parseBoolean(line);
               break;
            case 5:
               KeyStroke.e = Boolean.parseBoolean(line);
               break;
            case 6:
               KeyStroke.f = Boolean.parseBoolean(line);*/
            }
         }

         reader.close();
      } catch (Throwable var4) {
         var4.printStackTrace();
      }

   }


   public void saveConfig() {
      List<String> config = new ArrayList<String>();
      config.add(hypixelApiKeyPrefix + Utils.URLS.hypixelApiKey);
      config.add(pasteApiKeyPrefix + Utils.URLS.pasteApiKey);
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
            Utils.URLS.hypixelApiKey = line.replace(hypixelApiKeyPrefix, "");
            Ravenbplus.getExecutor().execute(() -> {
               if (!Utils.URLS.isHypixelKeyValid(Utils.URLS.hypixelApiKey)) {
                  Utils.URLS.hypixelApiKey = "";
                  ////System.out.println("Invalid key!");
               } else{
                  ////System.out.println("Valid key!");
               }

            });
         } else if(line.startsWith(pasteApiKeyPrefix)){
            Utils.URLS.pasteApiKey = line.replace(pasteApiKeyPrefix, "");
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
         for (ButtonCategory cat : NotAName.clickGui.categoryList) {
            if(what.startsWith(cat.categoryName.name())){
               List<String> cfg = Utils.Java.StringListToList(what.split("~"));
               cat.setX(Integer.parseInt(cfg.get(1)));
               cat.setY(Integer.parseInt(cfg.get(2)));
               cat.setOpened(Boolean.parseBoolean(cfg.get(3)));
            }
         }
      }
   }

   public String getClickGuiPos() {
      StringBuilder posConfig = new StringBuilder();
      for (ButtonCategory cat : NotAName.clickGui.categoryList) {
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
