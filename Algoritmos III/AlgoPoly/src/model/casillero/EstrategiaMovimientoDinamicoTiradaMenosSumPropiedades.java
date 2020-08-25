package model.casillero;

import model.juego.Jugador;

public class EstrategiaMovimientoDinamicoTiradaMenosSumPropiedades implements EstrategiaMovimientoDinamico{

	@Override
	public int cantidadCasillerosAMoverse(Jugador jugador) {
		return jugador.getUltimaTiradaDados() - jugador.getSumaPropiedades();
	}

}
