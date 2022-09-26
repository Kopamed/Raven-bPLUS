package keystrokesmod.client.clickgui.theme.themes;

import keystrokesmod.client.clickgui.theme.Theme;

import java.awt.*;

public class ArcDark implements Theme {
    @Override
    public String getName() {
        return "Arc Dark";
    }

    @Override
    public Color getTextColour() {
        return new Color(255, 255, 255);
    }

    @Override
    public Color getBackgroundColour() {
        return new Color(56, 60, 74);
    }

    @Override
    public Color getSecondBackgroundColour() {
        return new Color(64, 69, 82);
    }

    @Override
    public Color getForegroundColour() {
        return new Color(124, 129, 140);
    }

    @Override
    public Color getSelectionBackgroundColour() {
        return new Color(75, 81, 98);
    }

    @Override
    public Color getSelectionForegroundColour() {
        return new Color(31, 94, 212);
    }

    @Override
    public Color getButtonColour() {
        return new Color(64, 69, 82);
    }

    @Override
    public Color getDisabledColour() {
        return new Color(56, 56, 56);
    }

    @Override
    public Color getContrastColour() {
        return new Color(225, 82, 126);
    }

    @Override
    public Color getAccentColour() {
        return new Color(82, 148, 226);
    }

    @Override
    public Color getActiveColour() {
        return new Color(57, 61, 75);
    }

    @Override
    public Color getExcludedColour() {
        return getContrastColour();
    }

    @Override
    public Color getNotificationColour() {
        return new Color(51, 60, 77);
    }

    @Override
    public Color getHeadingColour() {
        return new Color(82, 224, 103);
    }

    @Override
    public Color getHighlightColour() {
        return new Color(80, 86, 102);
    }

    @Override
    public Color getBorderColour() {
        return new Color(43, 46, 57);
    }

    @Override
    public Color getBackdropColour() {
        return new Color(68, 73, 86, 40);
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
