//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod;

import java.awt.Color;

import keystrokesmod.module.ModuleDesc;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class d1 extends b {
   private final int c = (new Color(226, 83, 47)).getRGB();
   private final ModuleDesc desc;
   private final m3 p;
   private int o;

   public d1(ModuleDesc desc, m3 b, int o) {
      this.desc = desc;
      this.p = b;
      int x = b.c4t.gx() + b.c4t.gw();
      int y = b.c4t.gy() + b.o;
      this.o = o;
   }

   public void r3nd3r() {
      GL11.glPushMatrix();
      GL11.glScaled(0.5D, 0.5D, 0.5D);
      Minecraft.getMinecraft().fontRendererObj.drawString(this.desc.getDesc(), (float)((this.p.c4t.gx() + 4) * 2), (float)((this.p.c4t.gy() + this.o + 4) * 2), this.c, true);
      GL11.glPopMatrix();
   }

   public void so(int n) {
      this.o = n;
   }
}
