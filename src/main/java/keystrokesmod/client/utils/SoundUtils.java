package keystrokesmod.client.utils;

import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundUtils {
	
	private static final HashMap<String, AudioInputStream> sounds = new HashMap<String, AudioInputStream>();
	
	
	public static void start() {
		addSound("click1");
	}
	
	public static void addSound(String name) {
		try {
		sounds.put(name, (AudioInputStream) SoundUtils.class.getResourceAsStream("/assets/keystrokes/sounds/" + name + ".wav"));
		} catch(Exception e) {
			
		}
	}
	
	public static void playSound(String name) {
	    try {
	        AudioInputStream audioInputStream = sounds.get(name);
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	        clip.start();
	    } catch(Exception ex) {
	        System.out.println("Error with playing sound.");
	        ex.printStackTrace();
	        addSound(name);
	    }
	}

}
