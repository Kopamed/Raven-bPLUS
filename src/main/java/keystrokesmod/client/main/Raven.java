package keystrokesmod.client.main;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import keystrokesmod.client.utils.version.VersionManager;
import keystrokesmod.client.clickgui.raven.ClickGui;
import keystrokesmod.client.command.CommandManager;
import keystrokesmod.client.config.ConfigManager;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.ModuleManager;
import keystrokesmod.client.module.modules.HUD;
import keystrokesmod.client.utils.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


//Todo fix wtap
/* todo dump
ghost blocks add ability to place air and or other block by ID possibly

add blink

fix autotool crashing game

make it so that when you open the mod menu, your game's gui is set to normal/small to make organization and viewing modules easier

add a way to input a hex code for the values of header backgrounds, text colors, etc

menu blur in the background of the gui

make autoplace have checks for bridging only like bridge assist does

remove explicit b9 name tags or the default name tags, there isn't much point in both. you could also just make them into one module

Fix aim assist

tooltips, fix murder mystery detective, fix autotool
 */

public class Raven {

   public static boolean debugger = false;
   public static final VersionManager versionManager  = new VersionManager();
   public static CommandManager commandManager;
   public static final String sourceLocation = "https://github.com/Kopamed/Raven-bPLUS";
   public static final String downloadLocation = "https://github.com/Kopamed/Raven-bPLUS/raw/main/build/libs/%5B1.8.9%5D%20BetterKeystrokes%20V-1.2.jar";
   public static final String discord = "https://discord.gg/QQMQfCRyNP";
   public static String[] updateText = {"Your version of Raven B+ (" + versionManager.getClientVersion().toString() + ") is outdated!", "Enter the command update into client CommandLine to open the download page", "or just enable the update module to get a message in chat.", "", "Newest version: " + versionManager.getLatestVersion().toString()};
   public static ConfigManager configManager;
   public static ClientConfig clientConfig;

   public static final ModuleManager moduleManager = new ModuleManager();

   public static ClickGui clickGui;
   //public static TabGui tabGui;

   private static final ScheduledExecutorService ex = Executors.newScheduledThreadPool(2);

   public static ResourceLocation mResourceLocation;

   public static final String osName, osArch;


   static {
      osName = System.getProperty("os.name").toLowerCase();
      osArch = System.getProperty("os.arch").toLowerCase();
   }

   public static void init() {

      MinecraftForge.EVENT_BUS.register(new Raven());
      MinecraftForge.EVENT_BUS.register(new DebugInfoRenderer());
      MinecraftForge.EVENT_BUS.register(new MouseManager());
      MinecraftForge.EVENT_BUS.register(new ChatHelper());

      Runtime.getRuntime().addShutdownHook(new Thread(ex::shutdown));

      InputStream ravenLogoInputStream = HUD.class.getResourceAsStream("/assets/keystrokes/raven.png");
      BufferedImage bf;
      try {
         assert ravenLogoInputStream != null;
         bf = ImageIO.read(ravenLogoInputStream);
         mResourceLocation = Minecraft.getMinecraft().renderEngine.getDynamicTextureLocation("raven", new DynamicTexture(bf));
      } catch (IOException | IllegalArgumentException | NullPointerException noway) {
         noway.printStackTrace();
         mResourceLocation = null;
      }

      commandManager = new CommandManager();
      clickGui = new ClickGui();
      configManager = new ConfigManager();
      clientConfig = new ClientConfig();
      clientConfig.applyConfig();

      ex.execute(() -> {
         try {
            LaunchTracker.registerLaunch();
         } catch (IOException e) {
            throw new RuntimeException(e);
         }
      });
   }

   @SubscribeEvent
   public void onTick(ClientTickEvent event) {
      if (event.phase == Phase.END) {
         if (Utils.Player.isPlayerInGame()) {
            for (int i = 0; i < moduleManager.numberOfModules(); i++) {
               Module module = moduleManager.getModules().get(i);
               if (Minecraft.getMinecraft().currentScreen == null) {
                  module.keybind();
               } else if (Minecraft.getMinecraft().currentScreen instanceof ClickGui) {
                  module.guiUpdate();
               }

               if (module.isEnabled()) module.update();
            }
         }
      }
   }

   @SuppressWarnings("unused")
   @SubscribeEvent
   public void onChatMessageReceived(ClientChatReceivedEvent event) {
      if (Utils.Player.isPlayerInGame()) {
         String msg = event.message.getUnformattedText();

         if (msg.startsWith("Your new API key is")) {
            Utils.URLS.hypixelApiKey = msg.replace("Your new API key is ", "");
            Utils.Player.sendMessageToSelf("&aSet api key to " + Utils.URLS.hypixelApiKey + "!");
            clientConfig.saveConfig();
         }
      }
   }

   public static ScheduledExecutorService getExecutor() {
      return ex;
   }
}