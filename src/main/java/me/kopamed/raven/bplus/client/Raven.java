package me.kopamed.raven.bplus.client;

import java.awt.*;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicReference;

import me.kopamed.raven.bplus.client.visual.clickgui.plus.PlusGui;
import me.kopamed.raven.bplus.helper.discordRPC.DiscordRPCManager;
import me.kopamed.raven.bplus.helper.manager.*;
import me.kopamed.raven.bplus.helper.manager.cfg.Config;
import me.kopamed.raven.bplus.helper.manager.cfg.ConfigManager;
import me.kopamed.raven.bplus.helper.manager.version.VersionManager;
import me.kopamed.raven.bplus.helper.utils.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.common.MinecraftForge;

public class Raven {
   public static Raven client;

   private final PlusGui clickGui;
   private final ConfigManager configManager;
   private final ModuleManager moduleManager;
   private final FontRenderer fontRenderer;
   private final VersionManager versionManager;
   private final DebugManager debugManager;
   private final Tracker tracker = new Tracker();
   private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
   private final DiscordRPCManager discordRPCManager;

   private final Minecraft mc;

   private boolean destroyed = false;

   public Raven() throws IOException, FontFormatException {
      client = this;
      this.mc = Minecraft.getMinecraft();
      this.discordRPCManager = new DiscordRPCManager();
      this.moduleManager = new ModuleManager();
      this.versionManager = new VersionManager();
      this.clickGui = new PlusGui();
      this.configManager = new ConfigManager();
      this.anarchyConfigLoad();


      this.debugManager = new DebugManager();
      this.registerListeners();
      this.fontRenderer = mc.fontRendererObj;
      tracker.registerLaunch();
   }

   private void anarchyConfigLoad() {
      this.configManager.applyConfig(configManager.findConfigs().get("user").get(0));
   }


   private void registerListeners() {
      // todo

      //MinecraftForge.EVENT_BUS.register(new DebugInfoRenderer());
      MinecraftForge.EVENT_BUS.register(new KeybindManager());
      //MinecraftForge.EVENT_BUS.register(new MouseManager());
      //MinecraftForge.EVENT_BUS.register(new KeySrokeRenderer());
      //MinecraftForge.EVENT_BUS.register(new ChatHelper());

   }

   public PlusGui getClickGui() {
      return clickGui;
   }

   public ConfigManager getConfigManager() {
      return configManager;
   }

   public ModuleManager getModuleManager() {
      return moduleManager;
   }

   public FontRenderer getFontRenderer() {
      return fontRenderer;
   }

   public VersionManager getVersionManager() {
      return versionManager;
   }

   public DebugManager getDebugManager() {
      return debugManager;
   }

   public Tracker getTracker(){
      return tracker;
   }

   public Minecraft getMc() {
      return mc;
   }

   public ScheduledExecutorService getExecutor() {
      return executor;
   }

   public void destruct(){
      this.destroyed = true;
      // todo
   }

   public boolean isDestroyed() {
      return destroyed;
   }

   public DiscordRPCManager getDiscordRPCManager() {
      return discordRPCManager;
   }
}
