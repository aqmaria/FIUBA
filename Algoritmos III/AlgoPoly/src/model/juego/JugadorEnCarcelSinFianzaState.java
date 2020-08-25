package model.juego;

import excepciones.JugadorNoPuedePagarFianzaEnSegundoTurnoDeCarcelException;
import model.command.PagarFianzaComando;

public class JugadorEnCarcelSinFianzaState implements JugadorState {

	@Override
	public void accionarCarcel(Jugador jugador) {
		jugador.agregarAccionInicioTurno(new PagarFianzaComando(jugador));
		jugador.setEnCarcelState(new JugadorEnCarcelPuedePagarFianzaState());
	}

	@Override
	public void pagarFianza(Jugador jugador, int fianza) {
		throw new JugadorNoPuedePagarFianzaEnSegundoTurnoDeCarcelException();
	}

}
