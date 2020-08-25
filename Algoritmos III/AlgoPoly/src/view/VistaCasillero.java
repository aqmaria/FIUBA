package view;



import java.util.Hashtable;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model.casillero.Casillero;
import model.casillero.propiedad.Barrio;
import model.juego.Juego;
import model.juego.Jugador;

public class VistaCasillero extends GridPane{
	
	private static final int ancho = 180;
	private static final int altura = 81;
	private Rectangle border;
	
	private Image img;
	private Casillero casillero;
	private Hashtable<String,Image>imagenesJugadores;
	private Juego juego;
	private int x;
	private int y;
	private GridPane paneTablero;
	private Image imagenCasita;
	private Image imagenHotel;
	
	public VistaCasillero(int col, int row, GridPane paneTablero ,Juego juego ,Casillero casillero,Hashtable<String,Image> imagenesJugadores, Image imagenCasita, Image imagenHotel){
		this.x = row;
		this.y = col;
		border = new Rectangle(0, 0, ancho,altura );
        border.setStroke(Color.BLACK);
        border.setFill(Color.ALICEBLUE);
        this.paneTablero = paneTablero;
		paneTablero.add(border,col,row);
		this.casillero = casillero;
		this.imagenesJugadores = imagenesJugadores;
		this.juego = juego;
		this.imagenCasita = imagenCasita;
		this.imagenHotel = imagenHotel;
	}
	
	public void setImage(Image img ) {
		this.img= img;
	}
	
	
	public void dibujar() {
		
		border.setFill(new ImagePattern(this.img));
		double offset = 15;
		double posX = 50;
		for( Jugador jugador : this.juego.getJugadores() ) {
			if ( this.casillero.getPosicion().getValor() == jugador.getPosicion().getValor() ) {
				ImageView jugadorImg = new ImageView();
				jugadorImg.setImage(this.imagenesJugadores.get(jugador.getNombre()));				
				jugadorImg.setFitWidth(45);
				jugadorImg.setFitHeight(45);
				jugadorImg.setTranslateX(posX);
				this.paneTablero.add(jugadorImg, x, y);
				posX -= offset;
			}
		}
		offset = 15;
		posX = 140;
		int cantidad = 0;
		if(this.casillero instanceof Barrio) {
			Barrio barrio = (Barrio)this.casillero;
			ImageView edificacion = new ImageView();
			
			cantidad = barrio.getCantidadEdificaciones();
			if(cantidad == barrio.getLimiteEdificaciones()) {
				cantidad = 1;
				edificacion.setImage(imagenHotel);
				edificacion.setFitWidth(60);
				edificacion.setFitHeight(60);
			}
			else {
				edificacion.setImage(imagenCasita);
				edificacion.setFitWidth(45);
				edificacion.setFitHeight(45);
			}
			
			for (int i = 0; i < cantidad; i++) {
				edificacion.setTranslateX(posX);
				this.paneTablero.add(edificacion, x, y);
				posX -= offset;
			}
		}
	}
	
}
