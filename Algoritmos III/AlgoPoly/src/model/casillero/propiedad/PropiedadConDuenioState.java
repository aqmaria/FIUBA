package model.casillero.propiedad;

import model.juego.Jugador;

public class PropiedadConDuenioState implements PropiedadState {

	@Override
	public void accionar(Jugador jugador, Propiedad propiedad) {
		if(jugador == propiedad.getPropietario()) return;
		
		int monto = propiedad.getRenta(jugador);
		
		jugador.debitar(monto);
		propiedad.getPropietario().acreditar(monto);
		
	}

}
