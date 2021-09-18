//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.clickgui.components;

import java.awt.Color;

import keystrokesmod.clickgui.RenderComponent;
import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleSettingTick;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class ButtonTick extends RenderComponent {
   private final int c = (new Color(20, 255, 0)).getRGB();
   private final Module mod;
   private final ModuleSettingTick cl1ckbUtt0n;
   private final ButtonModule p;
   private int o;
   private int x;
   private int y;

   public ButtonTick(Module mod, ModuleSettingTick op, ButtonModule b, int o) {
      this.mod = mod;
      this.cl1ckbUtt0n = op;
      this.p = b;
      this.x = b.category.getX() + b.category.getWidth();
      this.y = b.category.getY() + b.o;
      this.o = o;
   }

   public static void e() {
      GL11.glDisable(2929);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glDepthMask(true);
      GL11.glEnable(2848);
      GL11.glHint(3154, 4354);
      GL11.glHint(3155, 4354);
   }

   public static void draw() {
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDisable(2848);
      GL11.glHint(3154, 4352);
      GL11.glHint(3155, 4352);
   }

   public static void draw(float x, float y, float x1, float y1, int c) {
      e();
      colour(c);
      draw(x, y, x1, y1);
      draw();
   }

   public static void draw(float x, float y, float x1, float y1) {
      GL11.glBegin(7);
      GL11.glVertex2f(x, y1);
      GL11.glVertex2f(x1, y1);
      GL11.glVertex2f(x1, y);
      GL11.glVertex2f(x, y);
      GL11.glEnd();
   }

   public static void colour(int h) {
      float a1pha = (float)(h >> 24 & 255) / 350.0F;
      GL11.glColor4f(0.0F, 0.0F, 0.0F, a1pha);
   }

   public void r3nd3r() {
      GL11.glPushMatrix();
      GL11.glScaled(0.5D, 0.5D, 0.5D);
      Minecraft.getMinecraft().fontRendererObj.drawString(this.cl1ckbUtt0n.isToggled() ? "[+]  " + this.cl1ckbUtt0n.getName() : "[-]  " + this.cl1ckbUtt0n.getName(), (float)((this.p.category.getX() + 4) * 2), (float)((this.p.category.getY() + this.o + 4) * 2), this.cl1ckbUtt0n.isToggled() ? this.c : -1, false);
      GL11.glPopMatrix();
   }

   public void so(int n) {
      this.o = n;
   }

   public void render(int x, int y) {
      this.y = this.p.category.getY() + this.o;
      this.x = this.p.category.getX();
   }

   public void onCl1ck(int x, int y, int b) {
      if (this.i(x, y) && b == 0 && this.p.po) {
         this.cl1ckbUtt0n.toggle();
         this.mod.guiButtonToggled(this.cl1ckbUtt0n);
      }

   }

   public boolean i(int x, int y) {
      return x > this.x && x < this.x + this.p.category.getWidth() && y > this.y && y < this.y + 11;
   }
}
