//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;

import keystrokesmod.main.Ravenb3;
import keystrokesmod.module.Module;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.opengl.GL11;

public class GuiModuleCategory {
   public ArrayList<b> modulesInCategory = new ArrayList();
   public Module.category categoryName;
   private boolean categoryOpened;
   private int k;
   private int y;
   private int x;
   private int bh;
   private boolean id;
   public int xx;
   public int yy;
   public boolean n4m = false;
   public String pvp;
   public boolean pin = false;
   private int chromaSpeed;
   private double marginY, marginX;

   public GuiModuleCategory(Module.category category) {
      this.categoryName = category;
      this.k = 92;
      this.x = 5;
      this.y = 5;
      this.bh = 13;
      this.xx = 0;
      this.categoryOpened = false;
      this.id = false;
      this.chromaSpeed = 3;
      int tY = this.bh + 3;
      this.marginX = 80;
      this.marginY = 4.5;

      for(Iterator var3 = Ravenb3.notAName.getm0dmanager().inCateg(this.categoryName).iterator(); var3.hasNext(); tY += 16) {
         Module mod = (Module) var3.next();
         m3 b = new m3(mod, this, tY);
         this.modulesInCategory.add(b);
      }

   }

   public GuiModuleCategory(String d) {
      this.k = 92;
      this.x = 5;
      this.y = 5;
      this.bh = 13;
      this.xx = 0;
      this.categoryOpened = false;
      this.id = false;
      int tY = this.bh;
      this.pvp = d;
      this.n4m = true;
   }

   public ArrayList<b> getModules() {
      return this.modulesInCategory;
   }

   public void x(int n) {
      this.x = n;
   }

   public void addCategoryWithOffset(int y) {
      this.y = y;
   }

   public void d(boolean d) {
      this.id = d;
   }

   public boolean p() {
      return this.pin;
   }

   public void cv(boolean on) {
      this.pin = on;
   }

   public boolean fv() {
      return this.categoryOpened;
   }

   public void oo(boolean on) {
      this.categoryOpened = on;
   }

   public void rf(FontRenderer renderer) {
      this.k = 92;
      if (!this.modulesInCategory.isEmpty() && this.categoryOpened) {
         int h = 0;

         b c;
         for(Iterator moduleInCategoryIterator = this.modulesInCategory.iterator(); moduleInCategoryIterator.hasNext(); h += c.getH()) {
            c = (b)moduleInCategoryIterator.next();
         }

         net.minecraft.client.gui.Gui.drawRect(this.x - 2, this.y, this.x + this.k + 2, this.y + this.bh + h + 4, (new Color(0, 0, 0, 110)).getRGB());
      }

      kk.d((float)(this.x - 2), (float)this.y, (float)(this.x + this.k + 2), (float)(this.y + this.bh + 3), -1);
      renderer.drawString(this.n4m ? this.pvp : this.categoryName.name(), (float)(this.x + 2), (float)(this.y + 4), Color.getHSBColor((float)(System.currentTimeMillis() % (7500L / (long)this.chromaSpeed)) / (7500.0F / (float)this.chromaSpeed), 1.0F, 1.0F).getRGB(), false);
      //renderer.drawString(this.n4m ? this.pvp : this.categoryName.name(), (float)(this.x + 2), (float)(this.y + 4), ay.astolfoColorsDraw(10, 14), false);
      if (!this.n4m) {
         GL11.glPushMatrix();
         //Opened/closed unicode... :yes: :holsum: :evil:
         renderer.drawString(this.categoryOpened ? "-" : "+", (float)(this.x + marginX), (float)((double)this.y + marginY), Color.white.getRGB(), false);
         GL11.glPopMatrix();
         if (this.categoryOpened && !this.modulesInCategory.isEmpty()) {
            Iterator var5 = this.modulesInCategory.iterator();

            while(var5.hasNext()) {
               b c2 = (b)var5.next();
               c2.r3nd3r();
            }
         }

      }
   }

   public void r3nd3r() {
      int o = this.bh + 3;

      b c;
      for(Iterator var2 = this.modulesInCategory.iterator(); var2.hasNext(); o += c.getH()) {
         c = (b)var2.next();
         c.so(o);
      }

   }

   public int gx() {
      return this.x;
   }

   public int gy() {
      return this.y;
   }

   public int gw() {
      return this.k;
   }

   public void up(int x, int y) {
      if (this.id) {
         this.x(x - this.xx);
         this.addCategoryWithOffset(y - this.yy);
      }

   }

   public boolean i(int x, int y) {
      return x >= this.x + 92 - 13 && x <= this.x + this.k && (float)y >= (float)this.y + 2.0F && y <= this.y + this.bh + 1;
   }

   public boolean d(int x, int y) {
      return x >= this.x + 77 && x <= this.x + this.k - 6 && (float)y >= (float)this.y + 2.0F && y <= this.y + this.bh + 1;
   }

   public boolean v(int x, int y) {
      return x >= this.x && x <= this.x + this.k && y >= this.y && y <= this.y + this.bh;
   }
}
