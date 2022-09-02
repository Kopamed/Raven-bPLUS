package keystrokesmod.client.module.modules.client;

import java.awt.Color;

import keystrokesmod.client.clickgui.raven.components.CategoryComponent;
import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.ComboSetting;
import keystrokesmod.client.module.setting.impl.RGBSetting;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.Utils;

public class GuiModule extends Module {
	public static final int bind = 54;
	public static SliderSetting backgroundOpacity;

	public static ComboSetting preset;

	public static TickSetting categoryBackground, cleanUp, reset, usePreset,

	matchTopWBottomEnabled, matchTopWBottomDisabled,
	showGradientEnabled, showGradientDisabled,
	useCustomFont;

	public static RGBSetting enabledTopRGB, enabledBottomRGB, enabledTextRGB,
	disabledTopRGB, disabledBottomRGB, disabledTextRGB, backgroundRGB, settingBackgroundRGB, categoryBackgroundRGB;

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
        this.registerSetting(useCustomFont = new TickSetting("Smooth Font (Very Bad)", false));
        this.registerSetting(cleanUp = new TickSetting("Clean Up", false));
        this.registerSetting(reset = new TickSetting("Reset position", false));
        this.registerSetting(usePreset = new TickSetting("Use preset", false));
        this.registerSetting(preset = new ComboSetting("Preset", Preset.IcyBlue));
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
	   } else if(setting == reset) {
           reset.disable();
           Raven.clickGui.resetSort();
       }
	   
	   
   }

	public void onEnable() {
		if (Utils.Player.isPlayerInGame() && mc.currentScreen != Raven.clickGui) {
			mc.displayGuiScreen(Raven.clickGui);
			Raven.clickGui.initMain();
		}

		this.disable();
	}



	public enum Preset {
		IcyBlue(
				true, false, false,
				new Color(0,89,218),
				new Color(151,236,245),
				new Color(255,245,224),
				new Color(10,0,36),
				new Color(0,0,160),
				new Color(3,200,218)
				),
		FieryRed(
				true, false, false,
				new Color(255,209,0),
				new Color(255,0,0),
				new Color(36,255,255),
				new Color(0,200,50),
				new Color(0,200,50),
				new Color(255,255,255)
				),
		Seaweed(
				false, false, false,
				new Color(0,0,0),
				new Color(0,0,0),
				new Color(85,186,131),
				new Color(0,0,0),
				new Color(0,0,0),
				new Color(55,66,58)
				),
		Vape(
				true, true, false,
				new Color(59,132,107),
				new Color(59,132,107),
				new Color(255,255,255),
				new Color(27,25,26),
				new Color(27,25,26),
				new Color(255,255,255)
				);



		public boolean showGradientEnabled, showGradientDisabled, useCustomFont;
		public Color enabledTopRGB, enabledBottomRGB, enabledTextRGB, disabledTopRGB, disabledBottomRGB, disabledTextRGB;


		private Preset(
				boolean showGradientEnabled, boolean showGradientDisabled, boolean useCustomFont,
				Color enabledTopRGB, Color enabledBottomRGB, Color enabledTextRGB, Color disabledTopRGB, Color disabledBottomRGB, Color disabledTextRGB
				) {
			this.showGradientEnabled = showGradientEnabled;
			this.showGradientDisabled = showGradientDisabled;
			this.useCustomFont = useCustomFont;
			this.enabledTopRGB = enabledTopRGB;
			this.enabledBottomRGB = enabledBottomRGB;
			this.enabledTextRGB = enabledTextRGB;
			this.disabledTopRGB = disabledTopRGB;
			this.disabledBottomRGB = disabledBottomRGB;
			this.disabledTextRGB = disabledTextRGB;
		}
	}
}
