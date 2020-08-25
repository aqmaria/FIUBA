package model.casillero;

import model.espacio.Posicion;
import model.juego.Jugador;

public class ImpuestoAlLujo extends Casillero {

	private final static double PORCENTAJE = 0.1;
	
	public ImpuestoAlLujo(Posicion posicion) {
		super(posicion);
	}

	@Override
	protected void accionar(Jugador jugador) {
		double impuesto = jugador.getEfectivo() * PORCENTAJE; //redondea para abajo
		jugador.debitar((int)impuesto);
	}
	
	
}
