package model.casillero.propiedad;

import model.juego.Jugador;

public interface PropiedadState {
	public void accionar(Jugador jugador, Propiedad propiedad);
}
