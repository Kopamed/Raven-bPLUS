//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.module.modules;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import keystrokesmod.*;
import keystrokesmod.main.NotAName;
import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleManager;
import keystrokesmod.module.ModuleSettingTick;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;

public class HUD extends Module {
   public static ModuleSettingTick ep;
   public static ModuleSettingTick sh;
   public static ModuleSettingTick al;
   private static int hudX = 5;
   private static int hudY = 70;

   public HUD() {
      super("HUD", Module.category.render, 0);
      this.registerSetting(ep = new ModuleSettingTick("Edit position", false));
      this.registerSetting(sh = new ModuleSettingTick("Drop shadow", true));
      this.registerSetting(al = new ModuleSettingTick("Alphabetical sort", false));
   }

   public void onEnable() {
      ModuleManager.sort();
   }

   public void guiButtonToggled(ModuleSettingTick b) {
      if (b == ep) {
         ep.disable();
         mc.displayGuiScreen(new HUD.eh());
      } else if (b == al) {
         ModuleManager.sort();
      }

   }

   @SubscribeEvent
   public void a(RenderTickEvent ev) {
      if (ev.phase == Phase.END && ay.isPlayerInGame()) {
         if (mc.currentScreen != null || mc.gameSettings.showDebugInfo) {
            return;
         }

         int y = hudY;
         int del = 0;
         List<Module> en = new ArrayList(NotAName.moduleManager.listofmods());
         Iterator var5 = en.iterator();

         while(var5.hasNext()) {
            Module m = (Module)var5.next();
            if (m.isEnabled() && m != this) {
               mc.fontRendererObj.drawString(m.getName(), (float)hudX, (float)y, ay.rainbowDraw(2L, (long)del), sh.isToggled());
               y += mc.fontRendererObj.FONT_HEIGHT + 2;
               del -= 120;
            }
         }
      }

   }

   static class eh extends GuiScreen {
      final String a = new String("This is an-Example-HUD");
      GuiButtonExt rp;
      boolean d = false;
      int miX = 0;
      int miY = 0;
      int maX = 0;
      int maY = 0;
      int aX = 5;
      int aY = 70;
      int laX = 0;
      int laY = 0;
      int lmX = 0;
      int lmY = 0;

      public void initGui() {
         super.initGui();
         this.buttonList.add(this.rp = new GuiButtonExt(1, this.width - 90, 5, 85, 20, new String("Reset position")));
         this.aX = HUD.hudX;
         this.aY = HUD.hudY;
      }

      public void drawScreen(int mX, int mY, float pt) {
         drawRect(0, 0, this.width, this.height, -1308622848);
         int miX = this.aX;
         int miY = this.aY;
         int maX = miX + 50;
         int maY = miY + 32;
         this.d(this.mc.fontRendererObj, this.a);
         this.miX = miX;
         this.miY = miY;
         this.maX = maX;
         this.maY = maY;
         HUD.hudX = miX;
         HUD.hudY = miY;
         ScaledResolution res = new ScaledResolution(this.mc);
         int x = res.getScaledWidth() / 2 - 84;
         int y = res.getScaledHeight() / 2 - 20;
         ru.dct("Edit the HUD position by dragging.", '-', x, y, 2L, 0L, true, this.mc.fontRendererObj);

         try {
            this.handleInput();
         } catch (IOException var12) {
         }

         super.drawScreen(mX, mY, pt);
      }

      private void d(FontRenderer fr, String t) {
         int x = this.miX;
         int y = this.miY;
         String[] var5 = t.split("-");

         for (String s : var5) {
            fr.drawString(s, (float) x, (float) y, Color.white.getRGB(), HUD.sh.isToggled());
            y += fr.FONT_HEIGHT + 2;
         }

      }

      protected void mouseClickMove(int mX, int mY, int b, long t) {
         super.mouseClickMove(mX, mY, b, t);
         if (b == 0) {
            if (this.d) {
               this.aX = this.laX + (mX - this.lmX);
               this.aY = this.laY + (mY - this.lmY);
            } else if (mX > this.miX && mX < this.maX && mY > this.miY && mY < this.maY) {
               this.d = true;
               this.lmX = mX;
               this.lmY = mY;
               this.laX = this.aX;
               this.laY = this.aY;
            }

         }
      }

      protected void mouseReleased(int mX, int mY, int s) {
         super.mouseReleased(mX, mY, s);
         if (s == 0) {
            this.d = false;
         }

      }

      public void actionPerformed(GuiButton b) {
         if (b == this.rp) {
            this.aX = HUD.hudX = 5;
            this.aY = HUD.hudY = 70;
         }

      }

      public boolean doesGuiPauseGame() {
         return false;
      }
   }
}
