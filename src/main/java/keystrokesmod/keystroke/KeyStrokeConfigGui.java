
package keystrokesmod.keystroke;

import keystrokesmod.main.ClientConfig;
import keystrokesmod.main.Ravenbplus;
import keystrokesmod.module.ModuleManager;
import keystrokesmod.module.modules.client.GuiModule;
import keystrokesmod.utils.mouseManager;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

public class KeyStrokeConfigGui extends GuiScreen {
   private static KeySrokeRenderer keySrokeRenderer;

   private static final String[] colors = new String[]{"White", "Red", "Green", "Blue", "Yellow", "Purple", "Rainbow"};
   private GuiButton modeBtn;
   private GuiButton textColorBtn;
   private GuiButton showMouseBtn;
   private GuiButton outlineBtn;
   private GuiButton clickGui;
   private boolean d = false;
   private int lx;
   private int ly;

   public KeyStrokeConfigGui() {
      KeyStrokeConfigGui.keySrokeRenderer = new KeySrokeRenderer();
   }

   public void initGui() {
      this.buttonList.add(this.modeBtn = new GuiButton(0, this.width / 2 - 70, this.height / 2 - 28, 140, 20, "Mod: " + (KeyStroke.mode ? "Enabled" : "Disabled")));
      this.buttonList.add(this.textColorBtn = new GuiButton(1, this.width / 2 - 70, this.height / 2 - 6, 140, 20, "Text color: " + colors[KeyStroke.currentColorNumber]));
      this.buttonList.add(this.showMouseBtn = new GuiButton(2, this.width / 2 - 70, this.height / 2 + 16, 140, 20, "Show mouse buttons: " + (KeyStroke.showMouseBtn ? "On" : "Off")));
      this.buttonList.add(this.outlineBtn = new GuiButton(3, this.width / 2 - 70, this.height / 2 + 38, 140, 20, "Outline: " + (KeyStroke.outline ? "On" : "Off")));
      GuiModule gui = (GuiModule) ModuleManager.getModuleByClazz(GuiModule.class);
      if (gui != null && gui.getKeycode() != 54) {
         this.buttonList.add(this.clickGui = new GuiButton(3, this.width / 2 - 70, this.height / 2 + 60, 140, 20, "ClickGui"));
      }
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      keySrokeRenderer.renderKeystrokes();
      super.drawScreen(mouseX, mouseY, partialTicks);
   }

   protected void actionPerformed(GuiButton button) {
      if (button == this.modeBtn) {
         KeyStroke.mode = !KeyStroke.mode;
         this.modeBtn.displayString = "Mod: " + (KeyStroke.mode ? "Enabled" : "Disabled");
      } else if (button == this.textColorBtn) {
         KeyStroke.currentColorNumber = KeyStroke.currentColorNumber == 6 ? 0 : KeyStroke.currentColorNumber + 1;
         this.textColorBtn.displayString = "Text color: " + colors[KeyStroke.currentColorNumber];
      } else if (button == this.showMouseBtn) {
         KeyStroke.showMouseBtn = !KeyStroke.showMouseBtn;
         this.showMouseBtn.displayString = "Show mouse buttons: " + (KeyStroke.showMouseBtn ? "On" : "Off");
      } else if (button == this.outlineBtn) {
         KeyStroke.outline = !KeyStroke.outline;
         this.outlineBtn.displayString = "Outline: " + (KeyStroke.outline ? "On" : "Off");
      } else if(button == this.clickGui){
         mc.currentScreen = null;
         mc.displayGuiScreen(Ravenbplus.clickGui);
         Ravenbplus.clickGui.initMain();
      }


   }

   protected void mouseClicked(int mouseX, int mouseY, int button) {
      try {
         super.mouseClicked(mouseX, mouseY, button);
      } catch (IOException ignored) {}

      if (button == 0) {
         mouseManager.addLeftClick();
         int startX = KeyStroke.x;
         int startY = KeyStroke.y;
         int endX = startX + 74;
         int endY = startY + (KeyStroke.showMouseBtn ? 74 : 50);
         if (mouseX >= startX && mouseX <= endX && mouseY >= startY && mouseY <= endY) {
            this.d = true;
            this.lx = mouseX;
            this.ly = mouseY;
         }
      } else if (button == 1) {
         mouseManager.addRightClick();
      }

   }

   protected void mouseReleased(int mouseX, int mouseY, int action) {
      super.mouseReleased(mouseX, mouseY, action);
      this.d = false;
   }

   protected void mouseClickMove(int mouseX, int mouseY, int lastButtonClicked, long timeSinceMouseClick) {
      super.mouseClickMove(mouseX, mouseY, lastButtonClicked, timeSinceMouseClick);
      if (this.d) {
         KeyStroke.x = KeyStroke.x + mouseX - this.lx;
         KeyStroke.y = KeyStroke.y + mouseY - this.ly;
         this.lx = mouseX;
         this.ly = mouseY;
      }
   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   public void onGuiClosed() {
      Ravenbplus.clientConfig.saveConfig();
      ClientConfig.saveKeyStrokeSettingsToConfigFile();
   }
}
