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

    private static SliderSetting backgroundOpacity;

    private static ComboSetting preset, cnColor;

    private static TickSetting categoryBackground, cleanUp, reset, usePreset, rainbowNotification, notifications, betagui,

    matchTopWBottomEnabled, matchTopWBottomDisabled, showGradientEnabled, showGradientDisabled, useCustomFont;

    private static RGBSetting enabledTopRGB, enabledBottomRGB, enabledTextRGB, disabledTopRGB, disabledBottomRGB,
    disabledTextRGB, backgroundRGB, settingBackgroundRGB, categoryBackgroundRGB, categoryNameRGB;

    public GuiModule() {
        super("Gui", ModuleCategory.client);
        withKeycode(54);

        if(Raven.debugger)
			this.registerSetting(betagui = new TickSetting("beta gui (VERY BETA)", false));   
        this.registerSetting(enabledTopRGB = new RGBSetting("EnabledTopRGB", 0, 200, 50));
        this.registerSetting(enabledBottomRGB = new RGBSetting("EnabledBottomRGB", 0, 200, 50));
        this.registerSetting(enabledTextRGB = new RGBSetting("EnabledTextRGB", 0, 200, 50));

        this.registerSetting(disabledTopRGB = new RGBSetting("DisabledTopRGB", 0, 200, 50));
        this.registerSetting(disabledBottomRGB = new RGBSetting("DisabledBottomRGB", 0, 200, 50));
        this.registerSetting(disabledTextRGB = new RGBSetting("DisabledTextRGB", 0, 200, 50));

        this.registerSetting(backgroundRGB = new RGBSetting("BackgroundRGB", 0, 0, 0));
        this.registerSetting(settingBackgroundRGB = new RGBSetting("SettingBackgroundRGB", 0, 0, 0));
        this.registerSetting(categoryBackgroundRGB = new RGBSetting("CategoryBackgroundRGB", 0, 0, 0));

        this.registerSetting(cnColor = new ComboSetting("Category Name Color", CNColor.STATIC));
        this.registerSetting(categoryNameRGB = new RGBSetting("CategoryNameRGB", 255, 255, 255));

        this.registerSetting(matchTopWBottomEnabled = new TickSetting("Match Top enabled w/ bottom enabled", false));
        this.registerSetting(matchTopWBottomDisabled = new TickSetting("Match Top enabled w/ bottom disabled", false));

        this.registerSetting(showGradientDisabled = new TickSetting("Show gradient when disabled", true));
        this.registerSetting(showGradientEnabled = new TickSetting("Show gradient when enabled", true));

        this.registerSetting(backgroundOpacity = new SliderSetting("Background Opacity %", 43.0D, 0.0D, 100.0D, 1.0D));
        this.registerSetting(categoryBackground = new TickSetting("Category Background", true));
        this.registerSetting(useCustomFont = new TickSetting("Smooth Font (BROKEN DONT USE)", false));
        this.registerSetting(cleanUp = new TickSetting("Clean Up", false));
        this.registerSetting(notifications = new TickSetting("Notifications", true));
        this.registerSetting(rainbowNotification = new TickSetting("Rainbow Notifications", true));
        this.registerSetting(reset = new TickSetting("Reset position", false));
        this.registerSetting(usePreset = new TickSetting("Use preset", true));
        this.registerSetting(preset = new ComboSetting("Preset", Preset.Vape));
    }

    @Override
    public void guiButtonToggled(TickSetting setting) {
        if (setting == cleanUp) {
            cleanUp.disable();
            for (CategoryComponent cc : Raven.clickGui.getCategoryList()) {
                cc.setX(((cc.getX() / 50) * 50) + ((cc.getX() % 50) > 25 ? 50 : 0));
                cc.setY(((cc.getY() / 50) * 50) + ((cc.getY() % 50) > 25 ? 50 : 0));
            }
        } else if (setting == matchTopWBottomEnabled) {
            matchTopWBottomEnabled.disable();
            enabledTopRGB.setColors(enabledBottomRGB.getColors());
        } else if (setting == matchTopWBottomDisabled) {
            matchTopWBottomDisabled.disable();
            disabledTopRGB.setColors(disabledBottomRGB.getColors());
        } else if (setting == reset) {
            reset.disable();
            Raven.clickGui.resetSort();
        }

    }

    @Override
	public void onEnable() {
        if (Utils.Player.isPlayerInGame() && ((mc.currentScreen != Raven.clickGui) || (mc.currentScreen != Raven.kvCompactGui)))
			if(Raven.debugger) {
                mc.displayGuiScreen(Raven.kvCompactGui);
                Raven.kvCompactGui.initGui();
            } else {
                mc.displayGuiScreen(Raven.clickGui);
                Raven.clickGui.initMain();
            }

        this.disable();
    }

    private static Preset getPresetMode() {
        return (Preset) preset.getMode();
    }

    public static int getBackgroundOpacity() {
        return usePreset.isToggled() ? getPresetMode().backgroundOpacity : (int) (backgroundOpacity.getInput() * 2.55);
    }

    public static boolean isCategoryBackgroundToggled() {
        return usePreset.isToggled() ? getPresetMode().categoryBackground : categoryBackground.isToggled();
    }

    public static boolean showGradientEnabled() {
        return usePreset.isToggled() ? getPresetMode().showGradientEnabled : showGradientEnabled.isToggled();
    }

    public static boolean showGradientDisabled() {
        return usePreset.isToggled() ? getPresetMode().showGradientDisabled : showGradientDisabled.isToggled();
    }

    public static boolean useCustomFont() {
        return usePreset.isToggled() ? getPresetMode().useCustomFont : useCustomFont.isToggled();
    }

    public static int getEnabledTopRGB() {
        return usePreset.isToggled() ? getPresetMode().enabledTopRGB.getRGB() : enabledTopRGB.getRGB();
    }

    public static int getEnabledBottomRGB() {
        return usePreset.isToggled() ? getPresetMode().enabledBottomRGB.getRGB() : enabledBottomRGB.getRGB();
    }

    public static int getEnabledTextRGB() {
        return usePreset.isToggled() ? getPresetMode().enabledTextRGB.getRGB() : enabledTextRGB.getRGB();
    }

    public static int getDisabledTopRGB() {
        return usePreset.isToggled() ? getPresetMode().disabledTopRGB.getRGB() : disabledTopRGB.getRGB();
    }

    public static int getDisabledBottomRGB() {
        return usePreset.isToggled() ? getPresetMode().disabledBottomRGB.getRGB() : disabledBottomRGB.getRGB();
    }

    public static int getDisabledTextRGB() {
        return usePreset.isToggled() ? getPresetMode().disabledTextRGB.getRGB() : disabledTextRGB.getRGB();
    }

    public static int getBackgroundRGB() {
        return usePreset.isToggled() ? getPresetMode().backgroundRGB.getRGB() : backgroundRGB.getRGB();
    }

    public static Color getSettingBackgroundColor() {
        return usePreset.isToggled() ? getPresetMode().settingBackgroundRGB
                : new Color(settingBackgroundRGB.getRed(), settingBackgroundRGB.getGreen(),
                        settingBackgroundRGB.getBlue(), (int) getBackgroundOpacity());
    }

    public static Color getCategoryBackgroundColor() {
        return usePreset.isToggled() ? getPresetMode().categoryBackgroundRGB
                : new Color(categoryBackgroundRGB.getRed(), categoryBackgroundRGB.getGreen(),
                        categoryBackgroundRGB.getBlue(), (int) getBackgroundOpacity());
    }

    public static int getCategoryNameRGB() {
        return usePreset.isToggled() ? getPresetMode().categoryNameRGB.getRGB() : categoryNameRGB.getRGB();
    }

    public static CNColor getCNColor() {
        return usePreset.isToggled() ? getPresetMode().cnColor : (CNColor) cnColor.getMode();
    }

    public static boolean rainbowNotification() {
        return rainbowNotification.isToggled();
    }

    public static boolean notifications() {
        return notifications.isToggled();
    }

    public enum Preset {
        Vape(true, false, true, true, // showGradientEnabled - showGradientDisabled - useCustomFont -
                // categoryBackground
                CNColor.STATIC, // just leave this
                // new Color(red, green, blue, alpha (optional out of 255 default is 255))
                new Color(255, 255, 255), // categoryNameRGB
                new Color(27, 25, 26, 255), // settingBackgroundRGB
                new Color(27, 25, 26), // categoryBackgroundRGB
                new Color(59, 132, 107), // enabledTopRGB
                new Color(59, 132, 107), // enabledBottomRGB
                new Color(250, 250, 250), // enabledTextRGB
                new Color(27, 25, 26), // disabledTopRGB
                new Color(27, 25, 26), // disabledBottomRGB
                new Color(255, 255, 255), // disabledTextRGB
                new Color(27, 25, 26) // backgroundRGBW
                ), 
        PlusPlus( // name
                false, false, true, true, // showGradientEnabled - showGradientDisabled - useCustomFont -
                // categoryBackground
                CNColor.STATIC, // just leave this
                // new Color(red, green, blue, alpha (optional out of 255 default is 255))
                new Color(255, 255, 255), // categoryNameRGB
                new Color(176, 103, 255, 153), // settingBackgroundRGB
                new Color(176, 103, 255, 153), // categoryBackgroundRGB
                new Color(0, 0, 0), // enabledTopRGB
                new Color(0, 0, 0), // enabledBottomRGB
                new Color(255, 0, 194), // enabledTextRGB
                new Color(0, 0, 0), // disabledTopRGB
                new Color(0, 0, 0), // disabledBottomRGB
                new Color(255, 255, 255), // disabledTextRGB
                new Color(173, 0, 233) // backgroundRGB
                );

        public boolean showGradientEnabled, showGradientDisabled, useCustomFont, categoryBackground;
        public int backgroundOpacity;
        public Color categoryNameRGB, settingBackgroundRGB, categoryBackgroundRGB, enabledTopRGB, enabledBottomRGB,
        enabledTextRGB, disabledTopRGB, disabledBottomRGB, disabledTextRGB, backgroundRGB;
        public CNColor cnColor;

        private Preset(boolean showGradientEnabled, boolean showGradientDisabled, boolean useCustomFont,
                boolean categoryBackground, CNColor cnColor, Color categoryNameRGB, Color settingBackgroundRGB,
                Color categoryBackgroundRGB, Color enabledTopRGB, Color enabledBottomRGB, Color enabledTextRGB,
                Color disabledTopRGB, Color disabledBottomRGB, Color disabledTextRGB, Color backgroundRGB) {
            this.showGradientEnabled = showGradientEnabled;
            this.showGradientDisabled = showGradientDisabled;
            this.useCustomFont = useCustomFont;
            this.categoryBackground = categoryBackground;
            this.backgroundOpacity = backgroundOpacity;
            this.categoryNameRGB = categoryNameRGB;
            this.settingBackgroundRGB = settingBackgroundRGB;
            this.categoryBackgroundRGB = categoryBackgroundRGB;
            this.enabledTopRGB = enabledTopRGB;
            this.enabledBottomRGB = enabledBottomRGB;
            this.enabledTextRGB = enabledTextRGB;
            this.disabledTopRGB = disabledTopRGB;
            this.disabledBottomRGB = disabledBottomRGB;
            this.disabledTextRGB = disabledTextRGB;
            this.backgroundRGB = backgroundRGB;
            this.cnColor = cnColor;
        }

    }

    public enum CNColor {
        RAINBOW, STATIC
    }
}
