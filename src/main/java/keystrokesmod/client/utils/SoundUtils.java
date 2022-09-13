package keystrokesmod.client.utils;

import java.net.URL;
import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundUtils {

    private static final HashMap<String, AudioInputStream> sounds = new HashMap<String, AudioInputStream>();

    static {
        addSound("click");
    }
    
    public static void addSound(String name) {
        try {
            URL url = SoundUtils.class.getResource("/assets/keystrokes/sounds/" + name + ".wav");
            sounds.put(name, AudioSystem.getAudioInputStream(url));

        } catch (Exception e) {
            System.out.println("Error loading sound");
            e.printStackTrace();
        }
    }

    public static void playSound(String name) {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream audioInputStream = sounds.get(name);
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            System.out.println("Error with playing sound.");
            e.printStackTrace();
        }
    }

}
