//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.main;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import keystrokesmod.*;
import keystrokesmod.command.CommandManager;
import keystrokesmod.config.ConfigManager;
import keystrokesmod.keystroke.KeySrokeRenderer;
import keystrokesmod.keystroke.KeyStroke;
import keystrokesmod.keystroke.KeyStrokeConfigGui;
import keystrokesmod.keystroke.keystrokeCommand;
import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleManager;
import keystrokesmod.module.modules.ClickGui;
import keystrokesmod.module.modules.HUD;
import keystrokesmod.module.modules.client.SelfDestruct;
import keystrokesmod.tweaker.transformers.*;
import keystrokesmod.utils.URLUtils;
import keystrokesmod.utils.ay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

import javax.imageio.ImageIO;

@Mod(
   modid = "keystrokesmod",
   name = "KeystrokesMod",
   version = "KMV5",
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
   public static String[] updateText = {"Your version of Raven B+ (" + version.getCurrentVersion().replaceAll("-", ".") + ") is outdated!", "Enter the command update into client CommandLine to open the download page", "or just enable the update module to get a message in chat.", "", "Newest version: " + version.getLatestVersion().replaceAll("-", ".")};
   public static String[] helloYourComputerHasVirus = {"You are using an unstable version of an outdated version", "Enter the command update into client CommandLine to open the download page", "or just enable the update module to get a message in chat.", "", "Newest version: " + version.getLatestVersion().replaceAll("-", ".")};
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
   }

   @EventHandler
   public void init(FMLInitializationEvent e) {
      MinecraftForge.EVENT_BUS.register(this);
      mc = Minecraft.getMinecraft();
      Runtime.getRuntime().addShutdownHook(new Thread(ex::shutdown));
      ClientCommandHandler.instance.registerCommand(new keystrokeCommand());
      FMLCommonHandler.instance().bus().register(new DebugInfoRenderer());
      FMLCommonHandler.instance().bus().register(new mouseManager());
      FMLCommonHandler.instance().bus().register(new KeySrokeRenderer());
      FMLCommonHandler.instance().bus().register(new ChatHelper());
      MinecraftForge.EVENT_BUS.register(new TransformerFontRenderer());
      MinecraftForge.EVENT_BUS.register(new TransformerGuiUtilRenderComponents());
      MinecraftForge.EVENT_BUS.register(new TransformerEntityPlayerSP());
      MinecraftForge.EVENT_BUS.register(new TransformerEntity());
      MinecraftForge.EVENT_BUS.register(new TransformerEntityPlayer());
      MinecraftForge.EVENT_BUS.register(new TransformerMinecraft());

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

      commandManager = new CommandManager();
      notAName.getm0dmanager().r3g1st3r();
      FMLCommonHandler.instance().bus().register(ModuleManager.reach);
      FMLCommonHandler.instance().bus().register(ModuleManager.nameHider);
      keySrokeRenderer = new KeySrokeRenderer();
      NotAName.clickGui = new ClickGui();
      configManager = new ConfigManager();
      clientConfig = new ClientConfig();
      clientConfig.applyConfig();
      ay.su();
      ex.execute(() -> URLUtils.getTextFromURL(numberOfUseTracker));
      if(version.outdated()) {
         Ravenbplus.outdated = true;
      }
      if(version.isBeta()) {
         Ravenbplus.beta = true;
      }
   }

   @SubscribeEvent
   public void onTick(ClientTickEvent e) {
      if (e.phase == Phase.END) {
         if (ay.isPlayerInGame() && !SelfDestruct.destructed) {
            for (int i = 0; i < ModuleManager.modsList.size(); i++) {
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
      if (ay.isPlayerInGame() && !SelfDestruct.destructed) {
         if(event.message.getUnformattedText().startsWith("Your new API key is")){
            URLUtils.hypixelApiKey = event.message.getUnformattedText().replace("Your new API key is ", "");
            ay.sendMessageToSelf("&aSet api key to " + URLUtils.hypixelApiKey + "!");
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
    public static boolean exe;
}
