package model.command;

import model.juego.Juego;

public class TerminarTurnoComando extends Comando {

	private Juego juego;
	public static String nombreAccion = "Terminar turno";

	public TerminarTurnoComando(Juego juegoNuevo) {
		super(nombreAccion);
		this.juego = juegoNuevo;
	}
	
	@Override
	public void ejecutar() {
		juego.avanzarTurno();
	}
}
