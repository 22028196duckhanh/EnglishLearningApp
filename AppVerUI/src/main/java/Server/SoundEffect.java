package Server;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class SoundEffect extends Thread {

    /*Enum Sound {
        String a = "true";
    };*/
    public static void playAudio(String filePath) {
        Media media = new Media(new File(filePath).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        // Play the audio
        mediaPlayer.play();

        // Optionally, you can set the volume
        mediaPlayer.setVolume(0.5); // Adjust the volume (0.0 to 1.0)
    }

    public static void trueSound(){
        playAudio("src/main/resources/Utils/sound/true_sound.mp3");
    }
    public static void falseSound(){
        playAudio("src/main/resources/Utils/sound/false_sound.mp3");
    }
    public static void endSound(){
        playAudio("src/main/resources/Utils/sound/endgame.mp3");
    }

    @Override
    public void run() {

    }
}