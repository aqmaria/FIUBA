package model.casillero;

import model.juego.Jugador;

public class EstrategiaMovimientoDinamicoEfectivoModuloTirada implements EstrategiaMovimientoDinamico {

	@Override
	public int cantidadCasillerosAMoverse(Jugador jugador) {
		return jugador.getEfectivo() % jugador.getUltimaTiradaDados();
	}
	
}
