//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package me.kopamed.lunarkeystrokes.keystroke;

import java.awt.Color;
import java.io.IOException;

import me.kopamed.lunarkeystrokes.keystroke.ui.KeyStrokeConfigGui;
import me.kopamed.lunarkeystrokes.keystroke.ui.component.KeyStrokeL;
import me.kopamed.lunarkeystrokes.main.Ravenbplus;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;

public class KeySrokeRenderer {
   private static final int[] a = new int[]{16777215, 16711680, 65280, 255, 16776960, 11141290};
   private final Minecraft mc = Minecraft.getMinecraft();

   private KeyStrokeL[] movementKeys;

   public KeySrokeRenderer() {
      KeyStroke f = Ravenbplus.getKeyStroke();

      // initiating the movement keys
      this.movementKeys = new KeyStrokeL[4];
      System.out.println(Ravenbplus.getKeyStroke() == null);
      this.movementKeys[0] = new KeyStrokeL((int)f.boxSize, (int)f.boxSize, mc.gameSettings.keyBindForward);
      this.movementKeys[1] = new KeyStrokeL((int)f.boxSize, (int)f.boxSize, mc.gameSettings.keyBindLeft);
      this.movementKeys[2] = new KeyStrokeL((int)f.boxSize, (int)f.boxSize, mc.gameSettings.keyBindBack);
      this.movementKeys[3] = new KeyStrokeL((int)f.boxSize, (int)f.boxSize, mc.gameSettings.keyBindRight);
   }

   @SubscribeEvent
   public void onRenderTick(RenderTickEvent e) {
      if (this.mc.currentScreen != null) {
         if (this.mc.currentScreen instanceof KeyStrokeConfigGui) {
            try {
               this.mc.currentScreen.handleInput();
            } catch (IOException var3) {
            }
         }

      } else if (this.mc.inGameHasFocus && !this.mc.gameSettings.showDebugInfo) {
         this.renderKeystrokes();
      }
   }

   public void updateSize(KeyStroke f){
      for(KeyStrokeL ke : this.movementKeys){
         ke.height = (int)f.boxSize;
         ke.width = (int)f.boxSize;
      }
   }

   public void renderKeystrokes() {
      KeyStroke f = Ravenbplus.getKeyStroke();
      updateSize(f);

      if(f.showMovementKeys){
         // render the movement keys
         int totalWidth = (int)f.boxSize * 3 + f.margin *2;
         int wx = (totalWidth - (int)f.boxSize)/2;
         this.movementKeys[0].draw(wx, f.y,(float)f.transition, f.textColour, f.textColourPressed, f.backgroundColour, f.backgroundColourPressed, f.textShadow, f.scale);
         int y = f.y + (int)f.boxSize + f.margin;
         for(int ks = 0; ks < 3; ks++){
            this.movementKeys[ks + 1].draw(f.x + f.margin *ks + (int)f.boxSize *ks, y,(float)f.transition, f.textColour, f.textColourPressed, f.backgroundColour, f.backgroundColourPressed, f.textShadow, f.scale);
         }
      }

      /*
      if (KeyStroke.e) {
         int x = KeyStroke.x;
         int y = KeyStroke.y;
         int g = this.getColor(KeyStroke.currentColorNumber);
         boolean h = KeyStroke.d;
         ScaledResolution res = new ScaledResolution(this.mc);
         int width = 74;
         int height = h ? 74 : 50;
         if (x < 0) {
            KeyStroke.x = 0;
            x = KeyStroke.x;
         } else if (x > res.getScaledWidth() - width) {
            KeyStroke.x = res.getScaledWidth() - width;
            x = KeyStroke.x;
         }

         if (y < 0) {
            KeyStroke.y = 0;
            y = KeyStroke.y;
         } else if (y > res.getScaledHeight() - height) {
            KeyStroke.y = res.getScaledHeight() - height;
            y = KeyStroke.y;
         }

         this.drawMovementKeys(x, y, g);
         if (h) {
            this.drawMouseButtons(x, y, g);
         }

      }*/
   }
/*
   private int getColor(int index) {
      return index == 6 ? Color.getHSBColor((float)(System.currentTimeMillis() % 3750L) / 3750.0F, 1.0F, 1.0F).getRGB() : a[index];
   }

   private void drawMovementKeys(int x, int y, int textColor) {
      KeyStrokeKeyRenderer[] var4 = this.b;
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         KeyStrokeKeyRenderer key = var4[var6];
         key.renderKey(x, y, textColor);
      }

   }

   private void drawMouseButtons(int x, int y, int textColor) {
      KeyStrokeMouse[] var4 = this.c;
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         KeyStrokeMouse button = var4[var6];
         button.n(x, y, textColor);
      }

   }*/
}
