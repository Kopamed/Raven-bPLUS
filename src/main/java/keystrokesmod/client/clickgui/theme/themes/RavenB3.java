package keystrokesmod.client.clickgui.theme.themes;

import keystrokesmod.client.clickgui.theme.Theme;

import java.awt.*;

public class RavenB3 implements Theme {
    @Override
    public String getName() {
        return "Raven B3";
    }

    @Override
    public Color getTextColour() {
        return new Color(0, 0, 0);
    }

    @Override
    public Color getBackgroundColour() {
        return new Color(179, 179, 179);
    }

    @Override
    public Color getSecondBackgroundColour() {
        return new Color(43, 43, 43);
    }

    @Override
    public Color getForegroundColour() {
        return null;
    }

    @Override
    public Color getSelectionBackgroundColour() {
        return null;
    }

    @Override
    public Color getSelectionForegroundColour() {
        return new Color(58, 58, 58);
    }

    @Override
    public Color getButtonColour() {
        return null;
    }

    @Override
    public Color getDisabledColour() {
        return new Color(160, 160, 160);
    }

    @Override
    public Color getContrastColour() {
        return new Color(0, 0, 0);
    }

    @Override
    public Color getAccentColour() {
        return new Color(0, 0, 0);
    }

    @Override
    public Color getActiveColour() {
        return null;
    }

    @Override
    public Color getExcludedColour() {
        return null;
    }

    @Override
    public Color getNotificationColour() {
        return null;
    }

    @Override
    public Color getHeadingColour() {
        return getArrayListColour(0, 100, 100);
    }

    @Override
    public Color getHighlightColour() {
        return null;
    }

    @Override
    public Color getBorderColour() {
        return null;
    }

    @Override
    public Color getBackdropColour() {
        return new Color(177, 177, 177, 50);
    }

    @Override
    public Color getArrayListColour(double currentY, double fullY, double speed) {
        double time = System.currentTimeMillis() + (speed * (currentY / fullY));
        return new Color(Color.HSBtoRGB((float) (time % (15000L / speed)) / (15000.0F / (float) speed), 1.0F, 1.0F));
    }
}
