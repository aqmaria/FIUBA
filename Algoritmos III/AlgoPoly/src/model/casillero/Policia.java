package model.casillero;

import model.espacio.Posicion;
import model.juego.Jugador;

public class Policia extends Casillero {
	
	private Posicion posicionCarcel;
	
	public Policia(Posicion posicion, Posicion posicionCarcelNueva) {
		super(posicion);
		this.posicionCarcel = posicionCarcelNueva;
	}

	@Override
	protected void accionar(Jugador jugador) {
		jugador.moverA(this.posicionCarcel);
	}
}
