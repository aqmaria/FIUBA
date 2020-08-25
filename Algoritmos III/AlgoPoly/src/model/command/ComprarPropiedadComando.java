package model.command;

import model.casillero.propiedad.Propiedad;
import model.casillero.propiedad.PropiedadConDuenioState;
import model.juego.Jugador;

public class ComprarPropiedadComando extends Comando {

	public static String prefijoAccion = "Comprar ";
	private Propiedad propiedad;
	private Jugador jugador;
	
	
	
	public ComprarPropiedadComando(Propiedad nuevaPropiedad, Jugador nuevoJugador) {
		super(prefijoAccion + nuevaPropiedad.getNombre());
		this.propiedad = nuevaPropiedad;
		this.jugador = nuevoJugador;
	}



	@Override
	public void ejecutar() {
		this.jugador.debitar(this.propiedad.getPrecioVenta());
		this.propiedad.setState(new PropiedadConDuenioState());	
		this.propiedad.setPropietario(this.jugador);
		this.jugador.agregarPropiedad(this.propiedad);
		this.jugador.eliminarAccion(nombre);
	}

}
