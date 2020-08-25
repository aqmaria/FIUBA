package model.casillero;

import model.juego.Jugador;

public class EstrategiaMovimientoDinamicoTiradaMenos2 implements EstrategiaMovimientoDinamico {

	@Override
	public int cantidadCasillerosAMoverse(Jugador jugador) {
		return jugador.getUltimaTiradaDados() - 2;
	}

}
