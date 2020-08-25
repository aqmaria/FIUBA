package model.casillero.propiedad;

import model.command.ComprarPropiedadComando;
import model.juego.Jugador;

public class PropiedadSinDuenioState implements PropiedadState {

	@Override
	public void accionar(Jugador jugador, Propiedad propiedad) {
		
		int monto = propiedad.getPrecioVenta();
		
		if(!jugador.puedePagar(monto)) return;;
		
		jugador.agregarAccion(new ComprarPropiedadComando(propiedad, jugador));
		
		
	}
	
}
