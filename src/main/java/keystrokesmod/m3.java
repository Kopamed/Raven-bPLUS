//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;

import keystrokesmod.module.*;
import keystrokesmod.module.modules.AutoConfig;
import keystrokesmod.module.modules.client.Gui;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class m3 extends b {
   private final int c1 = (new Color(0, 85, 255)).getRGB();
   private final int c2 = (new Color(154, 2, 255)).getRGB();
   public Module mod;
   public cm c4t;
   public int o;
   private ArrayList<b> sn;
   public boolean po;

   public m3(Module mod, cm p, int o) {
      this.mod = mod;
      this.c4t = p;
      this.o = o;
      this.sn = new ArrayList();
      this.po = false;
      int y = o + 12;
      if (!mod.getSettings().isEmpty()) {
         for (ModuleSettingsList v : mod.getSettings()) {
            if (v instanceof ModuleSetting2) {
               ModuleSetting2 n = (ModuleSetting2) v;
               dg s = new dg(n, this, y);
               this.sn.add(s);
               y += 12;
            } else if (v instanceof ModuleSetting) {
               ModuleSetting b = (ModuleSetting) v;
               kk c = new kk(mod, b, this, y);
               this.sn.add(c);
               y += 12;
            } else if (v instanceof ModuleDesc) {
               ModuleDesc d = (ModuleDesc) v;
               d1 m = new d1(d, this, y);
               this.sn.add(m);
               y += 12;
            }
         }
      }

      this.sn.add(new AutoConfig(this, y));
   }

   public void so(int n) {
      this.o = n;
      int y = this.o + 16;
      Iterator var3 = this.sn.iterator();

      while(true) {
         while(var3.hasNext()) {
            b co = (b)var3.next();
            co.so(y);
            if (co instanceof dg) {
               y += 16;
            } else if (co instanceof kk || co instanceof AutoConfig || co instanceof d1) {
               y += 12;
            }
         }

         return;
      }
   }

   public static void e() {
      GL11.glDisable(2929);
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glDepthMask(true);
      GL11.glEnable(2848);
      GL11.glHint(3154, 4354);
      GL11.glHint(3155, 4354);
   }

   public static void f() {
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glEnable(2929);
      GL11.glDisable(2848);
      GL11.glHint(3154, 4352);
      GL11.glHint(3155, 4352);
      GL11.glEdgeFlag(true);
   }

   public static void g(int h) {
      float a = 0.0F;
      float r = 0.0F;
      float g = 0.0F;
      float b = 0.0F;
      if (Gui.a.getInput() == 1.0D) {
         a = (float)(h >> 14 & 255) / 255.0F;
         r = (float)(h >> 5 & 255) / 255.0F;
         g = (float)(h >> 5 & 255) / 2155.0F;
         b = (float)(h & 255);
      } else if (Gui.a.getInput() == 2.0D) {
         a = (float)(h >> 14 & 255) / 255.0F;
         r = (float)(h >> 5 & 255) / 2155.0F;
         g = (float)(h >> 5 & 255) / 255.0F;
         b = (float)(h & 255);
      } else if (Gui.a.getInput() == 3.0D) {
      }

      GL11.glColor4f(r, g, b, a);
   }

   public static void v(float x, float y, float x1, float y1, int t, int b) {
      e();
      GL11.glShadeModel(7425);
      GL11.glBegin(7);
      g(t);
      GL11.glVertex2f(x, y1);
      GL11.glVertex2f(x1, y1);
      g(b);
      GL11.glVertex2f(x1, y);
      GL11.glVertex2f(x, y);
      GL11.glEnd();
      GL11.glShadeModel(7424);
      f();
   }

   public void r3nd3r() {
      v((float)this.c4t.gx(), (float)(this.c4t.gy() + this.o), (float)(this.c4t.gx() + this.c4t.gw()), (float)(this.c4t.gy() + 15 + this.o), this.mod.isEnabled() ? this.c2 : -12829381, this.mod.isEnabled() ? this.c2 : -12302777);
      GL11.glPushMatrix();
      int button_rgb = Gui.a.getInput() == 3.0D ? (this.mod.isEnabled() ? this.c1 : Color.lightGray.getRGB()) : Color.lightGray.getRGB();
      Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.mod.getName(), (float)(this.c4t.gx() + this.c4t.gw() / 2 - Minecraft.getMinecraft().fontRendererObj.getStringWidth(this.mod.getName()) / 2), (float)(this.c4t.gy() + this.o + 4), button_rgb);
      GL11.glPopMatrix();
      if (this.po && !this.sn.isEmpty()) {
         for (b c : this.sn) {
            c.r3nd3r();
         }
      }

   }

   public int gh() {
      if (!this.po) {
         return 16;
      } else {
         int h = 16;
         Iterator var2 = this.sn.iterator();

         while(true) {
            while(var2.hasNext()) {
               b c = (b)var2.next();
               if (c instanceof dg) {
                  h += 16;
               } else if (c instanceof kk || c instanceof AutoConfig || c instanceof d1) {
                  h += 12;
               }
            }

            return h;
         }
      }
   }

   public void uu(int x, int y) {
      if (!this.sn.isEmpty()) {
         for (b c : this.sn) {
            c.uu(x, y);
         }
      }

   }

   public void onCl1ck(int x, int y, int b) {
      if (this.ii(x, y) && b == 0) {
         this.mod.toggle();
      }

      if (this.ii(x, y) && b == 1) {
         this.po = !this.po;
         this.c4t.r3nd3r();
      }

      for (keystrokesmod.b c : this.sn) {
         c.onCl1ck(x, y, b);
      }

   }

   public void mr(int x, int y, int m) {
      for (b c : this.sn) {
         c.mr(x, y, m);
      }

   }

   public void ky(char t, int k) {
      for (b c : this.sn) {
         c.ky(t, k);
      }

   }

   public boolean ii(int x, int y) {
      return x > this.c4t.gx() && x < this.c4t.gx() + this.c4t.gw() && y > this.c4t.gy() + this.o && y < this.c4t.gy() + 16 + this.o;
   }
}
