package model.casillero;

import model.espacio.Posicion;
import model.juego.Jugador;

public class RetrocesoDinamico extends MovimientoDinamico {
	
	public RetrocesoDinamico(Posicion posicion) {	
		super(posicion);
		
		EstrategiaMovimientoDinamico estrategiaModulo = new EstrategiaMovimientoDinamicoEfectivoModuloTirada();
		EstrategiaMovimientoDinamico estrategiaMenosDos = new EstrategiaMovimientoDinamicoTiradaMenos2();
		EstrategiaMovimientoDinamico estrategiaSumPropiedades = new EstrategiaMovimientoDinamicoTiradaMenosSumPropiedades();
		
		estrategiaCantidadMovimientos.put(2, estrategiaSumPropiedades);
		estrategiaCantidadMovimientos.put(3, estrategiaSumPropiedades);
		estrategiaCantidadMovimientos.put(4, estrategiaSumPropiedades);
		estrategiaCantidadMovimientos.put(5, estrategiaSumPropiedades);
		estrategiaCantidadMovimientos.put(6, estrategiaSumPropiedades);
		estrategiaCantidadMovimientos.put(7, estrategiaModulo);
		estrategiaCantidadMovimientos.put(8, estrategiaModulo);
		estrategiaCantidadMovimientos.put(9, estrategiaModulo);
		estrategiaCantidadMovimientos.put(10, estrategiaModulo);
		estrategiaCantidadMovimientos.put(11, estrategiaMenosDos);
		estrategiaCantidadMovimientos.put(12, estrategiaMenosDos);
	}

	@Override
	public void moverJugador(Jugador jugador, int cantCasilleros) {
		jugador.retroceder(cantCasilleros);
	}	
}
