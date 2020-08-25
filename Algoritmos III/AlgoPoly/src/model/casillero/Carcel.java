package model.casillero;
import model.espacio.Posicion;
import model.juego.Jugador;

public class Carcel extends Casillero{
	
	
	
	public Carcel(Posicion posicion) {
		super(posicion);
	}

	@Override
	protected void accionar(Jugador jugador) {
		jugador.accionarCarcel();
	}
}
