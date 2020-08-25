package view;

import java.util.ArrayList;
import java.util.Hashtable;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import model.espacio.Posicion;
import model.juego.Juego;

public class VistaTablero {
	private final int cantCasilleros = 20;
	GridPane paneTablero; 
	private ArrayList<VistaCasillero> casilleros;
	private Juego juego;
	private Hashtable<String,Image> imgJugadores;
	private Image imagenCasita;
	private Image imagenHotel;
	
	public VistaTablero( Juego juego,BorderPane contenedorPrincipal,GridPane paneTablero) {
	
		this.paneTablero = paneTablero;
		casilleros = new ArrayList<VistaCasillero>();
		this.juego = juego;
		
		this.imgJugadores = new Hashtable<String,Image>();
		
		Image image1 = new Image("file:src/view/images/azul.png");
		imgJugadores.put("Azul",image1);
		Image image2 = new Image("file:src/view/images/amarillo.png");
		imgJugadores.put("Amarillo",image2);
		Image image3 = new Image("file:src/view/images/rojo.png");
		imgJugadores.put("Rojo",image3);
		
		this.imagenCasita = new Image("file:src/view/images/casita.png");
		this.imagenHotel = new Image("file:src/view/images/hotel.png");
		
		dibujarTablero(juego,contenedorPrincipal,paneTablero,casilleros,imgJugadores);
		dibujarCasilleros(casilleros);

	}

	private void dibujarTablero(Juego juego,BorderPane contenedorPrincipal,GridPane paneTablero,ArrayList<VistaCasillero> casilleros,Hashtable<String,Image> imagenesJugadores) {
	
		for(int i=0; i<6;i++) {
	    	for(int j=0; j<6; j++) {
	    		if(i==0 || i == 5 || j==0 || j==5 ) {
	    			casilleros.add(new VistaCasillero(j,i,paneTablero,juego,juego.getCasilleroEnPosicion(new Posicion(this.traducirCoordenada(j, i) ) ), imagenesJugadores, imagenCasita, imagenHotel) );
	    			
	    		}
	    	}
	    }
		
		 Image imagen = new Image("file:src/view/images/fondo_tablero.jpg");
		 BackgroundImage imagenDeFondo = new BackgroundImage(imagen, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		 this.paneTablero.setBackground(new Background(imagenDeFondo));	 
		 
		 //como no se usa un canvas con toooda la img del tablero, tengo que agregar la img de c/celda " a mano"
		 casilleros.get(0).setImage(new Image("file:src/view/images/impLujo.png"));
		 casilleros.get(1).setImage(new Image("file:src/view/images/stfe.png"));
		 casilleros.get(2).setImage(new Image("file:src/view/images/aysa.png"));
		 casilleros.get(3).setImage(new Image("file:src/view/images/stnort.png"));
		 casilleros.get(4).setImage(new Image("file:src/view/images/stsur.png"));
		 casilleros.get(5).setImage(new Image("file:src/view/images/poli.png"));
		 casilleros.get(6).setImage(new Image("file:src/view/images/cbanorte.png"));
		 casilleros.get(7).setImage(new Image("file:src/view/images/tren.png"));
		 casilleros.get(8).setImage(new Image("file:src/view/images/sute.png"));
		 casilleros.get(9).setImage(new Image("file:src/view/images/neuquen.png"));
		 casilleros.get(10).setImage(new Image("file:src/view/images/avadinamico.png"));
		 casilleros.get(11).setImage(new Image("file:src/view/images/redinamico.png"));
		 casilleros.get(12).setImage(new Image("file:src/view/images/cbasur.png"));
		 casilleros.get(13).setImage(new Image("file:src/view/images/tucu.png"));
		 casilleros.get(14).setImage(new Image("file:src/view/images/carcel.png"));
		 casilleros.get(15).setImage(new Image("file:src/view/images/banorte.png"));
		 casilleros.get(16).setImage(new Image("file:src/view/images/edesur.png"));
		 casilleros.get(17).setImage(new Image("file:src/view/images/basur.png"));
		 casilleros.get(18).setImage(new Image("file:src/view/images/quini6.png"));
		 casilleros.get(19).setImage(new Image("file:src/view/images/salida.png"));

	}
	
	private int traducirCoordenada(int posX , int posY) {
		if(posX==0 ) return posY+10;
		if(posX==5) return posX - posY;
		if(posY==0) return 10 -posX;
		return posX+posY+10;
	}
	
	
	
	private void dibujarCasilleros(ArrayList<VistaCasillero> casilleros) {
		for(int i=0 ; i< this.cantCasilleros; i++ )
			casilleros.get(i).dibujar();
	}
	
	public void actualizar() {
		this.dibujarCasilleros(this.casilleros);
	}
	
}
