package view.eventos;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.command.TerminarTurnoComando;
import model.command.TirarDadosYAvanzarComando;
import model.juego.Juego;
import model.juego.Jugador;
import view.ContenedorPrincipal;

public class BotonTerminarTurnoEventHandler implements EventHandler<ActionEvent> {
	private Juego juego;
	private ContenedorPrincipal contenedor;
	
	public BotonTerminarTurnoEventHandler(Juego juego , ContenedorPrincipal contenedor) {
		this.juego = juego;
		this.contenedor = contenedor;
	}
	
	@Override
	public void handle(ActionEvent actionEvent) {
		Jugador jugadorActual = juego.getJugadorActual();
		
		if(jugadorActual.getAccionesPosibles().containsKey(TirarDadosYAvanzarComando.nombreAccion))
			jugadorActual.getAccionesPosibles().get(TirarDadosYAvanzarComando.nombreAccion).ejecutar();
			
		jugadorActual.getAccionesPosibles().get(TerminarTurnoComando.nombreAccion).ejecutar();
		
		this.contenedor.actualizar();
	}
}
