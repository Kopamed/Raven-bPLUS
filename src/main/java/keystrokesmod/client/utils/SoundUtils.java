package keystrokesmod.client.utils;

import java.net.URL;
import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class SoundUtils {

    private static final HashMap<String, AudioInputStream> sounds = new HashMap<String, AudioInputStream>();
    private static Clip clip;

    static {
        addSound("click");
    }
    
    public static void addSound(String name) {
        try {
            AudioInputStream as = AudioSystem.getAudioInputStream(SoundUtils.class.getResource("/assets/keystrokes/sounds/" + name + ".wav"));

        } catch (Exception e) {
            System.out.println("Error loading sound");
            e.printStackTrace();
        }
    }

    public static void playSound(String name) {
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(SoundUtils.class.getResource("/assets/keystrokes/sounds/" + name + ".wav")));
            clip.start();
        } catch (Exception e) {
            System.out.println("Error with playing sound.");
            e.printStackTrace();
        }
    }

}
