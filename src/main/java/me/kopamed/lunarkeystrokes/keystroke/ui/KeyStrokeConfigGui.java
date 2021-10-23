//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package me.kopamed.lunarkeystrokes.keystroke.ui;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.kopamed.lunarkeystrokes.keystroke.KeyStroke;
import me.kopamed.lunarkeystrokes.keystroke.setting.settings.ColourSetting;
import me.kopamed.lunarkeystrokes.keystroke.setting.settings.SliderSetting;
import me.kopamed.lunarkeystrokes.keystroke.setting.settings.TickSetting;
import me.kopamed.lunarkeystrokes.keystroke.ui.component.Component;
import me.kopamed.lunarkeystrokes.keystroke.ui.component.components.Colour;
import me.kopamed.lunarkeystrokes.keystroke.ui.component.components.Slider;
import me.kopamed.lunarkeystrokes.keystroke.ui.component.components.Tick;
import me.kopamed.lunarkeystrokes.utils.MouseManager;
import me.kopamed.lunarkeystrokes.main.ClientConfig;
import me.kopamed.lunarkeystrokes.main.Ravenbplus;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;

public class KeyStrokeConfigGui extends GuiScreen {
   // All the gui components needed
   // settings
   private ColourSetting textColour, textColourPressed, backgroundColour, backgroundColourPressed;
   private SliderSetting scale, boxSize, transparency;
   private TickSetting showMouseButtons, showRMBCPS, showSpaceBar, textShadow, showLMBCPS, showMovementKeys, replaceNamesWithArrows;

   private int lastX;
   private int lastY;

   private boolean mouseDown;

   private boolean opened = false;
   private int mainWidth, barHeight = 24, barX, barY;
   private int mainHeight = 144;
   private int generalMargin = 4;
   private int crossSize = barHeight - generalMargin*2;
   private double barTextScale;
   private double barTextCoords;

   private int lastGuiX, lastGuiY, lastGuiWidth;

   private final int barColour = 0xcd0F0D0D, barTextColour = 0xfff9f9f9, barFrameColour = 0xcd0D0D0D;
   private final int mainColour = 0xcd111010;
   private final int crossBgColour = 0xcd474646;
   private KeyStroke ks;

   private List<Component> settings = new ArrayList<>();
   private MoveType moveType;
   private int componentHeight;

   public static Component helpingComponent = null;

   private final double textSize = 0.5D;
   private int fullWidth, halfWidth;
   private SliderSetting transition;
   private TickSetting chromaText;
   private TickSetting chromaBg;

   public KeyStrokeConfigGui(){
      this.opened = false; // i am retarded

      this.componentHeight = 16; // pixel height of the components
   }

   public void initGui() { // called whenever the gui is started by minecraft
      // i now have to set the settings to the values i just defined in KeyStroke class

      this.settings.clear();

      ks = Ravenbplus.getKeyStroke();

      this.mainWidth = (int)(this.width * 0.4);
      this.barX = (this.width - this.mainWidth)/2;
      this.barY = (int)(this.height/2 - this.barHeight * 1.5 - generalMargin - mainHeight / 2);
      this.barTextScale = (barHeight - 2D*generalMargin) / fontRendererObj.FONT_HEIGHT;
      this.barTextCoords = 1D / this.barTextScale;
      this.crossSize = (int)(barTextScale * fontRendererObj.getStringWidth("+"));

      // main gui components
      ///  i creat he colour byllshit ten i add them to lisytt then i remnder them and  gui close i set them ti thw ks YESSS
      this.textColour = new ColourSetting("Text Color", ks.textColour); //init of textcolur setting. we have not "added" it to the main display yet
      this.textColourPressed = new ColourSetting("Text Pressed Color", ks.textColourPressed);
      this.backgroundColour =  new ColourSetting("Background Color", ks.backgroundColour);
      this.backgroundColourPressed =  new ColourSetting("Background Pressed Color", ks.backgroundColourPressed);

      // i am so retarded i cant vhoose wich way to cde this thing

      // size components
      this.scale = new SliderSetting("Scale", ks.scale, 0.5D, 2D); // yesah the min is 0.01 or smthing i think
      this.boxSize = new SliderSetting("Box Size", ks.boxSize, 10D, 50D); // I have to check the values for this. I don't think you can set the scale to 0.01 in lunar....
      this.transparency = new SliderSetting("Transparency", ks.transparency, 1D, 255D);
      this.transition = new SliderSetting("Transition Time", ks.transition, 1, 500);

      // misc components
      this.showMouseButtons = new TickSetting("Show Mouse Buttons", ks.showMouseButtons);
      this.textShadow = new TickSetting("Text Shadow", ks.textShadow);
      this.showRMBCPS =  new TickSetting("Show RMB CPS", ks.showRMBCPS);
      this.showLMBCPS =  new TickSetting("Show LMB CPS", ks.showLMBCPS);
      this.showSpaceBar = new TickSetting("Show Space Bar", ks.showSpaceBar);
      this.showMovementKeys = new TickSetting("Show Movement Keys", ks.showMovementKeys);
      this.replaceNamesWithArrows = new TickSetting("Arrows For Movement Keys", ks.replaceNamesWithArrows); // ▲ ◀ ▼ ▶
      this.chromaText = new TickSetting("Chroma Text", ks.chromaText);
      this.chromaBg = new TickSetting("Chroma Background", ks.chromaBg);

      // adding settings
      // I need them to look like this
      //  |---------|
      //  |---------|
      //  |---------|
      //  |---------|
      //  |---| |---|
      //  []    []
      //  []    []
      //  []    []
      //  []

      int x = barX + generalMargin, y = barY + barHeight + generalMargin*2;// defining the x and y starting points
      int startHalfX =  barX + (mainWidth - generalMargin*3)/2 + generalMargin*2; // halfway through the gui (counting the margins)
      int componentY = barY + barHeight + generalMargin*3;
      this.fullWidth = mainWidth - generalMargin*2;
      this.halfWidth = (mainWidth - generalMargin*3) /2;

      // some bullshit i came up with idk
      // i dont waana define a new setting ever render call, so i gotta predefine them and put them in an array...
      this.settings.add(new Colour(x, componentY, fullWidth, componentHeight, this.textColour, textSize));
      componentY += componentHeight + generalMargin;
      this.settings.add(new Colour(x, componentY, fullWidth, componentHeight, this.textColourPressed, textSize));
      componentY += componentHeight + generalMargin;
      this.settings.add(new Colour(x, componentY, fullWidth, componentHeight, this.backgroundColour, textSize));
      componentY += componentHeight + generalMargin;
      this.settings.add(new Colour(x, componentY, fullWidth, componentHeight, this.backgroundColourPressed, textSize));
      componentY += componentHeight + generalMargin;
      this.settings.add(new Slider(x, componentY, halfWidth, componentHeight, this.scale, textSize));
      this.settings.add(new Slider(startHalfX, componentY, halfWidth, componentHeight, this.boxSize, textSize));
      componentY += componentHeight + generalMargin;
      this.settings.add(new Tick(x, componentY, halfWidth, componentHeight, this.showMouseButtons, textSize));
      this.settings.add(new Tick(startHalfX, componentY, halfWidth, componentHeight, this.textShadow, textSize));
      componentY += componentHeight + generalMargin;
      this.settings.add(new Tick(x, componentY, halfWidth, componentHeight, this.showRMBCPS, textSize));
      this.settings.add(new Tick(startHalfX, componentY, halfWidth, componentHeight, this.showLMBCPS, textSize));
      componentY += componentHeight + generalMargin;
      this.settings.add(new Tick(x, componentY, halfWidth, componentHeight, this.showSpaceBar, textSize));
      this.settings.add(new Tick(startHalfX, componentY, halfWidth, componentHeight, this.showMovementKeys, textSize));
      componentY += componentHeight + generalMargin;
      this.settings.add(new Tick(x, componentY, halfWidth, componentHeight, this.replaceNamesWithArrows, textSize));
      this.settings.add(new Slider(startHalfX, componentY, halfWidth, componentHeight, this.transparency, textSize));
      componentY += componentHeight + generalMargin;
      this.settings.add(new Tick(x, componentY, halfWidth, componentHeight, this.chromaText, textSize));
      this.settings.add(new Slider(startHalfX, componentY, halfWidth, componentHeight, this.transition, textSize));
      componentY += componentHeight + generalMargin;
      this.settings.add(new Tick(x, componentY, halfWidth, componentHeight, this.chromaBg, textSize));


      // definig starting gui x and y to offset the settings by the guis movements
      this.lastGuiX = barX;
      this.lastGuiY = barY;
      this.lastGuiWidth = mainWidth;
      this.mainHeight = componentY - (barY + barHeight)  + generalMargin;
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      Ravenbplus.getKeyStrokeRenderer().renderKeystrokes();

      net.minecraft.client.gui.Gui.drawRect(barX, barY, barX + mainWidth, barHeight + barY, barColour); // draw the main bar
      fontRendererObj.drawString("Lunar Keystrokes by Kopamed", (barX + generalMargin), (barY + generalMargin + (barHeight - 2*generalMargin - fontRendererObj.FONT_HEIGHT)/2), this.barTextColour);

      GL11.glPushMatrix();
      GL11.glScaled(barTextScale, barTextScale, barTextScale);
      fontRendererObj.drawString(this.opened ? "-": "+", (int)((barX + mainWidth - generalMargin - crossSize) * barTextCoords), (int)((barY + generalMargin + (barHeight - 2*generalMargin - fontRendererObj.FONT_HEIGHT) - generalMargin) * barTextCoords), this.barTextColour);
      GL11.glPopMatrix();

      if(!this.opened)
         return;

      net.minecraft.client.gui.Gui.drawRect(barX, barY + barHeight + generalMargin*2, barX + mainWidth, barHeight + barY + generalMargin*2 + mainHeight, mainColour);
      drawVerticalLine(barX, barY + barHeight + generalMargin*2, barHeight + barY + generalMargin*2 + mainHeight, barFrameColour);
      drawVerticalLine(barX + mainWidth, barY + barHeight + generalMargin*2, barHeight + barY + generalMargin*2 + mainHeight, barFrameColour);
      drawHorizontalLine(barX, barX + mainWidth, barY + barHeight + generalMargin*2, barFrameColour);
      drawHorizontalLine(barX, barX + mainWidth, barY + barHeight + generalMargin*2 + mainHeight, barFrameColour);
      for(Component component : settings){
         component.backgroundProcess(mouseX, mouseY);
      }

      int xOffset = barX - this.lastGuiX, yOffset = barY - this.lastGuiY; /// checking if the gui has been moved

      if(xOffset != 0 || yOffset != 0){
         this.lastGuiX = barX;
         this.lastGuiY = barY;
         for(Component component : this.settings){
            component.setX(component.getX() + xOffset);
            component.setY(component.getY() + yOffset);
         }
      }

      if(mainWidth != this.lastGuiWidth){ // resizing the settings if the gui size has been changed
         this.lastGuiWidth = mainWidth;
         int fullWidth = mainWidth - generalMargin*2;
         int halfWidth = (mainWidth - generalMargin*3)/2;

         //I need the old full height and width vals
         for(Component component : this.settings){
            if(component.getWidth() == this.halfWidth){
               component.setWidth(halfWidth);
            } else {
               component.setWidth(fullWidth);
            }
         }

         this.fullWidth = fullWidth;
         this.halfWidth = halfWidth;
      }


      for(Component component : this.settings){ // drawing all the settings
         component.draw();
      }


      setKsSettings();
      super.drawScreen(mouseX, mouseY, partialTicks);
   }

   private void setKsSettings() {
      // setting the rgb values of the keystrokes
      ks.textColour = this.textColour.getRGB();
      ks.textColourPressed = this.textColourPressed.getRGB();
      ks.backgroundColour =  colorTrans(this.backgroundColour.getRGB(), (int)(this.transparency.getValue()));
      ks.backgroundColourPressed =  colorTrans(this.backgroundColourPressed.getRGB(), (int)(this.transparency.getValue()));

      // settings the double values of the keystrokes
      ks.scale = this.scale.getValue();
      ks.boxSize = (int)this.boxSize.getValue();
      ks.transparency = this.transparency.getValue();
      ks.transition = this.transition.getValue();

      // setting boolean values of the ks
      ks.showMouseButtons = this.showMouseButtons.isTicked();
      ks.textShadow = this.textShadow.isTicked();
      ks.showRMBCPS =  this.showRMBCPS.isTicked();
      ks.showLMBCPS = this.showLMBCPS.isTicked();
      ks.showSpaceBar = this.showSpaceBar.isTicked();
      ks.showMovementKeys = this.showMovementKeys.isTicked();
      ks.replaceNamesWithArrows = this.replaceNamesWithArrows.isTicked();
      ks.chromaBg = this.chromaBg.isTicked();
      ks.chromaText = this.chromaText.isTicked();
   }

   private Color colorTrans(Color c, int trans){
      return new Color(c.getRed(), c.getGreen(), c.getBlue(), trans);
   }

   protected void mouseClicked(int mouseX, int mouseY, int button) {
      for(Component component : settings){
         component.setMouseDown(true);
      }
      try {
         super.mouseClicked(mouseX, mouseY, button);
      } catch (IOException var9) {
      }

      if (button == 0) {
         MouseManager.addLeftClick();
         KeyStroke st = Ravenbplus.getKeyStroke();
         int startX = KeyStroke.x;
         int startY = KeyStroke.y;
         int endX = startX + 74;
         int endY = startY + 74 /*(KeyStroke.d ? 74 : 50)*/;
         if (mouseX >= startX && mouseX <= endX && mouseY >= startY && mouseY <= endY) {
            this.mouseDown = true;
            this.lastX = mouseX;
            this.lastY = mouseY;
            this.moveType = MoveType.KEYSTROKES;
         }

         if(overOpenButton(mouseX, mouseY)){

            this.opened = !this.opened;
         }else if(overBar(mouseX, mouseY)){
            this.lastX = mouseX;
            this.lastY = mouseY;
            this.mouseDown = true;
            this.moveType = MoveType.BAR;
         }
      } else if (button == 1) {
         MouseManager.addRightClick();
      }

   }

   private boolean overBar(int mouseX, int mouseY) {
      if(mouseX >= barX && mouseX <= barX + mainWidth && mouseY >= barY && mouseY <= (barY + barHeight))
         return true;
      return false;
   }

   private boolean overOpenButton(int mouseX, int mouseY){
      if(mouseX >= (barX + mainWidth - generalMargin - crossSize) && mouseX <= (barX + mainWidth - generalMargin) && mouseY >= (barY + generalMargin) && mouseY <= (barY + generalMargin + crossSize))
         return true;
      return false;
   }

   protected void mouseReleased(int mouseX, int mouseY, int action) {
      super.mouseReleased(mouseX, mouseY, action);
      helpingComponent = null;
      for(Component component : settings){
         component.setMouseDown(false);
      }
      this.mouseDown = false;
   }

   protected void mouseClickMove(int mouseX, int mouseY, int lastButtonClicked, long timeSinceMouseClick) {
      super.mouseClickMove(mouseX, mouseY, lastButtonClicked, timeSinceMouseClick);

      if(this.mouseDown){
         switch (this.moveType){ // moving the gui
            case BAR:
               this.barX = barX + mouseX - this.lastX;
               this.barY = barY + mouseY - this.lastY;
               this.lastX = mouseX;
               this.lastY = mouseY;
         }
      }

      /*
      if (this.mouseDown) {
         KeyStroke st = Ravenbplus.getKeyStroke();
         KeyStroke.x = KeyStroke.x + mouseX - this.lastX;
         KeyStroke.y = KeyStroke.y + mouseY - this.lastY;
         this.lastX = mouseX;
         this.lastY = mouseY;
      }*/
   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   public void onGuiClosed() {
      Ravenbplus.clientConfig.saveConfig();
      ClientConfig.saveKeyStrokeSettingsToConfigFile();
   }

   public static enum MoveType{
      BAR,
      KEYSTROKES;
   }
}
