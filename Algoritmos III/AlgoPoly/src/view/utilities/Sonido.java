package view.utilities;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Sonido {

	public Sonido(String direccion) {
		Media sound = new Media(new File(direccion).toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.play();
	}
}
