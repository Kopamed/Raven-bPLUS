package keystrokesmod.client.clickgui.theme.themes;

import keystrokesmod.client.clickgui.theme.Theme;

import java.awt.*;

public class MaterialDark implements Theme {
    @Override
    public String getName() {
        return "Material Dark";
    }

    @Override
    public Color getTextColour() {
        return new Color(96, 125, 139).brighter();
    }

    @Override
    public Color getBackgroundColour() {
        return new Color(38, 50, 56);
    }

    @Override
    public Color getSecondBackgroundColour() {
        return new Color(50, 66, 74);
    }

    @Override
    public Color getForegroundColour() {
        return new Color(176, 190, 197);
    }

    @Override
    public Color getSelectionBackgroundColour() {
        return new Color(84, 110, 122);
    }

    @Override
    public Color getSelectionForegroundColour() {
        return new Color(255, 255, 255);
    }

    @Override
    public Color getButtonColour() {
        return new Color(46, 60, 67);
    }

    @Override
    public Color getDisabledColour() {
        return new Color(65, 89, 103);
    }

    @Override
    public Color getContrastColour() {
        return getAccentColour().brighter().brighter();
    }

    @Override
    public Color getAccentColour() {
        return new Color(0, 150, 136);
    }

    @Override
    public Color getActiveColour() {
        return new Color(49, 69, 73);
    }

    @Override
    public Color getExcludedColour() {
        return new Color(46, 60, 67);
    }

    @Override
    public Color getNotificationColour() {
        return new Color(30, 39, 44);
    }

    @Override
    public Color getHeadingColour() {
        return new Color(70, 231, 167);
    }

    @Override
    public Color getHighlightColour() {
        return new Color(66, 91, 103);
    }

    @Override
    public Color getBorderColour() {
        return new Color(42, 55, 62);
    }

    @Override
    public Color getBackdropColour() {
        return new Color(46, 46, 46, 90);
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
