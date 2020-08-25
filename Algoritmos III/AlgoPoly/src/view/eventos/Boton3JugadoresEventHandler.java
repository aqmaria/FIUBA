package view.eventos;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.juego.Juego;
import view.ContenedorPrincipal;

public class Boton3JugadoresEventHandler implements EventHandler<ActionEvent>{
	private Juego juego;
	private ContenedorPrincipal contenedor;
	
	public Boton3JugadoresEventHandler(Juego juego , ContenedorPrincipal contenedor) {
		this.juego = juego;
		this.contenedor = contenedor;
	}

	@Override
	public void handle(ActionEvent actionEvent) {
		this.juego.agregarJugador("Amarillo");
		this.juego.agregarJugador("Rojo");
		this.juego.agregarJugador("Azul");
		this.juego.establecerTurnosAlAzar();
        this.juego.iniciarJuego();
        this.contenedor.actualizar();

		
	}
}
