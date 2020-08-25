package model.casillero;


import model.espacio.Posicion;
import model.juego.Juego;
import model.juego.Jugador;

public abstract class Casillero {
	
	protected Posicion posicion;
	protected Juego juego;
	//protected Jugador ultimoJugadorEnCasillero;
	
	public Casillero(Posicion posicionNueva) {
		posicion = posicionNueva;
	}
	
	protected void accionar(Jugador jugador) {};
	
	public Posicion getPosicion() {
		return this.posicion;
	}
	
	public void colocarJugador(Jugador jugador) {
		//this.ultimoJugadorEnCasillero = jugador;
		this.accionar(jugador);
	}
	
}
