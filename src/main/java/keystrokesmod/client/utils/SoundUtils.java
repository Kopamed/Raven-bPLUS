package keystrokesmod.client.utils;

import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundUtils {

    private static final HashMap<String, AudioInputStream> sounds = new HashMap<String, AudioInputStream>();
    private static Clip clip;

    public static void playSound(String name) {
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(SoundUtils.class.getResource("/assets/keystrokesmod/sounds/" + name + ".wav")));
            clip.start();
        } catch (Exception e) {
            System.out.println("Error with playing sound.");
            e.printStackTrace();
        }
    }

}
