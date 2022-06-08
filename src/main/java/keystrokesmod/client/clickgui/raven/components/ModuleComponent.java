
package keystrokesmod.client.clickgui.raven.components;

import keystrokesmod.client.clickgui.raven.Component;
import keystrokesmod.client.module.*;
import keystrokesmod.client.module.modules.client.GuiModule;
import keystrokesmod.client.module.setting.Setting;
import keystrokesmod.client.module.setting.impl.*;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;

public class ModuleComponent implements Component {
   private final int c1 = (new Color(0, 85, 255)).getRGB();
   private final int c2 = (new Color(154, 2, 255)).getRGB();
   private final int c3 = (new Color(175, 143, 233) ).getRGB();
   public Module mod;
   public CategoryComponent category;
   public int o;
   private final ArrayList<Component> settings;
   public boolean po;

   public ModuleComponent(Module mod, CategoryComponent p, int o) {
      this.mod = mod;
      this.category = p;
      this.o = o;
      this.settings = new ArrayList<>();
      this.po = false;
      int y = o + 12;
      if (!mod.getSettings().isEmpty()) {
         for (Setting v : mod.getSettings()) {
            if (v instanceof SliderSetting) {
               SliderSetting n = (SliderSetting) v;
               SliderComponent s = new SliderComponent(n, this, y);
               this.settings.add(s);
               y += 16;
            } else if (v instanceof TickSetting) {
               TickSetting b = (TickSetting) v;
               TickComponent c = new TickComponent(mod, b, this, y);
               this.settings.add(c);
               y += 12;
            } else if (v instanceof DescriptionSetting) {
               DescriptionSetting d = (DescriptionSetting) v;
               DescriptionComponent m = new DescriptionComponent(d, this, y);
               this.settings.add(m);
               y += 12;
            } else if (v instanceof DoubleSliderSetting) {
               DoubleSliderSetting n = (DoubleSliderSetting) v;
               RangeSliderComponent s = new RangeSliderComponent(n, this, y);
               this.settings.add(s);
               y += 16;
            }else if (v instanceof ComboSetting) {
               ComboSetting n = (ComboSetting) v;
               ModeComponent s = new ModeComponent(n, this, y);
               this.settings.add(s);
               y += 12;
            }
         }
      }

      this.settings.add(new BindComponent(this, y));
   }

   public void setComponentStartAt(int n) {
      this.o = n;
      int y = this.o + 16;

      for (Component c : this.settings) {
         c.setComponentStartAt(y);
         if (c instanceof SliderComponent || c instanceof RangeSliderComponent) {
            y += 16;
         } else if (c instanceof TickComponent || c instanceof DescriptionComponent || c instanceof ModeComponent || c instanceof BindComponent) {
            y += 12;
         }
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
      if (GuiModule.guiTheme.getInput() == 1.0D) {
         a = (float)(h >> 14 & 255) / 255.0F;
         r = (float)(h >> 5 & 255) / 255.0F;
         g = (float)(h >> 5 & 255) / 2155.0F;
         b = (float)(h & 255);
      } else if (GuiModule.guiTheme.getInput() == 2.0D) {
         a = (float)(h >> 14 & 255) / 255.0F;
         r = (float)(h >> 5 & 255) / 2155.0F;
         g = (float)(h >> 5 & 255) / 255.0F;
         b = (float)(h & 255);
      } else if (GuiModule.guiTheme.getInput() == 3.0D) {
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

   public void draw() {
      v((float)this.category.getX(), (float)(this.category.getY() + this.o), (float)(this.category.getX() + this.category.getWidth()), (float)(this.category.getY() + 15 + this.o), this.mod.isEnabled() ? this.c2 : -12829381, this.mod.isEnabled() ? this.c2 : -12302777);
      GL11.glPushMatrix();
      // module text button
      int button_rgb;
      switch ((int) GuiModule.guiTheme.getInput()) {
         case 4:
            if (this.mod.isEnabled()) {
               button_rgb = this.c3;
            } else if (this.mod.canBeEnabled()) {
               button_rgb = Color.lightGray.getRGB();
            } else {
               button_rgb = new Color(102, 102, 102).getRGB();
            }
            break;
         case 3:
            if (this.mod.isEnabled()) {
               button_rgb = this.c1;
            } else if (this.mod.canBeEnabled()) {
               button_rgb = Color.lightGray.getRGB();
            } else {
               button_rgb = new Color(102, 102, 102).getRGB();
            }
            break;
         default:
            if (this.mod.canBeEnabled()) {
               button_rgb = Color.lightGray.getRGB();
            } else {
               button_rgb = new Color(102, 102, 102).getRGB();
            }
            break;
      }
      Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.mod.getName(), (float)(this.category.getX() + this.category.getWidth() / 2 - Minecraft.getMinecraft().fontRendererObj.getStringWidth(this.mod.getName()) / 2), (float)(this.category.getY() + this.o + 4), button_rgb);
      GL11.glPopMatrix();
      if (this.po && !this.settings.isEmpty()) {
         for (Component c : this.settings) {
            c.draw();
         }
      }


   }

   public int getHeight() {
      if (!this.po) {
         return 16;
      } else {
         int h = 16;

         for (Component c : this.settings) {
            if (c instanceof SliderComponent || c instanceof RangeSliderComponent) {
               h += 16;
            } else if (c instanceof TickComponent || c instanceof DescriptionComponent || c instanceof ModeComponent || c instanceof BindComponent) {
               h += 12;
            }
         }

         return h;

      }
   }

   public void update(int mousePosX, int mousePosY) {
      if (!this.settings.isEmpty()) {
         for (Component c : this.settings) {
            c.update(mousePosX, mousePosY);
         }
      }

   }

   public void mouseDown(int x, int y, int b) {
      if (mod.canBeEnabled()) {
         if (this.ii(x, y) && b == 0) {
            this.mod.toggle();
         }
      }

      if (this.ii(x, y) && b == 1) {
         this.po = !this.po;
         this.category.r3nd3r();
      }

      for (Component c : this.settings) {
         c.mouseDown(x, y, b);
      }

   }

   public void mouseReleased(int x, int y, int m) {
      for (Component c : this.settings) {
         c.mouseReleased(x, y, m);
      }

   }

   public void keyTyped(char t, int k) {
      for (Component c : this.settings) {
         c.keyTyped(t, k);
      }

   }

   public boolean ii(int x, int y) {
      return x > this.category.getX() && x < this.category.getX() + this.category.getWidth() && y > this.category.getY() + this.o && y < this.category.getY() + 16 + this.o;
   }
}
