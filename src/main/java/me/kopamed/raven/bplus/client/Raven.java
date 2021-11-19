package me.kopamed.raven.bplus.client;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import me.kopamed.raven.bplus.client.visual.clickgui.plus.PlusGui;
import me.kopamed.raven.bplus.helper.manager.*;
import me.kopamed.raven.bplus.helper.utils.*;
import me.superblaubeere27.client.utils.fontRenderer.GlyphPage;
import me.superblaubeere27.client.utils.fontRenderer.GlyphPageFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

public class Raven {
   public static Raven client;

   private final PlusGui clickGui;
   private final ConfigManager configManager;
   private final ModuleManager moduleManager;
   private GlyphPageFontRenderer fontRenderer;
   private final VersionManager versionManager;
   private final DebugManager debugManager;
   private final Tracker tracker = new Tracker();
   private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);

   private final Minecraft mc;

   private boolean destroyed = false;

   public Raven() throws IOException, FontFormatException {
      client = this;
      this.mc = Minecraft.getMinecraft();
      this.moduleManager = new ModuleManager();
      this.configManager = new ConfigManager();
      this.clickGui = new PlusGui();
      this.debugManager = new DebugManager();
      this.registerListeners();
      this.setUpFontRenderer();
      this.versionManager = new VersionManager();
      tracker.registerLaunch();
   }

   private void registerListeners() {
      // todo

      //MinecraftForge.EVENT_BUS.register(new DebugInfoRenderer());
      MinecraftForge.EVENT_BUS.register(new KeybindManager());
      //MinecraftForge.EVENT_BUS.register(new MouseManager());
      //MinecraftForge.EVENT_BUS.register(new KeySrokeRenderer());
      //MinecraftForge.EVENT_BUS.register(new ChatHelper());

   }

   private void setUpFontRenderer() throws IOException, FontFormatException {
      char[] chars = new char[256];
      for(int i = 0; i < chars.length; i++){
         chars[i] = (char) i;
      }

      InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("assets/raven/font/Adventure.ttf");
      System.out.println(stream == null);
      //Font font = Font.createFont(Font.TRUETYPE_FONT, stream);
      Font font = new Font("Heavitas", Font.PLAIN, 80);
      //System.out.println(font.getSize());



      GlyphPage glyphPage = new GlyphPage(font, true, true);
      glyphPage.generateGlyphPage(chars);
      glyphPage.setupTexture();
      GlyphPage glyphPageBold = new GlyphPage(font, true, true);
      glyphPage.generateGlyphPage(chars);
      glyphPage.setupTexture();
      GlyphPage glyphPageItalic = new GlyphPage(font, true, true);
      glyphPage.generateGlyphPage(chars);
      glyphPage.setupTexture();
      GlyphPage glyphPageBoth = new GlyphPage(font, true, true);
      glyphPage.generateGlyphPage(chars);
      glyphPage.setupTexture();


      this.fontRenderer = new GlyphPageFontRenderer(glyphPage, glyphPageBold, glyphPageItalic, glyphPageBoth);
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

   public GlyphPageFontRenderer getFontRenderer() {
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
}
