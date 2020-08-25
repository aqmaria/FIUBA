package model.juego;

import excepciones.JugadorEfectivoInsuficienteException;
import model.command.PagarFianzaComando;

public class JugadorEnCarcelPuedePagarFianzaState implements JugadorState {
	
	private int turnosTranscurridos;
	
	public JugadorEnCarcelPuedePagarFianzaState() {
		turnosTranscurridos = 0;
	}
	
	@Override
	public void accionarCarcel(Jugador jugador) {
		turnosTranscurridos += 1;
		if(turnosTranscurridos > 1)
			liberar(jugador);
	}

	@Override
	public void pagarFianza(Jugador jugador, int monto) {
		if( jugador.getEfectivo()< monto ) throw new JugadorEfectivoInsuficienteException();
		jugador.debitar(monto);
		liberar(jugador);
	}

	private void liberar(Jugador jugador) {
		jugador.permitirMovimiento();
		jugador.eliminarAccionInicioTurno(PagarFianzaComando.nombreAccion);
		jugador.eliminarAccion(PagarFianzaComando.nombreAccion);
		jugador.setEnCarcelState(new JugadorLiberadoState());
	}
}
