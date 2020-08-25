package model.juego;

public interface JugadorState {
	public void accionarCarcel(Jugador jugador);
	public void pagarFianza(Jugador jugador, int fianza);
}
