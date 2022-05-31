
package keystrokesmod.client.clickgui.raven;

import keystrokesmod.client.clickgui.raven.settings.BindComponent;
import keystrokesmod.client.clickgui.theme.Theme;
import keystrokesmod.client.clickgui.theme.themes.RavenB3;
import keystrokesmod.client.clickgui.theme.themes.Vape;
import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.ModuleManager;
import keystrokesmod.client.utils.Timer;
import keystrokesmod.client.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraftforge.fml.client.config.GuiButtonExt;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ClickGui extends GuiScreen {
   public int barWidth = 1;
   private ScheduledFuture<?> scheduledFuture;
   private Timer timerTopEntrance;
   private Timer timerLeftEntrance;
   private Timer timerBottomEntrance;
   private Timer timerRightEntrance;
   private ScaledResolution scaledResolution;
   private GuiButtonExt commandLineSendButton;
   private GuiTextField commandLineTestField;


   private final Theme theme = new Vape();
   private final boolean inBindingProcess = false;
   private String tooltip;
   private final String defaultTooltip;
   private final Terminal terminal;
   private FontRenderer fontRenderer;
   private Object setter;



   private final ArrayList<CategoryComponent> moduleCategories;

   public ClickGui() {
      this.moduleCategories = new ArrayList<>();
      this.tooltip = "";
      this.terminal = new Terminal();

      if(Raven.versionManager.getLatestVersion().isNewerThan(Raven.versionManager.getClientVersion())){
         this.defaultTooltip = "Raven B+ v" + Raven.versionManager.getClientVersion().toString() + " is outdated! Please update to the latest version " + Raven.versionManager.getLatestVersion().toString();
      } else {
         this.defaultTooltip = "Raven B+ v" + Raven.versionManager.getClientVersion().toString();
      }

      for(Module.ModuleCategory moduleCategory : Module.ModuleCategory.values()){
         CategoryComponent categoryComponent = new CategoryComponent(moduleCategory);
         categoryComponent.setDraggable(true);
         moduleCategories.add(categoryComponent);
      }

      int topOffset = 5;
      Module.ModuleCategory[] values;
      int categoryAmount = (values = Module.ModuleCategory.values()).length;

      for(int category = 0; category < categoryAmount; ++category) {
         Module.ModuleCategory moduleCategory = values[category];
         CategoryComponent currentModuleCategory = new CategoryComponent(moduleCategory);

         moduleCategories.add(currentModuleCategory);
      }
   }

   public void firstRun() {
      this.scaledResolution = new ScaledResolution(Minecraft.getMinecraft());

      int categoryNumber = Module.ModuleCategory.values().length;
      double marginX = scaledResolution.getScaledWidth() * 0.01;
      double marginY = scaledResolution.getScaledHeight() * 0.01;
      double totalMarginSpace = (categoryNumber + 1) * marginX;
      double catWidth = (width - totalMarginSpace) / categoryNumber;
      double catHeight = catWidth / 6;
      double currentX = marginX;

      for(CategoryComponent categoryComponent : moduleCategories){
         categoryComponent.setLocation(currentX, marginY);
         categoryComponent.setSize(92, 13);
         categoryComponent.onResize();
         categoryComponent.setOpened(true);
         currentX += catWidth + marginX;
      }
   }

   public void initMain() {
      this.fontRenderer = mc.fontRendererObj;
      (this.timerTopEntrance = this.timerBottomEntrance = this.timerRightEntrance = new Timer(500.0F)).start();
      this.scheduledFuture = Raven.getExecutor().schedule(() -> (
              this.timerLeftEntrance = new Timer(650.0F)
      ).start(), 650L, TimeUnit.MILLISECONDS);

   }

   public void initGui() {
      super.initGui();
   }

   public void drawScreen(int x, int y, float p) {
      ScaledResolution sr = new ScaledResolution(mc);
      double width = sr.getScaledWidth();
      double height = sr.getScaledHeight();

      double marginX = width * 0.01;
      double marginY = height * 0.01;

      double desiredTextSize = height * 0.024;
      double scaleFactor = desiredTextSize / fontRenderer.FONT_HEIGHT;
      double coordFactor = 1/scaleFactor;

      double desiredTooltipSize = desiredTextSize * 0.5;
      double tooltipScaleFactor = desiredTooltipSize / fontRenderer.FONT_HEIGHT;
      double tooltipCoordFactor = 1/tooltipScaleFactor;

      double barHeight = desiredTextSize * 1.6;
      double barTextY = (height - barHeight + (barHeight - desiredTextSize) / 2);
      double dateX = (width - (fontRenderer.getStringWidth(Utils.Java.getDate()) * scaleFactor + marginX));
      double tooltipX = 0;
      double tooltipSize = fontRenderer.getStringWidth(tooltip.isEmpty() ? defaultTooltip : tooltip) * (tooltip.isEmpty() ? scaleFactor : tooltipScaleFactor);
      tooltipX = (width - tooltipSize) / 2;


      double entitySize = width * 0.05;
      double entityX = width - marginX - entitySize;
      double entityY = height - barHeight - 1 - marginY - entitySize;

      drawRect(0, 0, this.width, this.height, theme.getBackdropColour().getRGB());

      int quarterScreenHeight = this.height / 4;
      int halfScreenWidth = this.width / 2;
      int w_c = 30 - this.timerTopEntrance.getValueInt(0, 30, 3);
      this.drawCenteredString(this.fontRendererObj, "r", halfScreenWidth + 1 - w_c, quarterScreenHeight - 25, Utils.Client.rainbowDraw(2L, 1500L));
      this.drawCenteredString(this.fontRendererObj, "a", halfScreenWidth - w_c, quarterScreenHeight - 15, Utils.Client.rainbowDraw(2L, 1200L));
      this.drawCenteredString(this.fontRendererObj, "v", halfScreenWidth - w_c, quarterScreenHeight - 5, Utils.Client.rainbowDraw(2L, 900L));
      this.drawCenteredString(this.fontRendererObj, "e", halfScreenWidth - w_c, quarterScreenHeight + 5, Utils.Client.rainbowDraw(2L, 600L));
      this.drawCenteredString(this.fontRendererObj, "n", halfScreenWidth - w_c, quarterScreenHeight + 15, Utils.Client.rainbowDraw(2L, 300L));
      this.drawCenteredString(this.fontRendererObj, "b+", halfScreenWidth + 1 + w_c, quarterScreenHeight + 30, Utils.Client.rainbowDraw(2L, 0L));

      fontRenderer.drawString(tooltip.isEmpty() ? defaultTooltip : tooltip, (float) (tooltipX * coordFactor), (float)(barTextY * coordFactor), theme.getTextColour().getRGB(), false);


      this.drawVerticalLine(halfScreenWidth - 10 - w_c, quarterScreenHeight - 30, quarterScreenHeight + 43, Color.white.getRGB());
      this.drawVerticalLine(halfScreenWidth + 10 + w_c, quarterScreenHeight - 30, quarterScreenHeight + 43, Color.white.getRGB());
      int r;
      if (this.timerLeftEntrance != null) {
         r = this.timerLeftEntrance.getValueInt(0, 20, 2);
         this.drawHorizontalLine(halfScreenWidth - 10, halfScreenWidth - 10 + r, quarterScreenHeight - 29, -1);
         this.drawHorizontalLine(halfScreenWidth + 10, halfScreenWidth + 10 - r, quarterScreenHeight + 42, -1);
      }

      for (CategoryComponent category : moduleCategories) {
         category.update(x, y);
         category.paint(fontRenderer);
      }


      // PLAYER
      GuiInventory.drawEntityOnScreen(this.width + 15 - this.timerBottomEntrance.getValueInt(0, 40, 2), this.height - 19 - this.fontRendererObj.FONT_HEIGHT, 40, (float)(this.width - 25 - x), (float)(this.height - 50), this.mc.thePlayer);
   }

   public void mouseClicked(int x, int y, int mouseButton) throws IOException {
      int categoryNumber = moduleCategories.size();

      terminal.mouseDown(x, y, mouseButton);

      for(int i = 0; i < categoryNumber; i++){
         CategoryComponent categoryComponent = moduleCategories.get(i);
         categoryComponent.mouseDown(x, y, mouseButton);
      }
   }

   public void mouseReleased(int x, int y, int s) {
      int categoryNumber = moduleCategories.size();

      terminal.mouseReleased(x, y, s);

      for(int i = 0; i < categoryNumber; i++){
         CategoryComponent categoryComponent = moduleCategories.get(i);
         categoryComponent.mouseReleased(x, y, s);
      }
   }

   public void keyTyped(char typedChar, int keyCode) {
      if(!binding()){
         try {
            super.keyTyped(typedChar, keyCode);
         } catch (IOException e) {
            e.printStackTrace();
         }
      }

      for(CategoryComponent categoryComponent : moduleCategories){
         categoryComponent.keyTyped(typedChar, keyCode);
      }
   }

   public void onGuiClosed() {
      this.timerLeftEntrance = null;
      if (this.scheduledFuture != null) {
         this.scheduledFuture.cancel(true);
         this.scheduledFuture = null;
      }

      for(Module module : ModuleManager.getModules()){
         module.onGuiClose();
      }

      Raven.configManager.save();
   }

   public boolean doesGuiPauseGame()    {
      return false;
   }

   public ArrayList<CategoryComponent> getCategoryList() {
      return moduleCategories;
   }

   public Theme getTheme() {
      return theme;
   }

   public void setTooltip(String tooltip, Object setter) {
      this.tooltip = tooltip;
      this.setter = setter;
   }

   public Object getTooltipSetter() {
      return setter;
   }

   public String getTooltip(){
      return tooltip;
   }

   public void clearTooltip(){
      this.tooltip = "";
   }

   private boolean binding() {
      for (CategoryComponent categoryComponent : moduleCategories) {
         for (Component module : categoryComponent.getComponents()) {
            for (Component setting : module.getComponents()) {
               if (setting instanceof BindComponent) {
                  if (((BindComponent) setting).isListening())
                     return true;
               }
            }
         }
      }
      return false;
   }
}
