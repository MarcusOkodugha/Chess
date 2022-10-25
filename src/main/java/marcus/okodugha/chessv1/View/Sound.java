package marcus.okodugha.chessv1.View;

import marcus.okodugha.chessv1.Model.Move;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {


    public static void makeSound() {
        try {
            Clip clip;
            AudioInputStream sound;
            String soundFileName = "C:\\JavaProgram\\ChessV1\\src\\main\\java\\marcus\\okodugha\\chessv1\\resources\\moveSound.wav";
            File file = new File(soundFileName);
            sound = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(sound);
            clip.start();
        } catch (LineUnavailableException ex) {
            System.out.println(ex);;
        } catch (IOException ex) {
            System.out.println(ex);;
        } catch (UnsupportedAudioFileException e) {
            System.out.println(e);;
        }

    }

}
