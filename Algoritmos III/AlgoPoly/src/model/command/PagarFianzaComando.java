package model.command;

import model.juego.Jugador;

public class PagarFianzaComando extends Comando {

	private Jugador jugador;
	public static String nombreAccion = "Pagar fianza";

	@Override
	public void ejecutar() {
		jugador.pagarFianza();
	}

	public PagarFianzaComando(Jugador jugadorNuevo) {
		super(nombreAccion);
		this.jugador = jugadorNuevo;
	}

}
