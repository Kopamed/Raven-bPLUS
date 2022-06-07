
package keystrokesmod.client.clickgui.raven;

import keystrokesmod.client.clickgui.raven.components.CategoryComponent;
import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.utils.Timer;
import keystrokesmod.client.utils.Utils;
import keystrokesmod.client.utils.version.Version;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraftforge.fml.client.config.GuiButtonExt;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ClickGui extends GuiScreen {
   private ScheduledFuture<?> sf;
   private Timer aT;
   private Timer aL;
   private Timer aE;
   private Timer aR;
   private ScaledResolution sr;
   private GuiButtonExt s;
   private GuiTextField c;
   private final  ArrayList<CategoryComponent> categoryList;

   public ClickGui() {
      this.categoryList = new ArrayList<>();
      int topOffset = 5;
      Module.ModuleCategory[] values;
      int categoryAmount = (values = Module.ModuleCategory.values()).length;

      for(int category = 0; category < categoryAmount; ++category) {
         Module.ModuleCategory moduleCategory = values[category];
         CategoryComponent currentModuleCategory = new CategoryComponent(moduleCategory);
         currentModuleCategory.setY(topOffset);
         categoryList.add(currentModuleCategory);
         topOffset += 20;
      }
   }

   public void initMain() {
      (this.aT = this.aE = this.aR = new Timer(500.0F)).start();
      this.sf = Raven.getExecutor().schedule(() -> (
              this.aL = new Timer(650.0F)
      ).start(), 650L, TimeUnit.MILLISECONDS);

   }

   public void initGui() {
      super.initGui();
      this.sr = new ScaledResolution(this.mc);
      (this.c = new GuiTextField(1, this.mc.fontRendererObj, 22, this.height - 100, 150, 20)).setMaxStringLength(256);
      this.buttonList.add(this.s = new GuiButtonExt(2, 22, this.height - 70, 150, 20, "Send"));
      this.s.visible = keystrokesmod.client.module.modules.client.CommandLine.a;
   }

   public void drawScreen(int x, int y, float p) {
      Version clientVersion = Raven.versionManager.getClientVersion();
      Version latestVersion = Raven.versionManager.getLatestVersion();

      drawRect(0, 0, this.width, this.height, (int)(this.aR.getValueFloat(0.0F, 0.7F, 2) * 255.0F) << 24);
      int quarterScreenHeight = this.height / 4;
      int halfScreenWidth = this.width / 2;
      int w_c = 30 - this.aT.getValueInt(0, 30, 3);
      this.drawCenteredString(this.fontRendererObj, "r", halfScreenWidth + 1 - w_c, quarterScreenHeight - 25, Utils.Client.rainbowDraw(2L, 1500L));
      this.drawCenteredString(this.fontRendererObj, "a", halfScreenWidth - w_c, quarterScreenHeight - 15, Utils.Client.rainbowDraw(2L, 1200L));
      this.drawCenteredString(this.fontRendererObj, "v", halfScreenWidth - w_c, quarterScreenHeight - 5, Utils.Client.rainbowDraw(2L, 900L));
      this.drawCenteredString(this.fontRendererObj, "e", halfScreenWidth - w_c, quarterScreenHeight + 5, Utils.Client.rainbowDraw(2L, 600L));
      this.drawCenteredString(this.fontRendererObj, "n", halfScreenWidth - w_c, quarterScreenHeight + 15, Utils.Client.rainbowDraw(2L, 300L));
      this.drawCenteredString(this.fontRendererObj, "b+", halfScreenWidth + 1 + w_c, quarterScreenHeight + 30, Utils.Client.rainbowDraw(2L, 0L));

      float speed = 4890;

      if(latestVersion.isNewerThan(clientVersion)){
         int margin = 2;
         int rows = 1;
         for (int i = Raven.updateText.length-1; i >= 0; i--) {
            String up = Raven.updateText[i];
            mc.fontRendererObj.drawString(up, halfScreenWidth - this.fontRendererObj.getStringWidth(up) / 2, this.height - this.fontRendererObj.FONT_HEIGHT * rows - margin, Utils.Client.astolfoColorsDraw(10, 28, speed));
            rows++;
            margin += 2;
         }
      }else {
         mc.fontRendererObj.drawString("Raven B+ v" + clientVersion, 4, this.height - 3 - mc.fontRendererObj.FONT_HEIGHT, Utils.Client.astolfoColorsDraw(10, 14, speed));
      }

      this.drawVerticalLine(halfScreenWidth - 10 - w_c, quarterScreenHeight - 30, quarterScreenHeight + 43, Color.white.getRGB());
      this.drawVerticalLine(halfScreenWidth + 10 + w_c, quarterScreenHeight - 30, quarterScreenHeight + 43, Color.white.getRGB());
      int r;
      if (this.aL != null) {
         r = this.aL.getValueInt(0, 20, 2);
         this.drawHorizontalLine(halfScreenWidth - 10, halfScreenWidth - 10 + r, quarterScreenHeight - 29, -1);
         this.drawHorizontalLine(halfScreenWidth + 10, halfScreenWidth + 10 - r, quarterScreenHeight + 42, -1);
      }

      for (CategoryComponent category : categoryList) {
         category.rf(this.fontRendererObj);
         category.up(x, y);

         for (Component module : category.getModules()) {
            module.compute(x, y);
         }
      }


      // PLAYER
      GuiInventory.drawEntityOnScreen(this.width + 15 - this.aE.getValueInt(0, 40, 2), this.height - 19 - this.fontRendererObj.FONT_HEIGHT, 40, (float)(this.width - 25 - x), (float)(this.height - 50 - y), this.mc.thePlayer);

      if (keystrokesmod.client.module.modules.client.CommandLine.a) {
         if (!this.s.visible) {
            this.s.visible = true;
         }

         r = keystrokesmod.client.module.modules.client.CommandLine.animate.isToggled() ? keystrokesmod.client.module.modules.client.CommandLine.an.getValueInt(0, 200, 2) : 200;
         if (keystrokesmod.client.module.modules.client.CommandLine.b) {
            r = 200 - r;
            if (r == 0) {
               keystrokesmod.client.module.modules.client.CommandLine.b = false;
               keystrokesmod.client.module.modules.client.CommandLine.a = false;
               this.s.visible = false;
            }
         }

         drawRect(0, 0, r, this.height, -1089466352);
         this.drawHorizontalLine(0, r - 1, 0, -1);
         this.drawHorizontalLine(0, r - 1, this.height - 115, -1);
         drawRect(r - 1, 0, r, this.height, -1);
         CommandLine.rc(this.fontRendererObj, this.height, r, this.sr.getScaleFactor());
         int x2 = r - 178;
         this.c.xPosition = x2;
         this.s.xPosition = x2;
         this.c.drawTextBox();
         super.drawScreen(x, y, p);
      } else if (keystrokesmod.client.module.modules.client.CommandLine.b) {
         keystrokesmod.client.module.modules.client.CommandLine.b = false;
      }

   }

   public void mouseClicked(int x, int y, int mouseButton) throws IOException {
      Iterator<CategoryComponent> btnCat = categoryList.iterator();

      while(true) {
         CategoryComponent category;
         do {
            do {
               if (!btnCat.hasNext()) {
                  if (keystrokesmod.client.module.modules.client.CommandLine.a) {
                     this.c.mouseClicked(x, y, mouseButton);
                     super.mouseClicked(x, y, mouseButton);
                  }

                  return;
               }

               category = btnCat.next();
               if (category.insideArea(x, y) && !category.i(x, y) && !category.mousePressed(x, y) && mouseButton == 0) {
                  category.mousePressed(true);
                  category.xx = x - category.getX();
                  category.yy = y - category.getY();
               }

               if (category.mousePressed(x, y) && mouseButton == 0) {
                  category.setOpened(!category.isOpened());
               }

               if (category.i(x, y) && mouseButton == 0) {
                  category.cv(!category.p());
               }
            } while(!category.isOpened());
         } while(category.getModules().isEmpty());

         for (Component c : category.getModules()) {
            c.mouseDown(x, y, mouseButton);
         }
      }
   }

   public void mouseReleased(int x, int y, int s) {
      if (s == 0) {
         Iterator<CategoryComponent> btnCat = categoryList.iterator();

         CategoryComponent c4t;
         while(btnCat.hasNext()) {
            c4t = btnCat.next();
            c4t.mousePressed(false);
         }

         btnCat = categoryList.iterator();

         while(true) {
            do {
               do {
                  if (!btnCat.hasNext()) {
                     return;
                  }

                  c4t = btnCat.next();
               } while(!c4t.isOpened());
            } while(c4t.getModules().isEmpty());

            for (Component c : c4t.getModules()) {
               c.mouseReleased(x, y, s);
            }
         }
      }
      if(Raven.clientConfig != null){
         Raven.clientConfig.saveConfig();
      }
   }

   public void keyTyped(char t, int k) {
      if (k == 1) {
         this.mc.displayGuiScreen(null);
      } else {
         Iterator<CategoryComponent> btnCat = categoryList.iterator();

         while(true) {
            CategoryComponent cat;
            do {
               do {
                  if (!btnCat.hasNext()) {
                     if (keystrokesmod.client.module.modules.client.CommandLine.a) {
                        String cm = this.c.getText();
                        if (k == 28 && !cm.isEmpty()) {
                           CommandLine.rCMD(this.c.getText());
                           this.c.setText("");
                           return;
                        }

                        this.c.textboxKeyTyped(t, k);
                     }

                     return;
                  }

                  cat = btnCat.next();
               } while(!cat.isOpened());
            } while(cat.getModules().isEmpty());

            for (Component c : cat.getModules()) {
               c.ky(t, k);
            }
         }
      }
   }

   public void actionPerformed(GuiButton b) {
      if (b == this.s) {
         CommandLine.rCMD(this.c.getText());
         this.c.setText("");
      }
   }

   public void onGuiClosed() {
      this.aL = null;
      if (this.sf != null) {
         this.sf.cancel(true);
         this.sf = null;
      }
      Raven.configManager.save();
   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   public ArrayList<CategoryComponent> getCategoryList() {
      return categoryList;
   }
}
