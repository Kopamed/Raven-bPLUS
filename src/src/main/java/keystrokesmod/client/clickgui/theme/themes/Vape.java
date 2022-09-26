package keystrokesmod.client.clickgui.theme.themes;

import keystrokesmod.client.clickgui.theme.Theme;

import java.awt.*;

public class Vape implements Theme {
    @Override
    public String getName() {
        return "VapeV4";
    }

    @Override
    public Color getTextColour() {
        return new Color(252, 253, 253);
    }

    @Override
    public Color getBackgroundColour() {
        return new Color(33, 31, 28);
    }

    @Override
    public Color getSecondBackgroundColour() {
        return new Color(43, 42, 41);
    }

    @Override
    public Color getForegroundColour() {
        return getTextColour();
    }

    @Override
    public Color getSelectionBackgroundColour() {
        return new Color(88, 89, 89); // dot on tick thingy
    }

    @Override
    public Color getSelectionForegroundColour() {
        return new Color(40, 38, 38);
    }

    @Override
    public Color getButtonColour() {
        return new Color(17, 19, 21);
    }

    @Override
    public Color getDisabledColour() {
        return new Color(120, 118, 115);
    }

    @Override
    public Color getContrastColour() {
        return new Color(249, 125, 1);
    }

    @Override
    public Color getAccentColour() {
        return new Color(17, 209, 169);
    }

    @Override
    public Color getActiveColour() {
        return getAccentColour();
    }

    @Override
    public Color getExcludedColour() {
        return getDisabledColour();
    }

    @Override
    public Color getNotificationColour() {
        return getSecondBackgroundColour();
    }

    @Override
    public Color getHeadingColour() {
        return getAccentColour();
    }

    @Override
    public Color getHighlightColour() {
        return new Color(36, 36, 36);
    }

    @Override
    public Color getBorderColour() {
        return getBackdropColour();
    }

    @Override
    public Color getBackdropColour() {
        return new Color(0, 0, 0, 0);
    }

    @Override
    public Color getArrayListColour(double currentY, double fullY, double speed) {
        long time = System.currentTimeMillis();
        long l1 = (long) (fullY - currentY) * 10L;
        float f1 = (float) (time % (l1 / speed));
        float f2 = (l1 / (float) speed);
        float c = f1 / f2;
        return Color.getHSBColor(c, 1.0F, 1.0F);
    }
}
