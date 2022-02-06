package keystrokesmod.main;

import keystrokesmod.NotificationRenderer;
import keystrokesmod.clickgui.raven.ClickGui;
import keystrokesmod.command.CommandManager;
import keystrokesmod.config.ConfigManager;
import keystrokesmod.keystroke.KeySrokeRenderer;
import keystrokesmod.keystroke.keystrokeCommand;
import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleManager;
import keystrokesmod.module.modules.HUD;
import keystrokesmod.module.modules.client.SelfDestruct;
import keystrokesmod.utils.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Base64;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Mod(
   modid = "keystrokesmod",
   name = "KeystrokesMod",
   version = "KMV5",
   acceptedMinecraftVersions = "[1.8.9]",
   clientSideOnly = true
)
public class Ravenbplus {
   public static boolean debugger = false;

   public static boolean outdated = false;
   public static boolean beta = false;

   public static CommandManager commandManager;
   private static final String numberOfUseTracker = "https://pastebin.com/raw/EgBH4cxS";
   public static final String numberOfFirstLaunchesTracker = "https://pastebin.com/raw/AyRARCeU";
   public static final String sourceLocation = "https://github.com/Kopamed/Raven-bPLUS";
   public static final String discord = "https://discord.gg/PZeAAUEAwz";
   public static String[] updateText = {"Your version of Raven B+ (" + Version.getCurrentVersion().replaceAll("-", ".") + ") is outdated!", "Enter the command update into client CommandLine to open the download page", "or just enable the update module to get a message in chat.", "", "Newest version: " + Version.getLatestVersion().replaceAll("-", ".")};
   public static String[] helloYourComputerHasVirus = {"You are using an unstable version of an outdated version", "Enter the command update into client CommandLine to open the download page", "or just enable the update module to get a message in chat.", "", "Newest version: " + Version.getLatestVersion().replaceAll("-", ".")};

   public static ConfigManager configManager;
   public static ClientConfig clientConfig;

   public static final ModuleManager moduleManager;

   public static ClickGui clickGui;
   //public static TabGui tabGui;

   private static final ScheduledExecutorService ex = Executors.newScheduledThreadPool(2);

   public static InputStream ravenLogoInputStream;
   public static ResourceLocation mResourceLocation;

   public static final String osName, osArch;

   public static String clientName = "Raven B+";
   public static String version = Version.getFullVersion();

   static {
      osName = System.getProperty("os.name").toLowerCase();
      osArch = System.getProperty("os.arch").toLowerCase();

      moduleManager = new ModuleManager();
      moduleManager.r3g1st3r();
   }

   public Ravenbplus() {
      // shout out to my homie
      // https://i.imgur.com/Mli8beT.png
      virus.exe = true;
      // Fuuuuuuuuui think i am retarded, i have spent 4 hours TRYNNG TO SEND S A POST REQUESR TO PASTEBIN FUI AEHFIU ESIFUY UESOF YESOUF
      /*try {
         URLUtils.createPaste();
      } catch (IOException e) {
         e.printStackTrace();
      }*/

      String paste_code = new String(Base64.getDecoder().decode("aWhFTTNxbmQ".getBytes()));
      //System.out.println("https://pastebin.com/raw/" + paste_code);
   }

   @EventHandler
   public void init(FMLInitializationEvent e) {
      MinecraftForge.EVENT_BUS.register(this);

      Runtime.getRuntime().addShutdownHook(new Thread(ex::shutdown));

      ClientCommandHandler.instance.registerCommand(new keystrokeCommand());

      MinecraftForge.EVENT_BUS.register(new DebugInfoRenderer());
      MinecraftForge.EVENT_BUS.register(new mouseManager());
      MinecraftForge.EVENT_BUS.register(new KeySrokeRenderer());
      MinecraftForge.EVENT_BUS.register(new ChatHelper());

      //lodaing assest
      ravenLogoInputStream = HUD.class.getResourceAsStream("/assets/keystrokes/raven.png");
      BufferedImage bf;
      try {
         bf = ImageIO.read(ravenLogoInputStream);
         mResourceLocation = Minecraft.getMinecraft().renderEngine.getDynamicTextureLocation("raven", new DynamicTexture(bf));
      } catch (IOException | IllegalArgumentException | NullPointerException noway) {
         noway.printStackTrace();
         mResourceLocation = null;
      }

      ClientConfig.applyKeyStrokeSettingsFromConfigFile();
      commandManager = new CommandManager();

      MinecraftForge.EVENT_BUS.register(ModuleManager.reach);
      MinecraftForge.EVENT_BUS.register(ModuleManager.nameHider);
      MinecraftForge.EVENT_BUS.register(NotificationRenderer.notificationRenderer);

      clickGui = new ClickGui();
      configManager = new ConfigManager();
      clientConfig = new ClientConfig();
      clientConfig.applyConfig();

      ex.execute(() -> Utils.URLS.getTextFromURL(numberOfUseTracker));

      if (Version.outdated())  Ravenbplus.outdated = true;
      if (Version.isBeta()) Ravenbplus.beta = true;
   }

   @SubscribeEvent
   public void onTick(ClientTickEvent event) {
      if (event.phase == Phase.END) {
         if (Utils.Player.isPlayerInGame() && !SelfDestruct.destructed) {
            for (int i = 0; i < ModuleManager.modListSize(); i++) {
               Module module = ModuleManager.modsList.get(i);
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

   @SubscribeEvent
   public void onChatMessageRecieved(ClientChatReceivedEvent event) {
      if (Utils.Player.isPlayerInGame() && !SelfDestruct.destructed) {
         if(event.message.getUnformattedText().startsWith("Your new API key is")){
            Utils.URLS.hypixelApiKey = event.message.getUnformattedText().replace("Your new API key is ", "");
            Utils.Player.sendMessageToSelf("&aSet api key to " + Utils.URLS.hypixelApiKey + "!");
            clientConfig.saveConfig();
         }
      }
   }

   public static ScheduledExecutorService getExecutor() {
      return ex;
   }
}

class virus{
    public static boolean exe;
}
