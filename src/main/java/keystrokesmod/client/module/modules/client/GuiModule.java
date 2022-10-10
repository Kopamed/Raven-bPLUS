package keystrokesmod.client.module.modules.client;

import keystrokesmod.client.clickgui.raven.components.CategoryComponent;
import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.ComboSetting;
import keystrokesmod.client.module.setting.impl.RGBSetting;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.ColorM;
import keystrokesmod.client.utils.Utils;

public class GuiModule extends Module {

    private static SliderSetting backgroundOpacity;

    private static ComboSetting preset, cnColor;

    private static TickSetting categoryBackground, cleanUp, reset, usePreset, rainbowNotification, notifications, betagui,

    matchTopWBottomEnabled, matchTopWBottomDisabled, showGradientEnabled, showGradientDisabled, useCustomFont, roundedCorners, swing, boarder;

    private static RGBSetting enabledTopRGB, enabledBottomRGB, enabledTextRGB, disabledTopRGB, disabledBottomRGB,
    disabledTextRGB, backgroundRGB, settingBackgroundRGB, categoryBackgroundRGB, categoryNameRGB;

    public static int guiScale;

    public GuiModule() {
        super("Gui", ModuleCategory.client);
        withKeycode(54);

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
        this.registerSetting(roundedCorners = new TickSetting("Rounded corners", true));
        this.registerSetting(swing = new TickSetting("Swing", true));
        this.registerSetting(boarder = new TickSetting("boarder", true));
        this.registerSetting(preset = new ComboSetting("Preset", Preset.PlusPlus));
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
			if(betagui.isToggled()) {
			    guiScale = mc.gameSettings.guiScale;
			    mc.gameSettings.guiScale = 3;
				mc.displayGuiScreen(Raven.kvCompactGui);
                Raven.kvCompactGui.initGui();
                Raven.kvCompactGui.initGui(); //no idea why this works
			}
			else {
                mc.displayGuiScreen(Raven.clickGui);
                Raven.clickGui.initMain();
            }

        this.disable();
    }


    private static Preset getPresetMode() {
        return (Preset) preset.getMode();
    }

    // sgimas going to tell me theres a better way to do this isnt he

    public static int getBackgroundOpacity() {
        return usePreset.isToggled() ? 0 : (int) (backgroundOpacity.getInput() * 2.55);
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

    public static int getEnabledTopRGB(int delay) {
        return usePreset.isToggled() ? getPresetMode().enabledTopRGB.color(delay) : enabledTopRGB.getRGB();
    }

    public static int getEnabledTopRGB() {
        return getEnabledTopRGB(0);
    }

    public static int getEnabledBottomRGB(int delay) {
        return usePreset.isToggled() ? getPresetMode().enabledBottomRGB.color(delay) : enabledBottomRGB.getRGB();
    }

    public static int getEnabledBottomRGB() {
        return getEnabledBottomRGB(0);
    }

    public static int getEnabledTextRGB(int delay) {
        return usePreset.isToggled() ? getPresetMode().enabledTextRGB.color(delay) : enabledTextRGB.getRGB();
    }

    public static int getEnabledTextRGB() {
        return getEnabledTextRGB(0);
    }

    public static int getDisabledTopRGB(int delay) {
        return usePreset.isToggled() ? getPresetMode().disabledTopRGB.color(delay) : disabledTopRGB.getRGB();
    }

    public static int getDisabledTopRGB() {
        return getDisabledTopRGB(0);
    }

    public static int getDisabledBottomRGB(int delay) {
        return usePreset.isToggled() ? getPresetMode().disabledBottomRGB.color(delay) : disabledBottomRGB.getRGB();
    }

    public static int getDisabledBottomRGB() {
        return getDisabledBottomRGB(0);
    }

    public static int getDisabledTextRGB(int delay) {
        return usePreset.isToggled() ? getPresetMode().disabledTextRGB.color(0) : disabledTextRGB.getRGB();
    }

    public static int getDisabledTextRGB() {
        return getDisabledTextRGB(0);
    }

    public static int getBackgroundRGB(int delay) {
        return usePreset.isToggled() ? getPresetMode().backgroundRGB.color(delay) : backgroundRGB.getRGB();
    }

    public static int getBackgroundRGB() {
        return getBackgroundRGB(0);
    }

    public static int getSettingBackgroundRGB(int delay) {
        return usePreset.isToggled() ? getPresetMode().settingBackgroundRGB.color(delay) : settingBackgroundRGB.getRGB();
    }

    public static int getSettingBackgroundRGB() {
        return getSettingBackgroundRGB(0);
    }


    public static int getCategoryBackgroundRGB(int delay) {
        return usePreset.isToggled() ? getPresetMode().categoryBackgroundRGB.color(delay) : categoryBackgroundRGB.getRGB();
    }

    public static int getCategoryBackgroundRGB() {
        return getCategoryBackgroundRGB(0);
    }

    public static int getCategoryNameRGB(int delay) {
        return usePreset.isToggled() ? getPresetMode().categoryNameRGB.color(delay) : categoryNameRGB.getRGB();
    }

    public static int getCategoryNameRGB() {
        return getCategoryNameRGB(0);
    }

    public static int getBoarderColour(int delay) {
        return usePreset.isToggled() ? getPresetMode().boarderColor.color(delay) : categoryNameRGB.getRGB();
    }

    public static int getBoarderColour() {
        return getBoarderColour(0);
    }

    public static CNColor getCNColor() {
        return usePreset.isToggled() ? getPresetMode().cnColor : (CNColor) cnColor.getMode();
    }

    public static boolean isSwingToggled() {
        return usePreset.isToggled() ? getPresetMode().swing : swing.isToggled();
    }

    public static boolean isRoundedToggled() {
        return usePreset.isToggled() ? getPresetMode().swing : roundedCorners.isToggled();
    }

    public static boolean isBoarderToggled() {
        return boarder.isToggled() ? getPresetMode().swing : swing.isToggled();
    }

    public static boolean rainbowNotification() {
        return rainbowNotification.isToggled();
    }

    public static boolean notifications() {
        return notifications.isToggled();
    }

    public enum Preset {
       /* Vape(true, false, true, true, // showGradientEnabled - showGradientDisabled - useCustomFont -
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
                new Color(27, 25, 26), // backgroundRGBW
                false, //rounded
                false //swing
                ), */
        PlusPlus( // name
                false, false, true, true, // showGradientEnabled - showGradientDisabled - useCustomFont -
                CNColor.STATIC, // just leave this
                in -> 0xFFFFFFFF, // categoryNameRGB
                in -> 0x99b067ff, // settingBackgroundRGB
                in -> 0x99b067ff, // categoryBackgroundRGB
                in -> 0xFF000000, // enabledTopRGB
                in -> 0xFF000000, // enabledBottomRGB
                in -> 0xFFFFFFFF, // enabledTextRGB
                in -> 0xFF000000, // disabledTopRGB
                in -> 0xFF000000, // disabledBottomRGB
                in -> 0xffff0c02, // disabledTextRGB
                in -> 0xFFad0e09, // backgroundRGB
                true, //rounded
                true, //swing
                true, //boarder
                (in -> Utils.Client.astolfoColorsDraw(14, 10))//boarderColor
                );

        public boolean showGradientEnabled, showGradientDisabled, useCustomFont, categoryBackground, roundedCorners, swing, boarder;
        public ColorM categoryNameRGB, settingBackgroundRGB, categoryBackgroundRGB, enabledTopRGB, enabledBottomRGB,
        enabledTextRGB, disabledTopRGB, disabledBottomRGB, disabledTextRGB, backgroundRGB, boarderColor;
        public CNColor cnColor;

        private Preset(
                boolean showGradientEnabled, boolean showGradientDisabled, boolean useCustomFont,
                boolean categoryBackground, CNColor cnColor, ColorM categoryNameRGB, ColorM settingBackgroundRGB,
                ColorM categoryBackgroundRGB, ColorM enabledTopRGB, ColorM enabledBottomRGB, ColorM enabledTextRGB,
                ColorM disabledTopRGB, ColorM disabledBottomRGB, ColorM disabledTextRGB, ColorM backgroundRGB,
                boolean roundedCorners, boolean swing, boolean boarder, ColorM boarderColor) {
            this.showGradientEnabled = showGradientEnabled;
            this.showGradientDisabled = showGradientDisabled;
            this.useCustomFont = useCustomFont;
            this.categoryBackground = categoryBackground;
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
            this.roundedCorners = roundedCorners;
            this.swing = swing;
            this.boarder = boarder;
            this.boarderColor = boarderColor;
        }

    }



    public enum CNColor {
        RAINBOW, STATIC
    }
}
