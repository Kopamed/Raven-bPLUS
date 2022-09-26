package keystrokesmod.client.clickgui.theme;

import java.awt.*;

public interface Theme {
    String getName();

    Color getTextColour();

    Color getBackgroundColour();

    Color getSecondBackgroundColour();

    Color getForegroundColour();

    Color getSelectionBackgroundColour();

    Color getSelectionForegroundColour();

    Color getButtonColour();

    Color getDisabledColour();

    Color getContrastColour();

    Color getAccentColour();

    Color getActiveColour();

    Color getExcludedColour();

    Color getNotificationColour();

    Color getHeadingColour();

    Color getHighlightColour();

    Color getBorderColour();

    Color getBackdropColour();

    Color getArrayListColour(double currentY, double fullY, double speed);
}
