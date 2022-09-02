
package keystrokesmod.client.clickgui.raven.components;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.lwjgl.opengl.GL11;

import keystrokesmod.client.clickgui.raven.Component;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.modules.client.GuiModule;
import keystrokesmod.client.module.modules.client.GuiModule.Preset;
import keystrokesmod.client.module.setting.Setting;
import keystrokesmod.client.module.setting.impl.ComboSetting;
import keystrokesmod.client.module.setting.impl.DescriptionSetting;
import keystrokesmod.client.module.setting.impl.DoubleSliderSetting;
import keystrokesmod.client.module.setting.impl.RGBSetting;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.Utils;
import keystrokesmod.client.utils.font.FontUtil;
import net.minecraft.client.Minecraft;

public class ModuleComponent implements Component {
   public Module mod;
   public CategoryComponent category;
   public int o;
   private ArrayList<Component> settings;
   public boolean po;

   public ModuleComponent(Module mod, CategoryComponent p, int o) {
      this.mod = mod;
      this.category = p;
      this.o = o;
      this.settings = new ArrayList<>();
      this.po = false;
      int y = o + 12;
      mod.setModuleComponent(this);
      updateSettings();
   }
   
   public void updateSettings() {
	   //ill fix this later but cannot be fked rn
	   ArrayList<Component> newSettings = new ArrayList<Component>();
	   int y = o + 12;
	   if (!mod.getSettings().isEmpty()) {
		   for (Setting v : mod.getSettings()) {
				   if (v instanceof SliderSetting) {
					   SliderSetting n = (SliderSetting) v;
					   SliderComponent s = new SliderComponent(n, this, y);
					   newSettings.add(s);
					   y += 16;
				   } else if (v instanceof TickSetting) {
					   TickSetting b = (TickSetting) v;
					   TickComponent c = new TickComponent(mod, b, this, y);
					   newSettings.add(c);
					   y += 12;
				   } else if (v instanceof DescriptionSetting) {
					   DescriptionSetting d = (DescriptionSetting) v;
					   DescriptionComponent m = new DescriptionComponent(d, this, y);
					   newSettings.add(m);
					   y += 12;
				   } else if (v instanceof DoubleSliderSetting) {
					   DoubleSliderSetting n = (DoubleSliderSetting) v;
					   RangeSliderComponent s = new RangeSliderComponent(n, this, y);
					   newSettings.add(s);
					   y += 16;
				   } else if (v instanceof ComboSetting) {
					   ComboSetting n = (ComboSetting) v;
					   ModeComponent s = new ModeComponent(n, this, y);
					   newSettings.add(s);
					   y += 12;
				   } else if (v instanceof RGBSetting) {
					   RGBSetting n = (RGBSetting) v;
					   RGBComponent s = new RGBComponent(n, this, y);
					   newSettings.add(s);
					   y += 12;
				   }
		   }
	   } 
	   if(mod.isBindable())
		   newSettings.add(new BindComponent(this, y));
	   settings = newSettings;
	   if(po) {
		   this.category.r3nd3r();
	   }
   }

   public void setComponentStartAt(int n) {
      this.o = n;
      int y = this.o + 16;

      for (Component c : this.settings) {
         c.setComponentStartAt(y);
         if (c instanceof SliderComponent || c instanceof RangeSliderComponent || c instanceof RGBComponent) {
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

   public static void g(int[] rgb) {
	   float a = 255;
	   float r = (float) rgb[0]/255f;
	   float g = (float) rgb[1]/255f;
	   float b = (float) rgb[2]/255f;
      GL11.glColor4f(r, g, b, a);
   }

   public static void v(float x, float y, float x1, float y1, int[] t, int[] b) {
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
	  if(GuiModule.usePreset.isToggled()) {
		  GuiModule.Preset preset = (Preset) GuiModule.preset.getMode();
		  if(preset.showGradientEnabled && mod.isEnabled()) {
			   v((float)this.category.getX(),
					   (float)(this.category.getY() + this.o),
					   (float)(this.category.getX() + this.category.getWidth()),
					   (float)(this.category.getY() + 15 + this.o),
					   new int[] {preset.enabledTopRGB.getRed(), preset.enabledTopRGB.getGreen(), preset.enabledTopRGB.getBlue()} ,
					   new int[] {preset.enabledBottomRGB.getRed(), preset.enabledBottomRGB.getGreen(), preset.enabledBottomRGB.getBlue()});
		   } else if (preset.showGradientDisabled && !mod.isEnabled()) {
			   v((float)this.category.getX(),
					   (float)(this.category.getY() + this.o),
					   (float)(this.category.getX() + this.category.getWidth()),
					   (float)(this.category.getY() + 15 + this.o),
					   new int[] {preset.disabledTopRGB.getRed(), preset.disabledTopRGB.getGreen(), preset.disabledTopRGB.getBlue()} ,
					   new int[] {preset.disabledBottomRGB.getRed(), preset.disabledBottomRGB.getGreen(), preset.disabledBottomRGB.getBlue()});
		   }
	      GL11.glPushMatrix();
	      // module text button
	      int button_rgb = preset.enabledTextRGB.getRGB();
	      if (this.mod.isEnabled()) {
	    	  button_rgb = preset.enabledTextRGB.getRGB();
	      } else if (this.mod.canBeEnabled()) {
	    	  button_rgb = preset.disabledTextRGB.getRGB();
	      } else {
	    	  button_rgb = new Color(102, 102, 102).getRGB();
	      }
	      if(preset.useCustomFont) {
	    	  FontUtil.normal.drawCenteredString(this.mod.getName(), (float)(this.category.getX() + this.category.getWidth()/2), (float)(this.category.getY() + this.o + 4), button_rgb);
	      } else {
	    	  Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.mod.getName(), (float)(this.category.getX() + this.category.getWidth() / 2 - Minecraft.getMinecraft().fontRendererObj.getStringWidth(this.mod.getName()) / 2), (float)(this.category.getY() + this.o + 4), button_rgb);
	      }
	      GL11.glPopMatrix();
	      
	  } else {
		  if(GuiModule.showGradientEnabled.isToggled() && mod.isEnabled()) {
			   v((float)this.category.getX(),
					   (float)(this.category.getY() + this.o),
					   (float)(this.category.getX() + this.category.getWidth()),
					   (float)(this.category.getY() + 15 + this.o),
					   GuiModule.enabledTopRGB.getColors(),
					   GuiModule.enabledBottomRGB.getColors());
		   } else if (GuiModule.showGradientDisabled.isToggled() && !mod.isEnabled()) {
			   v((float)this.category.getX(),
					   (float)(this.category.getY() + this.o),
					   (float)(this.category.getX() + this.category.getWidth()),
					   (float)(this.category.getY() + 15 + this.o),
					   GuiModule.disabledTopRGB.getColors(),
					   GuiModule.disabledBottomRGB.getColors());
		   }
	      GL11.glPushMatrix();
	      // module text button
	      int button_rgb = GuiModule.enabledTextRGB.getRGB();
	      if (this.mod.isEnabled()) {
	    	  button_rgb = GuiModule.enabledTextRGB.getRGB();
	      } else if (this.mod.canBeEnabled()) {
	    	  button_rgb = GuiModule.disabledTextRGB.getRGB();
	      } else {
	    	  button_rgb = new Color(102, 102, 102).getRGB();
	      }
	      if(GuiModule.useCustomFont.isToggled()) {
	    	  FontUtil.normal.drawCenteredString(this.mod.getName(), (float)(this.category.getX() + this.category.getWidth()/2), (float)(this.category.getY() + this.o + 4), button_rgb);
	      } else {
	    	  Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.mod.getName(), (float)(this.category.getX() + this.category.getWidth() / 2 - Minecraft.getMinecraft().fontRendererObj.getStringWidth(this.mod.getName()) / 2), (float)(this.category.getY() + this.o + 4), button_rgb);
	      }
	      GL11.glPopMatrix();
	  }
	   
	   
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
            if (c instanceof SliderComponent || c instanceof RangeSliderComponent || c instanceof RGBComponent) {
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
         if(!po) {
        	 if(mod.getSettings().size() > 0) {
        		 this.category.loadSpecificModule(mod);
        	 }
        	 po = true;
         } else if (po) {
        	 po = false;
        	 this.category.moduleOpened = false;
         }
         this.category.r3nd3r();
      }

      for (Component c : this.settings) {
         c.mouseDown(x, y, b);
      }

   }

   public void mouseReleased(int x, int y, int m) {
      for (Component c : this.settings) {
         c.mouseReleased(x, y, m);
         //updateSettings();
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