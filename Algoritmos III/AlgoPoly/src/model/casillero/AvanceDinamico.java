package model.casillero;

import java.util.Hashtable;
import model.espacio.Posicion;
import model.juego.Jugador;


public class AvanceDinamico extends MovimientoDinamico {
	
	public AvanceDinamico(Posicion posicion) {
		
		super(posicion);
		
		EstrategiaMovimientoDinamico estrategiaModulo = new EstrategiaMovimientoDinamicoEfectivoModuloTirada();
		EstrategiaMovimientoDinamico estrategiaMenosDos = new EstrategiaMovimientoDinamicoTiradaMenos2();
		EstrategiaMovimientoDinamico estrategiaSumProp = new EstrategiaMovimientoDinamicoTiradaMenosSumPropiedades();
		
		
		estrategiaCantidadMovimientos.put(2, estrategiaMenosDos);
		estrategiaCantidadMovimientos.put(3, estrategiaMenosDos);
		estrategiaCantidadMovimientos.put(4, estrategiaMenosDos);
		estrategiaCantidadMovimientos.put(5, estrategiaMenosDos);
		estrategiaCantidadMovimientos.put(6, estrategiaMenosDos);
		estrategiaCantidadMovimientos.put(7, estrategiaModulo);
		estrategiaCantidadMovimientos.put(8, estrategiaModulo);
		estrategiaCantidadMovimientos.put(9, estrategiaModulo);
		estrategiaCantidadMovimientos.put(10, estrategiaModulo);
		estrategiaCantidadMovimientos.put(11, estrategiaSumProp);
		estrategiaCantidadMovimientos.put(12, estrategiaSumProp);
		
	}

	@Override
	public void moverJugador(Jugador jugador, int cantCasilleros) {
		jugador.avanzar(cantCasilleros);
	}	

}
