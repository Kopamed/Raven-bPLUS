package keystrokesmod.keystroke;

public class KeyStroke {
    public static int x;
    public static int y;
    public static int currentColorNumber;
    public static boolean showMouseButtons;
    public static boolean enabled;
    public static boolean outline;

    public KeyStroke() {
        x = 0;
        y = 0;
        currentColorNumber = 0;
        showMouseButtons = false;
        enabled = true;
        outline = false;
    }
}
