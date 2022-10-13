package keystrokesmod.client.module.modules.client;

import keystrokesmod.client.clickgui.raven.components.CategoryComponent;
import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.Setting;
import keystrokesmod.client.module.setting.impl.ComboSetting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.ColorM;
import keystrokesmod.client.utils.Utils;

public class GuiModule extends Module {

    private static ComboSetting preset;

    private static TickSetting cleanUp, reset, betagui, rainbowNotification, notifications;

    public static int guiScale;

    public GuiModule() {
        super("Gui", ModuleCategory.client);
        withKeycode(54);

        this.registerSetting(betagui = new TickSetting("beta gui (VERY BETA)", false));
        this.registerSetting(cleanUp = new TickSetting("Clean Up", false));
        this.registerSetting(reset = new TickSetting("Reset position", false));
        this.registerSetting(notifications = new TickSetting("Notifications", false));
        this.registerSetting(rainbowNotification = new TickSetting("Reset position", false));
        this.registerSetting(preset = new ComboSetting("Preset", Preset.PlusPlus));
    }

    @Override
    public void guiButtonToggled(Setting setting) {
        if (setting == cleanUp) {
            cleanUp.disable();
            for (CategoryComponent cc : Raven.clickGui.getCategoryList())
                cc.setCoords(((cc.getX() / 50) * 50) + ((cc.getX() % 50) > 25 ? 50 : 0), ((cc.getY() / 50) * 50) + ((cc.getY() % 50) > 25 ? 50 : 0));
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

    public static boolean isCategoryBackgroundToggled() {
        return getPresetMode().categoryBackground;
    }

    public static boolean showGradientEnabled() {
        return getPresetMode().showGradientEnabled;
    }

    public static boolean showGradientDisabled() {
        return  getPresetMode().showGradientDisabled;
    }

    public static boolean useCustomFont() {
        return  getPresetMode().useCustomFont;
    }

    public static int getEnabledTopRGB(int delay) {
        return  getPresetMode().enabledTopRGB.color(delay);
    }

    public static int getEnabledTopRGB() {
        return getEnabledTopRGB(0);
    }

    public static int getEnabledBottomRGB(int delay) {
        return  getPresetMode().enabledBottomRGB.color(delay);
    }

    public static int getEnabledBottomRGB() {
        return getEnabledBottomRGB(0);
    }

    public static int getEnabledTextRGB(int delay) {
        return  getPresetMode().enabledTextRGB.color(delay);
    }

    public static int getEnabledTextRGB() {
        return getEnabledTextRGB(0);
    }

    public static int getDisabledTopRGB(int delay) {
        return  getPresetMode().disabledTopRGB.color(delay);
    }

    public static int getDisabledTopRGB() {
        return getDisabledTopRGB(0);
    }

    public static int getDisabledBottomRGB(int delay) {
        return  getPresetMode().disabledBottomRGB.color(delay);
    }

    public static int getDisabledBottomRGB() {
        return getDisabledBottomRGB(0);
    }

    public static int getDisabledTextRGB(int delay) {
        return  getPresetMode().disabledTextRGB.color(0);
    }

    public static int getDisabledTextRGB() {
        return getDisabledTextRGB(0);
    }

    public static int getBackgroundRGB(int delay) {
        return  getPresetMode().backgroundRGB.color(delay);
    }

    public static int getBackgroundRGB() {
        return getBackgroundRGB(0);
    }

    public static int getSettingBackgroundRGB(int delay) {
        return  getPresetMode().settingBackgroundRGB.color(delay);
    }

    public static int getSettingBackgroundRGB() {
        return getSettingBackgroundRGB(0);
    }


    public static int getCategoryBackgroundRGB(int delay) {
        return  getPresetMode().categoryBackgroundRGB.color(delay);
    }

    public static int getCategoryBackgroundRGB() {
        return getCategoryBackgroundRGB(0);
    }

    public static int getCategoryNameRGB(int delay) {
        return  getPresetMode().categoryNameRGB.color(delay);
    }

    public static int getCategoryNameRGB() {
        return getCategoryNameRGB(0);
    }

    public static int getBoarderColour(int delay) {
        return  getPresetMode().boarderColor.color(delay);
    }

    public static int getBoarderColour() {
        return getBoarderColour(0);
    }

    public static int getCategoryOutlineColor1(int delay) {
        return  getPresetMode().categoryOutlineColor.color(delay);
    }

    public static int getCategoryOutlineColor1() {
        return getCategoryOutlineColor1(0);
    }

    public static int getCategoryOutlineColor2(int delay) {
        return  getPresetMode().categoryOutlineColor2.color(delay);
    }

    public static int getCategoryOutlineColor2() {
        return getCategoryOutlineColor2(0);
    }

    public static CNColor getCNColor() {
        return  getPresetMode().cnColor;
    }

    public static boolean isSwingToggled() {
        return  getPresetMode().swing;
    }

    public static boolean isRoundedToggled() {
        return  getPresetMode().swing;
    }

    public static boolean isBoarderToggled() {
        return  getPresetMode().boarder;
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
        Vape( // name
                        true, false, true, true, // showGradientEnabled - showGradientDisabled - useCustomFont -
                        CNColor.STATIC, // just leave this
                        in -> 0xFFFFFFFE, // categoryNameRGB
                        in -> 0x99808080, // settingBackgroundRGB
                        in -> 0x99808080, // categoryBackgroundRGB
                        in -> -12876693, // enabledTopRGB
                        in -> -12876693, // enabledBottomRGB
                        in -> 0xFFFFFFFE, // enabledTextRGB
                        in -> 0xFF000000, // disabledTopRGB
                        in -> 0xFF000000, // disabledBottomRGB
                        in -> 0xFFFFFFFE, // disabledTextRGB
                        in -> 0x99808080, // backgroundRGB
                        true, //rounded
                        true, //swing
                        false, //boarder
                        in -> -12876693,
                        in -> -12876693,
                        in -> Utils.Client.otherAstolfoColorsDraw(in, 10)
                        ),

        PlusPlus( // name
                        true, false, true, true, // showGradientEnabled - showGradientDisabled - useCustomFont -
                        CNColor.STATIC, // just leave this
                        in -> 0xFFFFFFFE, // categoryNameRGB
                        in -> -15001318, // settingBackgroundRGB
                        in -> -15001318, // categoryBackgroundRGB
                        in -> Utils.Client.rainbowDraw(2, in), // enabledTopRGB
                        in -> Utils.Client.rainbowDraw(2, in), // enabledBottomRGB
                        in -> 0xFF000000, // enabledTextRGB
                        in -> 0xFF000000, // disabledTopRGB
                        in -> 0xFF000000, // disabledBottomRGB
                        in -> 0xFFFFFFFE, // disabledTextRGB
                        in -> 0xFF808080, // backgroundRGB
                        true, //rounded
                        true, //swing
                        true, //boarder
                        in -> 0xFFFFFFFE,
                        in -> Utils.Client.astolfoColorsDraw(in, 10),
                        in -> Utils.Client.otherAstolfoColorsDraw(in, 10)
                        );

        public boolean showGradientEnabled, showGradientDisabled, useCustomFont, categoryBackground, roundedCorners, swing, boarder;
        public ColorM categoryNameRGB, settingBackgroundRGB, categoryBackgroundRGB, enabledTopRGB, enabledBottomRGB,
        enabledTextRGB, disabledTopRGB, disabledBottomRGB, disabledTextRGB, backgroundRGB, boarderColor, categoryOutlineColor, categoryOutlineColor2;
        public CNColor cnColor;

        private Preset(
                        boolean showGradientEnabled, boolean showGradientDisabled, boolean useCustomFont,
                        boolean categoryBackground, CNColor cnColor, ColorM categoryNameRGB, ColorM settingBackgroundRGB,
                        ColorM categoryBackgroundRGB, ColorM enabledTopRGB, ColorM enabledBottomRGB, ColorM enabledTextRGB,
                        ColorM disabledTopRGB, ColorM disabledBottomRGB, ColorM disabledTextRGB, ColorM backgroundRGB,
                        boolean roundedCorners, boolean swing, boolean boarder, ColorM boarderColor, ColorM categoryOutlineColor, ColorM categoryOutlineColor2) {
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
            this.categoryOutlineColor = categoryOutlineColor;
            this.categoryOutlineColor2 = categoryOutlineColor2;
        }

    }



    public enum CNColor {
        RAINBOW, STATIC
    }
}
