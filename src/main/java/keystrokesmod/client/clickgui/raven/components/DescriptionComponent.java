
package keystrokesmod.client.clickgui.raven.components;

import keystrokesmod.client.clickgui.raven.Component;
import keystrokesmod.client.module.setting.impl.DescriptionSetting;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class DescriptionComponent implements Component {
   private final int c = (new Color(226, 83, 47)).getRGB();
   private final DescriptionSetting desc;
   private final ModuleComponent p;
   private int o;

   public DescriptionComponent(DescriptionSetting desc, ModuleComponent b, int o) {
      this.desc = desc;
      this.p = b;
      int x = b.category.getX() + b.category.getWidth();
      int y = b.category.getY() + b.o;
      this.o = o;
   }

   public void draw() {
      GL11.glPushMatrix();
      GL11.glScaled(0.5D, 0.5D, 0.5D);
      Minecraft.getMinecraft().fontRendererObj.drawString(this.desc.getDesc(), (float)((this.p.category.getX() + 4) * 2), (float)((this.p.category.getY() + this.o + 4) * 2), this.c, true);
      GL11.glPopMatrix();
   }

   @Override
   public void update(int mousePosX, int mousePosY) {

   }

   @Override
   public void mouseDown(int x, int y, int b) {

   }

   @Override
   public void mouseReleased(int x, int y, int m) {

   }

   @Override
   public void keyTyped(char t, int k) {

   }

   public void setComponentStartAt(int n) {
      this.o = n;
   }

   @Override
   public int getHeight() {
      return 0;
   }
}
