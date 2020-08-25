package model.juego;

import excepciones.JugadorLiberadoIntentaPagarFianzaException;

public class JugadorLiberadoState implements JugadorState{

	@Override
	public void accionarCarcel(Jugador jugador) {
		jugador.impedirMovimiento();
		jugador.setEnCarcelState(new JugadorEnCarcelSinFianzaState());
	}

	@Override
	public void pagarFianza(Jugador jugador, int fianza) {
		throw new JugadorLiberadoIntentaPagarFianzaException();
	}
	
}
