package me.kopamed.raven.bplus.client.visual.clickgui.plus.theme;

import java.awt.*;

public interface Theme {
    public String getName();

    public Color getTextColour();

    public Color getBackgroundColour();

    public Color getSecondBackgroundColour();

    public Color getForegroundColour();

    public Color getSelectionBackgroundColour();

    public Color getSelectionForegroundColour();

    public Color getButtonColour();

    public Color getDisabledColour();

    public Color getContrastColour();

    public Color getAccentColour();

    public Color getActiveColour();

    public Color getExcludedColour();

    public Color getNotificationColour();

    public Color getHeadingColour();

    public Color getHighlightColour();

    public Color getBorderColour();

    public Color getBackdropColour();

    public Color getArrayListColour(double currentY, double fullY, double speed);
}
