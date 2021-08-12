//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.module.modules;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import keystrokesmod.*;
import keystrokesmod.main.NotAName;
import keystrokesmod.module.*;
import net.minecraft.client.gui.*;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;

import javax.swing.text.Position;

public class HUD extends Module {
   public static ModuleSettingTick editPosition;
   public static ModuleSettingTick dropShadow;
   public static ModuleSettingTick alphabeticalSort;
   public static ModuleSettingSlider colourMode;
   public static ModuleDesc colourModeDesc;
   private static int hudX = 5;
   private static int hudY = 70;
   public static ru.PositionMode positionMode;

   public HUD() {
      super("HUD", Module.category.render, 0);
      this.registerSetting(editPosition = new ModuleSettingTick("Edit position", false));
      this.registerSetting(dropShadow = new ModuleSettingTick("Drop shadow", true));
      this.registerSetting(alphabeticalSort = new ModuleSettingTick("Alphabetical sort", false));
      this.registerSetting(colourMode = new ModuleSettingSlider("Value: ", 1, 1, 5, 1));
      this.registerSetting(colourModeDesc = new ModuleDesc("Mode: RAVEN"));
   }

   public void guiUpdate(){
      colourModeDesc.setDesc(ay.md + ColourModes.values()[(int) colourMode.getInput()-1]);
   }

   public void onEnable() {
      ModuleManager.sort();
   }

   public void guiButtonToggled(ModuleSettingTick b) {
      if (b == editPosition) {
         editPosition.disable();
         mc.displayGuiScreen(new EditHudPositionScreen());
      } else if (b == alphabeticalSort) {
         ModuleManager.sort();
      }

   }

   @SubscribeEvent
   public void a(RenderTickEvent ev) {
      // IK THIS METHOD IS INNEFECTIVE I WILL OPTIMISE IT LATER DONT BULLY ME

      if (ev.phase == Phase.END && ay.isPlayerInGame()) {
         if (mc.currentScreen != null || mc.gameSettings.showDebugInfo) {
            return;
         }

         int margin = 2;
         int y = hudY;
         int del = 0;
         if (!alphabeticalSort.isToggled()){
            if (positionMode == ru.PositionMode.UPLEFT || positionMode == ru.PositionMode.UPRIGHT) {
               ModuleManager.sortShortLong();
            }
            else if(positionMode == ru.PositionMode.DOWNLEFT || positionMode == ru.PositionMode.DOWNRIGHT) {
               ModuleManager.sortLongShort();
            }
         }
         List<Module> en = new ArrayList(NotAName.moduleManager.enModsList);
         if(en.isEmpty()) return;

         int textBoxWidth = ModuleManager.getLongestActiveModule(mc.fontRendererObj);
         int textBoxHeight = ModuleManager.getBoxHeight(mc.fontRendererObj, margin);
         //System.out.println(mc.displayWidth + " " + mc.displayHeight + " || " + hudX + " " + hudY);

         if(hudX < 0) {
            hudX = margin;
         }
         if(hudY < 0) {
            hudY = margin;
         }

         if(hudX + textBoxWidth > mc.displayWidth/2){
            hudX = mc.displayWidth/2 - textBoxWidth - margin;
         }

         if(hudY + textBoxHeight > mc.displayHeight/2){
            hudY = mc.displayHeight/2 - textBoxHeight;
         }


         Iterator var5 = en.iterator();

         while(var5.hasNext()) {
            Module m = (Module)var5.next();
            if (m.isEnabled() && m != this) {
               if(HUD.positionMode == ru.PositionMode.DOWNRIGHT || HUD.positionMode == ru.PositionMode.UPRIGHT){
                  if (ColourModes.values()[(int) colourMode.getInput() - 1] == ColourModes.RAVEN) {
                     mc.fontRendererObj.drawString(m.getName(), (float) hudX + (textBoxWidth - mc.fontRendererObj.getStringWidth(m.getName())), (float) y, ay.rainbowDraw(2L, (long) del), dropShadow.isToggled());
                     y += mc.fontRendererObj.FONT_HEIGHT + margin;
                     del -= 120;
                  } else if (ColourModes.values()[(int) colourMode.getInput() - 1] == ColourModes.RAVEN2) {
                     mc.fontRendererObj.drawString(m.getName(), (float) hudX + (textBoxWidth - mc.fontRendererObj.getStringWidth(m.getName())), (float) y, ay.rainbowDraw(2L, del), dropShadow.isToggled());
                     y += mc.fontRendererObj.FONT_HEIGHT + margin;
                     del -= 10;
                  } else if (ColourModes.values()[(int) colourMode.getInput() - 1] == ColourModes.ASTOLFO) {
                     mc.fontRendererObj.drawString(m.getName(), (float) hudX + (textBoxWidth - mc.fontRendererObj.getStringWidth(m.getName())), (float) y, ay.astolfoColorsDraw(10, 14), dropShadow.isToggled());
                     y += mc.fontRendererObj.FONT_HEIGHT + margin;
                     del -= 120;
                  } else if (ColourModes.values()[(int) colourMode.getInput() - 1] == ColourModes.ASTOLFO2) {
                     mc.fontRendererObj.drawString(m.getName(), (float) hudX + (textBoxWidth - mc.fontRendererObj.getStringWidth(m.getName())), (float) y, ay.astolfoColorsDraw(10, del), dropShadow.isToggled());
                     y += mc.fontRendererObj.FONT_HEIGHT + margin;
                     del -= 120;
                  } else if (ColourModes.values()[(int) colourMode.getInput() - 1] == ColourModes.ASTOLFO3) {
                     mc.fontRendererObj.drawString(m.getName(), (float) hudX + (textBoxWidth - mc.fontRendererObj.getStringWidth(m.getName())), (float) y, ay.astolfoColorsDraw(10, del), dropShadow.isToggled());
                     y += mc.fontRendererObj.FONT_HEIGHT + margin;
                     del -= 10;
                  }
               } else {
                  if (ColourModes.values()[(int) colourMode.getInput() - 1] == ColourModes.RAVEN) {
                     mc.fontRendererObj.drawString(m.getName(), (float) hudX, (float) y, ay.rainbowDraw(2L, (long) del), dropShadow.isToggled());
                     y += mc.fontRendererObj.FONT_HEIGHT + margin;
                     del -= 120;
                  } else if (ColourModes.values()[(int) colourMode.getInput() - 1] == ColourModes.RAVEN2) {
                     mc.fontRendererObj.drawString(m.getName(), (float) hudX, (float) y, ay.rainbowDraw(2L, del), dropShadow.isToggled());
                     y += mc.fontRendererObj.FONT_HEIGHT + margin;
                     del -= 10;
                  } else if (ColourModes.values()[(int) colourMode.getInput() - 1] == ColourModes.ASTOLFO) {
                     mc.fontRendererObj.drawString(m.getName(), (float) hudX, (float) y, ay.astolfoColorsDraw(10, 14), dropShadow.isToggled());
                     y += mc.fontRendererObj.FONT_HEIGHT + margin;
                     del -= 120;
                  } else if (ColourModes.values()[(int) colourMode.getInput() - 1] == ColourModes.ASTOLFO2) {
                     mc.fontRendererObj.drawString(m.getName(), (float) hudX, (float) y, ay.astolfoColorsDraw(10, del), dropShadow.isToggled());
                     y += mc.fontRendererObj.FONT_HEIGHT + margin;
                     del -= 120;
                  } else if (ColourModes.values()[(int) colourMode.getInput() - 1] == ColourModes.ASTOLFO3) {
                     mc.fontRendererObj.drawString(m.getName(), (float) hudX, (float) y, ay.astolfoColorsDraw(10, del), dropShadow.isToggled());
                     y += mc.fontRendererObj.FONT_HEIGHT + margin;
                     del -= 10;
                  }
               }
            }
         }
      }

   }

   static class EditHudPositionScreen extends GuiScreen {
      final String hudTextExample = new String("This is an-Example-HUD");
      GuiButtonExt resetPosButton;
      boolean mouseDown = false;
      int textBoxStartX = 0;
      int textBoxStartY = 0;
      int textBoxEndX = 0;
      int textBoxEndY = 0;
      int marginX = 5;
      int marginY = 70;
      int lastMousePosX = 0;
      int lastMousePosY = 0;
      int sessionMousePosX = 0;
      int sessionMousePosY = 0;

      public void initGui() {
         super.initGui();
         this.buttonList.add(this.resetPosButton = new GuiButtonExt(1, this.width - 90, 5, 85, 20, new String("Reset position")));
         this.marginX = HUD.hudX;
         this.marginY = HUD.hudY;
         HUD.positionMode = ru.getPostitionMode(marginX, marginY, this.width, this.height);
      }

      public void drawScreen(int mX, int mY, float pt) {
         drawRect(0, 0, this.width, this.height, -1308622848);
         drawRect(0, (int)this.height/2, (int)this.width, (int)this.height/2 + 1, 0x9936393f);
         drawRect((int)this.width/2, 0, (int)this.width/2 + 1, (int)this.height, 0x9936393f);
         int textBoxStartX = this.marginX;
         int textBoxStartY = this.marginY;
         int textBoxEndX = textBoxStartX + 50;
         int textBoxEndY = textBoxStartY + 32;
         this.drawArrayList(this.mc.fontRendererObj, this.hudTextExample);
         this.textBoxStartX = textBoxStartX;
         this.textBoxStartY = textBoxStartY;
         this.textBoxEndX = textBoxEndX;
         this.textBoxEndY = textBoxEndY;
         HUD.hudX = textBoxStartX;
         HUD.hudY = textBoxStartY;
         ScaledResolution res = new ScaledResolution(this.mc);
         int descriptionOffsetX = res.getScaledWidth() / 2 - 84;
         int descriptionOffsetY = res.getScaledHeight() / 2 - 20;
         ru.drawColouredText("Edit the HUD position by dragging.", '-', descriptionOffsetX, descriptionOffsetY, 2L, 0L, true, this.mc.fontRendererObj);

         try {
            this.handleInput();
         } catch (IOException var12) {
         }

         super.drawScreen(mX, mY, pt);
      }

      private void drawArrayList(FontRenderer fr, String t) {
         int x = this.textBoxStartX;
         int gap = this.textBoxEndX - this.textBoxStartX;
         int y = this.textBoxStartY;
         double marginY = fr.FONT_HEIGHT + 2;
         String[] var4 = t.split("-");
         ArrayList<String> var5 = ay.toArrayList(var4);
         if (HUD.positionMode == ru.PositionMode.UPLEFT || HUD.positionMode == ru.PositionMode.UPRIGHT) {
            var5.sort((o1, o2) -> ay.mc.fontRendererObj.getStringWidth(o2) - ay.mc.fontRendererObj.getStringWidth(o1));
         }
         else if(HUD.positionMode == ru.PositionMode.DOWNLEFT || HUD.positionMode == ru.PositionMode.DOWNRIGHT) {
            var5.sort((o2, o1) -> ay.mc.fontRendererObj.getStringWidth(o2) - ay.mc.fontRendererObj.getStringWidth(o1));
         }

         if(HUD.positionMode == ru.PositionMode.DOWNRIGHT || HUD.positionMode == ru.PositionMode.UPRIGHT){
            for (String s : var5) {
               fr.drawString(s, (float) x + (gap - fr.getStringWidth(s)), (float) y, Color.white.getRGB(), HUD.dropShadow.isToggled());
               y += marginY;
            }
         } else {
            for (String s : var5) {
               fr.drawString(s, (float) x, (float) y, Color.white.getRGB(), HUD.dropShadow.isToggled());
               y += marginY;
            }
         }
      }

      protected void mouseClickMove(int mousePosX, int mousePosY, int clickedMouseButton, long timeSinceLastClick) {
         super.mouseClickMove(mousePosX, mousePosY, clickedMouseButton, timeSinceLastClick);
         if (clickedMouseButton == 0) {
            if (this.mouseDown) {
               this.marginX = this.lastMousePosX + (mousePosX - this.sessionMousePosX);
               this.marginY = this.lastMousePosY + (mousePosY - this.sessionMousePosY);
               HUD.positionMode = ru.getPostitionMode(marginX, marginY, this.width, this.height);

               //in the else if statement, we check if the mouse is clicked AND inside the "text box"
            } else if (mousePosX > this.textBoxStartX && mousePosX < this.textBoxEndX && mousePosY > this.textBoxStartY && mousePosY < this.textBoxEndY) {
               this.mouseDown = true;
               this.sessionMousePosX = mousePosX;
               this.sessionMousePosY = mousePosY;
               this.lastMousePosX = this.marginX;
               this.lastMousePosY = this.marginY;
            }

         }
      }

      protected void mouseReleased(int mX, int mY, int state) {
         super.mouseReleased(mX, mY, state);
         if (state == 0) {
            this.mouseDown = false;
         }

      }

      public void actionPerformed(GuiButton b) {
         if (b == this.resetPosButton) {
            this.marginX = HUD.hudX = 5;
            this.marginY = HUD.hudY = 70;
         }

      }

      public boolean doesGuiPauseGame() {
         return false;
      }
   }

   public static enum ColourModes {
      RAVEN,
      RAVEN2,
      ASTOLFO,
      ASTOLFO2,
      ASTOLFO3,
      KOPAMED;
   }
}
