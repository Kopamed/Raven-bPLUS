package keystrokesmod.client.module.modules.client;

import keystrokesmod.client.clickgui.raven.components.CategoryComponent;
import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.RGBSetting;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.Utils;

public class GuiModule extends Module {
   public static final int bind = 54;
   public static SliderSetting backgroundOpacity;
   public static TickSetting categoryBackground, cleanUp, matchTopWBottomEnabled, matchTopWBottomDisabled, showGradientEnabled, showGradientDisabled, showTextColour;
   public static RGBSetting enabledTopRGB, enabledBottomRGB, enabledTextRGB,
   							disabledTopRGB, disabledBottomRGB, disabledTextRGB,
                            backgroundRGB, settingBackgroundRGB, categoryBackgroundRGB;

   public GuiModule() {
      super("Gui", ModuleCategory.client);
      withKeycode(54);

      this.registerSetting(enabledTopRGB = new RGBSetting("EnabledTopRGB", 0, 200, 50));
      this.registerSetting(enabledBottomRGB = new RGBSetting("EnabledBottomRGB", 0, 200, 50));
      this.registerSetting(enabledTextRGB = new RGBSetting("EnabledTextRGB", 0, 200, 50));
      
      this.registerSetting(disabledTopRGB = new RGBSetting("DisabledTopRGB", 0, 200, 50));
      this.registerSetting(disabledBottomRGB = new RGBSetting("DisabledBottomRGB", 0, 200, 50));
      this.registerSetting(disabledTextRGB = new RGBSetting("DisabledTextRGB", 0, 200, 50));

       this.registerSetting(backgroundRGB = new RGBSetting("BackgroundRGB", 0, 0, 0));
       this.registerSetting(settingBackgroundRGB = new RGBSetting("SettingBackgroundRGB", 0, 0, 0));
       this.registerSetting(categoryBackgroundRGB = new RGBSetting("CategoryBackgroundRGB", 0, 0, 0));
      
      this.registerSetting(matchTopWBottomEnabled = new TickSetting("Match Top enabled w/ bottom enabled", false));
      this.registerSetting(matchTopWBottomDisabled = new TickSetting("Match Top enabled w/ bottom disabled", false));
      
      this.registerSetting(showGradientDisabled = new TickSetting("Show gradient when disabled", true));
      this.registerSetting(showGradientEnabled = new TickSetting("Show gradient when enabled", true));
      
      this.registerSetting(backgroundOpacity = new SliderSetting("Background Opacity %", 43.0D, 0.0D, 100.0D, 1.0D));
      this.registerSetting(categoryBackground = new TickSetting("Category Background", true));
      this.registerSetting(cleanUp = new TickSetting("Clean Up", false));
   }
   
   @Override
   public void guiButtonToggled(TickSetting setting) {
	   if(setting == cleanUp) {
		   cleanUp.disable();
		   for(CategoryComponent cc : Raven.clickGui.getCategoryList()) {
			   cc.setX((cc.getX()/50*50) + (cc.getX() % 50 > 25 ? 50:0 ));
			   cc.setY((cc.getY()/50*50) + (cc.getY() % 50 > 25 ? 50:0 ));
		   }
	   } else if (setting == matchTopWBottomEnabled){
		   matchTopWBottomEnabled.disable();
		   enabledTopRGB.setColors(enabledBottomRGB.getColors());
	   } else if (setting == matchTopWBottomDisabled){
		   matchTopWBottomDisabled.disable();
		   disabledTopRGB.setColors(disabledBottomRGB.getColors());
	   }
	   
	   
   }

   public void onEnable() {
      if (Utils.Player.isPlayerInGame() && mc.currentScreen != Raven.clickGui) {
         mc.displayGuiScreen(Raven.clickGui);
            Raven.clickGui.initMain();
      }

      this.disable();
   }
}
