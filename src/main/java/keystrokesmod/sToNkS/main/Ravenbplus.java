package keystrokesmod.sToNkS.main;

import keystrokesmod.sToNkS.lib.fr.jmraich.rax.event.EventManager;
import keystrokesmod.sToNkS.lib.fr.jmraich.rax.event.EventTransmitter;
import keystrokesmod.sToNkS.lib.fr.jmraich.rax.event.FMLEvent;
import keystrokesmod.sToNkS.lib.fr.jmraich.rax.utils.FMLEventHelper;
import keystrokesmod.keystroke.KeySrokeRenderer;
import keystrokesmod.sToNkS.NotificationRenderer;
import keystrokesmod.sToNkS.clickgui.raven.ClickGui;
import keystrokesmod.sToNkS.command.CommandManager;
import keystrokesmod.sToNkS.config.ConfigManager;
import keystrokesmod.sToNkS.module.Module;
import keystrokesmod.sToNkS.module.ModuleManager;
import keystrokesmod.sToNkS.module.modules.HUD;
import keystrokesmod.sToNkS.utils.*;
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
import java.util.Base64;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

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

   public static ResourceLocation mResourceLocation;

   public static final String osName, osArch;

   public static String clientName = "Raven B+";
   public static String version = Version.getFullVersion();

   public static final EventTransmitter eventTransmitter = new EventTransmitter();

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

   /**
    * @see keystrokesmod.sToNkS.tweaker.transformers.TransformerFMLConfigGuiFactory
    */
   public static void init() {
      try {
         FMLEventHelper.fmlRegister(MinecraftForge.EVENT_BUS, eventTransmitter);
      } catch (ReflectiveOperationException exc) {
         exc.printStackTrace();
      }

      EventManager.register(new Ravenbplus());

      EventManager.register(new DebugInfoRenderer());
      EventManager.register(new mouseManager());
      EventManager.register(new KeySrokeRenderer());
      EventManager.register(new ChatHelper());

      EventManager.register(new NotificationRenderer());

      Runtime.getRuntime().addShutdownHook(new Thread(ex::shutdown));


      //lodaing assest
      InputStream ravenLogoInputStream = HUD.class.getResourceAsStream("/assets/keystrokes/raven.png");
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

      /* // USELESS
         MinecraftForge.EVENT_BUS.register(ModuleManager.reach);
         MinecraftForge.EVENT_BUS.register(ModuleManager.nameHider);
       */

      clickGui = new ClickGui();
      configManager = new ConfigManager();
      clientConfig = new ClientConfig();
      clientConfig.applyConfig();

      //ex.execute(() -> Utils.URLS.getTextFromURL(numberOfUseTracker));

      if (Version.outdated())  Ravenbplus.outdated = true;
      if (Version.isBeta()) Ravenbplus.beta = true;
   }

   @FMLEvent
   public void onTick(ClientTickEvent event) {
      if (event.phase == Phase.END) {
         if (Utils.Player.isPlayerInGame()) {
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

   @FMLEvent
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

class virus{
    public static boolean exe;
}
