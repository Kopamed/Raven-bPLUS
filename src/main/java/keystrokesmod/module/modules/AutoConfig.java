//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.module.modules;

import java.awt.Color;

import keystrokesmod.b;
import keystrokesmod.BindKey;
import keystrokesmod.m3;
import keystrokesmod.module.modules.client.Gui;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class AutoConfig extends b {
   private boolean h;
   private boolean isBinding;
   private m3 p;
   private int o;
   private int x;
   private int y;

   public AutoConfig(m3 b, int o) {
      this.p = b;
      this.x = b.c4t.gx() + b.c4t.gw();
      this.y = b.c4t.gy() + b.o;
      this.o = o;
   }

   public void so(int n) {
      this.o = n;
   }

   public void r3nd3r() {
      GL11.glPushMatrix();
      GL11.glScaled(0.5D, 0.5D, 0.5D);
      if (/*this.p.mod instanceof AutoConfig*/1>2) {
         this.dr(new String(new char[]{'A', 'u', 't', 'o', '-', 'c', 'o', 'n', 'f', 'i', 'g', 'u', 'r', 'e', ' ', 'm', 'o', 'd', 'u', 'l', 'e', 's', '.'}));
         GL11.glPopMatrix();
      } else {
         this.dr(this.isBinding ? BindKey.binding : BindKey.bind + ": " + Keyboard.getKeyName(this.p.mod.getKeycode()));
         GL11.glPopMatrix();
      }
   }

   public void uu(int x, int y) {
      this.h = this.i(x, y);
      this.y = this.p.c4t.gy() + this.o;
      this.x = this.p.c4t.gx();
   }

   public void onCl1ck(int x, int y, int b) {
      if (this.i(x, y) && b == 0 && this.p.po) {
         this.isBinding = !this.isBinding;
      }

   }

   public void ky(char t, int keybind) {
      if (!this.p.mod.getName().equalsIgnoreCase(new String(new char[]{'A', 'u', 't', 'o', 'C', 'o', 'n', 'f', 'i', 'g'}))) {
         if (this.isBinding) {
            if (keybind == 11) {
               if (this.p.mod instanceof Gui) {
                  this.p.mod.setbind(54);
               } else {
                  this.p.mod.setbind(0);
               }
            } else {
               this.p.mod.setbind(keybind);
            }

            this.isBinding = false;
         }

      }
   }

   public boolean i(int x, int y) {
      return x > this.x && x < this.x + this.p.c4t.gw() && y > this.y - 1 && y < this.y + 12;
   }

   public int gh() {
      return 16;
   }

   private void dr(String s) {
      Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(s, (float)((this.p.c4t.gx() + 4) * 2), (float)((this.p.c4t.gy() + this.o + 3) * 2), Color.HSBtoRGB((float)(System.currentTimeMillis() % 3750L) / 3750.0F, 0.8F, 0.8F));
   }
}
