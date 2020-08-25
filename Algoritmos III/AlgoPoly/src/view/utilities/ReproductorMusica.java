package view.utilities;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class ReproductorMusica {

    private static MediaPlayer musicPlayer;
    private static Timeline timeline;
    private static Boolean isMute = false;

    public static Timeline getTimeline() {
        return timeline;
    }


    public static void playBackGroundTheme(String musicFile, Boolean loopeable) {
        Media sound = new Media(new File(musicFile).toURI().toString());
        musicPlayer = new MediaPlayer(sound);

        if (loopeable)
            musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        musicPlayer.setMute(isMute);
        musicPlayer.play();
    }


    public static Boolean isMute() {
        return isMute;
    }


    public static void setMute(Boolean mute) {
        musicPlayer.setMute(mute);
        isMute = mute;
    }


    public static void stop() {
        musicPlayer.stop();
    }


    public static void fadeOut(Duration duracion) {

        timeline = new Timeline(new KeyFrame(duracion, new KeyValue(musicPlayer.volumeProperty(), 0)));
        timeline.play();
        timeline.setOnFinished( (actionEvent) -> {
            musicPlayer.stop();
            timeline.stop();
        } );
    }


}
