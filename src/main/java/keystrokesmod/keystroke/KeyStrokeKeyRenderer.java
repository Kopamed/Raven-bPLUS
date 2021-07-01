//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.keystroke;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class KeyStrokeKeyRenderer {
   private Minecraft a = Minecraft.getMinecraft();
   private KeyBinding keyBinding;
   private int c;
   private int d;
   private boolean e = true;
   private long f = 0L;
   private int g = 255;
   private double h = 1.0D;

   public KeyStrokeKeyRenderer(KeyBinding i, int j, int k) {
      this.keyBinding = i;
      this.c = j;
      this.d = k;
   }

   public void renderKey(int l, int m, int color) {
      boolean o = this.keyBinding.isKeyDown();
      String p = Keyboard.getKeyName(this.keyBinding.getKeyCode());
      if (o != this.e) {
         this.e = o;
         this.f = System.currentTimeMillis();
      }

      if (o) {
         this.g = Math.min(255, (int)(2L * (System.currentTimeMillis() - this.f)));
         this.h = Math.max(0.0D, 1.0D - (double)(System.currentTimeMillis() - this.f) / 20.0D);
      } else {
         this.g = Math.max(0, 255 - (int)(2L * (System.currentTimeMillis() - this.f)));
         this.h = Math.min(1.0D, (double)(System.currentTimeMillis() - this.f) / 20.0D);
      }

      int q = color >> 16 & 255;
      int r = color >> 8 & 255;
      int s = color & 255;
      int c = (new Color(q, r, s)).getRGB();
      net.minecraft.client.gui.Gui.drawRect(l + this.c, m + this.d, l + this.c + 22, m + this.d + 22, 2013265920 + (this.g << 16) + (this.g << 8) + this.g);
      if (KeyStroke.f) {
         net.minecraft.client.gui.Gui.drawRect(l + this.c, m + this.d, l + this.c + 22, m + this.d + 1, c);
         net.minecraft.client.gui.Gui.drawRect(l + this.c, m + this.d + 21, l + this.c + 22, m + this.d + 22, c);
         net.minecraft.client.gui.Gui.drawRect(l + this.c, m + this.d, l + this.c + 1, m + this.d + 22, c);
         net.minecraft.client.gui.Gui.drawRect(l + this.c + 21, m + this.d, l + this.c + 22, m + this.d + 22, c);
      }

      this.a.fontRendererObj.drawString(p, l + this.c + 8, m + this.d + 8, -16777216 + ((int)((double)q * this.h) << 16) + ((int)((double)r * this.h) << 8) + (int)((double)s * this.h));
   }
}
