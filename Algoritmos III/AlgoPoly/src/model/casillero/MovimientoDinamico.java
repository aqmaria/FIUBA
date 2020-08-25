package model.casillero;

import java.util.Hashtable;

import model.espacio.Posicion;
import model.juego.Jugador;

public abstract class MovimientoDinamico extends Casillero {

	protected Hashtable<Integer, EstrategiaMovimientoDinamico> estrategiaCantidadMovimientos ;
	
	public MovimientoDinamico(Posicion posicion) {	
		super(posicion);
	
		estrategiaCantidadMovimientos = new Hashtable<Integer, EstrategiaMovimientoDinamico>();
	}
	
	

	@Override
	protected void accionar(Jugador jugador) {
		if( jugador.getUltimaTiradaDados() == 1 ) return; //Q deberia devolver exception
		
		int cantCasillerosAMoverse = this.estrategiaCantidadMovimientos.get(jugador.getUltimaTiradaDados()).cantidadCasillerosAMoverse(jugador);
		
		if(cantCasillerosAMoverse == 0) return; //Entraria en loop infinito si jugador tiene 0 efectivo
		
		this.moverJugador(jugador, cantCasillerosAMoverse);
	}
	
	public abstract void moverJugador(Jugador jugador, int cantCasilleros);
}
