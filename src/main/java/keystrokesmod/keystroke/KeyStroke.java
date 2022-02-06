package keystrokesmod.keystroke;

public class KeyStroke {
   public static int x;
   public static int y;

   public static int currentColorNumber;

   public static boolean showMouseBtn;
   public static boolean mode;
   public static boolean outline;

   public KeyStroke() {
      x = 0;
      y = 0;

      currentColorNumber = 0;

      showMouseBtn = false;
      mode = true;
      outline = false;
   }
}
