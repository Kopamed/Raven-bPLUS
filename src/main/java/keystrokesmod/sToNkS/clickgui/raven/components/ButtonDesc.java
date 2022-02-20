
package keystrokesmod.sToNkS.clickgui.raven.components;

import keystrokesmod.sToNkS.clickgui.raven.Component;
import keystrokesmod.sToNkS.module.ModuleDesc;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class ButtonDesc extends Component {
   private final int c = (new Color(226, 83, 47)).getRGB();
   private final ModuleDesc desc;
   private final ButtonModule p;
   private int o;

   public ButtonDesc(ModuleDesc desc, ButtonModule b, int o) {
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

   public void setModuleStartAt(int n) {
      this.o = n;
   }
}
