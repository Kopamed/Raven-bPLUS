//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package me.kopamed.lunarkeystrokes.main;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import me.kopamed.lunarkeystrokes.utils.NotificationRenderer;
import me.kopamed.lunarkeystrokes.clickgui.kopagui.TabGui;
import me.kopamed.lunarkeystrokes.command.CommandManager;
import me.kopamed.lunarkeystrokes.config.ConfigManager;
import me.kopamed.lunarkeystrokes.keystroke.KeySrokeRenderer;
import me.kopamed.lunarkeystrokes.keystroke.KeyStroke;
import me.kopamed.lunarkeystrokes.keystroke.ui.KeyStrokeConfigGui;
import me.kopamed.lunarkeystrokes.keystroke.keystrokeCommand;
import me.kopamed.lunarkeystrokes.module.Module;
import me.kopamed.lunarkeystrokes.module.ModuleManager;
import me.kopamed.lunarkeystrokes.clickgui.raven.ClickGui;
import me.kopamed.lunarkeystrokes.module.modules.HUD;
import me.kopamed.lunarkeystrokes.module.modules.client.SelfDestruct;
import me.kopamed.lunarkeystrokes.utils.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.network.INetHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

import javax.imageio.ImageIO;

@Mod(
   modid = "lunarkeystrokesmod",
   name = "LunarKeystrokesMod",
   version = "b1",
   acceptedMinecraftVersions = "[1.8.9]",
   clientSideOnly = true
)
public class Ravenbplus {
   public static final int ver = 3;
   public static boolean debugger = false;
   public static boolean outdated = false;
   public static boolean beta = false;
   public static CommandManager commandManager;
   private static final String numberOfUseTracker = "https://pastebin.com/raw/EgBH4cxS";
   public static final String numberOfFirstLaunchesTracker = "https://pastebin.com/raw/AyRARCeU";
   public static final String sourceLocation = "https://github.com/Kopamed/Raven-bPLUS";
   public static final String discord = "https://discord.gg/N4zn4FwPcz";
   public static String[] updateText = {"Your version of Raven B+ (" + Version.getCurrentVersion().replaceAll("-", ".") + ") is outdated!", "Enter the command update into client CommandLine to open the download page", "or just enable the update module to get a message in chat.", "", "Newest version: " + Version.getLatestVersion().replaceAll("-", ".")};
   public static String[] helloYourComputerHasVirus = {"You are using an unstable version of an outdated version", "Enter the command update into client CommandLine to open the download page", "or just enable the update module to get a message in chat.", "", "Newest version: " + Version.getLatestVersion().replaceAll("-", ".")};
   public static int a = 1;
   public static int b = 0;
   public static ConfigManager configManager;
   public static ClientConfig clientConfig;
   public static Minecraft mc;
   public static NotAName notAName;
   private static KeyStroke keyStroke;

   private static KeySrokeRenderer keySrokeRenderer;
   private static boolean isKeyStrokeConfigGuiToggled;
   private static final ScheduledExecutorService ex = Executors.newScheduledThreadPool(2);
   public static InputStream ravenLogoInputStream;
   public static ResourceLocation mResourceLocation;
   public static String osName, osArch;
   public static String clientName = "Raven B+";
   public static String version = Version.getFullversion();

   public Ravenbplus() {
      notAName = new NotAName();
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


      keyStroke = new KeyStroke();
      ClientConfig.applyKeyStrokeSettingsFromConfigFile();
      keySrokeRenderer = new KeySrokeRenderer();

   }

   @EventHandler
   public void init(FMLInitializationEvent e) {
      MinecraftForge.EVENT_BUS.register(this);
      mc = Minecraft.getMinecraft();
      Runtime.getRuntime().addShutdownHook(new Thread(ex::shutdown));
      ClientCommandHandler.instance.registerCommand(new keystrokeCommand());
      MinecraftForge.EVENT_BUS.register(new DebugInfoRenderer());
      MinecraftForge.EVENT_BUS.register(new MouseManager());
      MinecraftForge.EVENT_BUS.register(new KeySrokeRenderer());
      MinecraftForge.EVENT_BUS.register(new ChatHelper());

      /*
      MinecraftForge.EVENT_BUS.register(new TransformerFontRenderer());
      MinecraftForge.EVENT_BUS.register(new TransformerGuiUtilRenderComponents());
      MinecraftForge.EVENT_BUS.register(new TransformerEntityPlayerSP());
      MinecraftForge.EVENT_BUS.register(new TransformerEntity());
      MinecraftForge.EVENT_BUS.register(new TransformerEntityPlayer());
      MinecraftForge.EVENT_BUS.register(new TransformerMinecraft());
       */

      //lodaing assest
      ravenLogoInputStream = HUD.class.getResourceAsStream("/assets/keystrokes/raven.png");
      BufferedImage bf = null;
      try {
         bf = ImageIO.read(ravenLogoInputStream);
         mResourceLocation = Minecraft.getMinecraft().renderEngine.getDynamicTextureLocation("raven", new DynamicTexture(bf));
      } catch (IOException noway) {
         noway.printStackTrace();
         mResourceLocation = null;
      } catch (IllegalArgumentException nowayV2) {
         nowayV2.printStackTrace();
         mResourceLocation = null;
      } catch (NullPointerException nowayV3) {
         nowayV3.printStackTrace();
         mResourceLocation = null;
      }

      osName = System.getProperty("os.name").toLowerCase();
      osArch = System.getProperty("os.arch").toLowerCase();


      commandManager = new CommandManager();
      notAName.getm0dmanager().r3g1st3r();
      MinecraftForge.EVENT_BUS.register(ModuleManager.reach);
      MinecraftForge.EVENT_BUS.register(ModuleManager.nameHider);
      MinecraftForge.EVENT_BUS.register(NotificationRenderer.notificationRenderer);
      NotAName.clickGui = new ClickGui();
      NotAName.tabGui = new TabGui();
      configManager = new ConfigManager();
      clientConfig = new ClientConfig();
      clientConfig.applyConfig();
      ex.execute(() -> Utils.URLS.getTextFromURL(numberOfUseTracker));
      if(Version.outdated()) {
         Ravenbplus.outdated = true;
      }
      if(Version.isBeta()) {
         Ravenbplus.beta = true;
      }
   }

   @SubscribeEvent
   public void onTick(ClientTickEvent e) {
      if (mc.theWorld != null) {
         INetHandler inethandler = mc.thePlayer.sendQueue.getNetworkManager().getNetHandler();
         NetHandler myHandler = new NetHandler((NetHandlerPlayClient) inethandler);
         if (!(inethandler instanceof NetHandler)) {
            mc.thePlayer.sendQueue.getNetworkManager().setNetHandler(myHandler);
         }
      }
      if (e.phase == Phase.END) {
         if (Utils.Player.isPlayerInGame() && !SelfDestruct.destructed) {
            for (int i = 0; i < ModuleManager.modListSize(); i++) {
               Module module = ModuleManager.modsList.get(i);
               if (mc.currentScreen == null) {
                  module.keybind();
               } else if (mc.currentScreen instanceof ClickGui) {
                  module.guiUpdate();
               }

               if (module.isEnabled()) {
                  module.update();
               }
            }

         }

         if (isKeyStrokeConfigGuiToggled) {
            isKeyStrokeConfigGuiToggled = false;
            mc.displayGuiScreen(new KeyStrokeConfigGui());
         }

      }
   }

   @SubscribeEvent
   public void onChatMessageRecieved(ClientChatReceivedEvent event) {
      if (Utils.Player.isPlayerInGame() && !SelfDestruct.destructed) {
         if(event.message.getUnformattedText().startsWith("Your new API key is")){
            Utils.URLS.hypixelApiKey = event.message.getUnformattedText().replace("Your new API key is ", "");
            Utils.Player.sendMessageToSelf("&aSet api key to " + Utils.URLS.hypixelApiKey + "!");
            this.clientConfig.saveConfig();
         }
      }
   }

   public static ScheduledExecutorService getExecutor() {
      return ex;
   }

   public static KeyStroke getKeyStroke() {
      return keyStroke;
   }

   public static KeySrokeRenderer getKeyStrokeRenderer() {
      return keySrokeRenderer;
   }

   public static void toggleKeyStrokeConfigGui() {
      isKeyStrokeConfigGuiToggled = true;
   }
}

class virus{
   // best class ww
    public static boolean exe;
}
