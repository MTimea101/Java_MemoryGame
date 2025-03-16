import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundHandler {

    private Clip clip; // A lejátszandó hang

    // Konstruktor
    public SoundHandler() {
    }

    // Hangfájl lejátszása
    public void playSound(String filePath) {
        try {
            // Hangfájl betöltése
            File soundFile = new File(filePath);
            if (!soundFile.exists()) {
                System.err.println("Sound file not found: " + filePath);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error while playing sound: " + e.getMessage());
        }
    }

    // Háttérzene lejátszása ismétléssel
    public void playBackgroundMusic(String filePath) {
        try {
            // Hangfájl betöltése
            File soundFile = new File(filePath);
            if (!soundFile.exists()) {
                System.err.println("Sound file not found: " + filePath);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Folyamatos ismétlés
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error while playing background music: " + e.getMessage());
        }
    }

    // Hangfájl megállítása
    public void stopSound() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}
