package subterranean.crimson.server.audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class Sounds {

	private static Clip clip;
	static {
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void play(String sound) {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(Sounds.class.getResource("resource/" + sound + ".wav"));
			clip.open(audioInputStream);
			clip.start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
