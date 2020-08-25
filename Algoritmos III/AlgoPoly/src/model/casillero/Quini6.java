package model.casillero;

import java.util.Hashtable;

import model.espacio.Posicion;
import model.juego.Jugador;

public class Quini6 extends Casillero{
	
	private Hashtable<Jugador,Integer> historialDeJugadores ;
	
	public Quini6(Posicion posicion){
		super(posicion);
		this.historialDeJugadores = new Hashtable<Jugador,Integer>();
	}

	@Override
	protected void accionar(Jugador jugador) {
		if( !historialDeJugadores.containsKey(jugador) ) {
			
			historialDeJugadores.put(jugador, 1);
			jugador.acreditar(50000);
			return;
		}
		else if( historialDeJugadores.get(jugador) < 2 ) {
			historialDeJugadores.put(jugador, historialDeJugadores.get(jugador) + 1);
			jugador.acreditar(30000);
		}
	}
}
