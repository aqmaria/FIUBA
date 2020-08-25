package view.eventos;



import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.command.TirarDadosYAvanzarComando;
import model.juego.Juego;
import model.juego.Jugador;
import view.ContenedorPrincipal;
import view.utilities.Sonido;

public class BotonTirarDadosEventHandler implements EventHandler<ActionEvent>{
	private Juego juego;
	private ContenedorPrincipal contenedor;
	
	public BotonTirarDadosEventHandler(Juego juego , ContenedorPrincipal contenedor) {
		this.juego = juego;
		this.contenedor = contenedor;
	}
	@Override
	public void handle(ActionEvent actionEvent) {
		Jugador jugadorActual = this.juego.getJugadorActual();
		boolean puedeMoverseAntesDeTirar = jugadorActual.puedeMoverse();
		
		jugadorActual.getAccionesPosibles().get(TirarDadosYAvanzarComando.nombreAccion).ejecutar();
		
	    if(puedeMoverseAntesDeTirar) {
	    	ArrayList<Integer> valoresTirada = juego.getTiradaJuego().getValoresUltimaTirada();
	    	this.contenedor.actualizar();
	    	
	    	for(Integer valor : valoresTirada)
	    		this.contenedor.mostrarDado(valor);
			
			if(this.juego.estaTerminado()) {
				new Alerta("FIN DEL JUEGO","El ganador del juego es "+ juego.getJugadores().get(0).getNombre() + "FELICITACIONES");
				this.contenedor.actualizar();
	    	}
	    }
	    
	    else{
	    	new Alerta("NO PUEDE MOVERSE", "Jugador en carcel");
	    	this.contenedor.actualizar();
	    }
	}
}
