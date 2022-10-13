package keystrokesmod.client.utils.font;

import java.awt.Font;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import keystrokesmod.client.module.modules.HUD;

public class FontUtil {
    public static volatile int completed;

    public static FontRenderer normal;
    public static FontRenderer two;
    public static FontRenderer small;

    private static Font normal_;
    private static Font two_;
    private static Font small_;

    private static Font getFont(Map<String, Font> locationMap, String location, int size, int fonttype) {
        Font font = null;

        try {
            if (locationMap.containsKey(location))
				font = locationMap.get(location).deriveFont(Font.PLAIN, size);
			else {
                InputStream is = HUD.class.getResourceAsStream("/assets/keystrokes/fonts/" + location);
                assert is != null;
                font = Font.createFont(Font.TRUETYPE_FONT, is);
                locationMap.put(location, font);
                font = font.deriveFont(fonttype, size);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("comfortaa", Font.PLAIN, +size);
        }

        return font;
    }

    public static boolean hasLoaded() {
        return completed >= 3;
    }

    public static void bootstrap() {
        new Thread(() -> {
            Map<String, Font> locationMap = new HashMap<>();
            normal_ = getFont(locationMap, "gilroy.otf", 19, Font.PLAIN);
            two_ = getFont(locationMap, "gilroy.otf", 30, Font.PLAIN);
            small_ = getFont(locationMap, "gilroybold.otf", 14, Font.BOLD);
            completed++;
        }).start();
        new Thread(() -> {
            Map<String, Font> locationMap = new HashMap<>();
            completed++;
        }).start();
        new Thread(() -> {
            Map<String, Font> locationMap = new HashMap<>();
            completed++;
        }).start();

        while (!hasLoaded())
			try {
                // noinspection BusyWait
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        normal = new FontRenderer(normal_, true, true);
        two = new FontRenderer(normal_, true, true);
        small = new FontRenderer(small_, true, true);
    }
}