//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;

import keystrokesmod.module.ModuleSetting2;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class dg extends b {
   private ModuleSetting2 v;
   private m3 p;
   private int o;
   private int x;
   private int y;
   private boolean d = false;
   private double w;
   private final int msl = 84;

   public dg(ModuleSetting2 v, m3 b, int o) {
      this.v = v;
      this.p = b;
      this.x = b.c4t.gx() + b.c4t.gw();
      this.y = b.c4t.gy() + b.o;
      this.o = o;
   }

   public void r3nd3r() {
      net.minecraft.client.gui.Gui.drawRect(this.p.c4t.gx() + 4, this.p.c4t.gy() + this.o + 11, this.p.c4t.gx() + 4 + this.p.c4t.gw() - 8, this.p.c4t.gy() + this.o + 15, -12302777);
      int l = this.p.c4t.gx() + 4;
      int r = this.p.c4t.gx() + 4 + (int)this.w;
      if (r - l > 84) {
         r = l + 84;
      }

      net.minecraft.client.gui.Gui.drawRect(l, this.p.c4t.gy() + this.o + 11, r, this.p.c4t.gy() + this.o + 15, Color.getHSBColor((float)(System.currentTimeMillis() % 11000L) / 11000.0F, 0.75F, 0.9F).getRGB());
      GL11.glPushMatrix();
      GL11.glScaled(0.5D, 0.5D, 0.5D);
      Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.v.get() + ": " + this.v.getInput(), (float)((int)((float)(this.p.c4t.gx() + 4) * 2.0F)), (float)((int)((float)(this.p.c4t.gy() + this.o + 3) * 2.0F)), -1);
      GL11.glPopMatrix();
   }

   public void so(int n) {
      this.o = n;
   }

   public void uu(int x, int y) {
      this.y = this.p.c4t.gy() + this.o;
      this.x = this.p.c4t.gx();
      double d = (double)Math.min(this.p.c4t.gw() - 8, Math.max(0, x - this.x));
      this.w = (double)(this.p.c4t.gw() - 8) * (this.v.getInput() - this.v.g3ti()) / (this.v.g3ta() - this.v.g3ti());
      if (this.d) {
         if (d == 0.0D) {
            this.v.setValue(this.v.g3ti());
         } else {
            double n = r(d / (double)(this.p.c4t.gw() - 8) * (this.v.g3ta() - this.v.g3ti()) + this.v.g3ti(), 2);
            this.v.setValue(n);
         }
      }

   }

   private static double r(double v, int p) {
      if (p < 0) {
         return 0.0D;
      } else {
         BigDecimal bd = new BigDecimal(v);
         bd = bd.setScale(p, RoundingMode.HALF_UP);
         return bd.doubleValue();
      }
   }

   public void onCl1ck(int x, int y, int b) {
      if (this.u(x, y) && b == 0 && this.p.po) {
         this.d = true;
      }

      if (this.i(x, y) && b == 0 && this.p.po) {
         this.d = true;
      }

   }

   public void mr(int x, int y, int m) {
      this.d = false;
   }

   public boolean u(int x, int y) {
      return x > this.x && x < this.x + this.p.c4t.gw() / 2 + 1 && y > this.y && y < this.y + 16;
   }

   public boolean i(int x, int y) {
      return x > this.x + this.p.c4t.gw() / 2 && x < this.x + this.p.c4t.gw() && y > this.y && y < this.y + 16;
   }
}
