package model.juego;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;


public class Turno {
	private ArrayList<Jugador> jugadores;
	private int jugadorActual;
	private TreeSet<Integer> saltear;
	
	
	public Turno() {
		this.jugadores = new ArrayList<Jugador>();
		jugadorActual = 0;
		saltear = new TreeSet<Integer>();
	}
	
	public void agregarJugadores(Collection<Jugador> jugadores) {
		this.jugadores = new ArrayList<Jugador>(jugadores);
	}
	
	public Jugador getJugadorActual() {
		return jugadores.get(jugadorActual);
	}

	public void avanzar() {
		avanzarJugadorActual();
		
		while(saltear.contains(jugadorActual))
			avanzarJugadorActual();
	}

	private void avanzarJugadorActual() {
		jugadorActual = (jugadorActual + 1) % jugadores.size();
	}

	public void eliminar(Jugador jugador) {
		this.saltear.add(this.jugadores.indexOf(jugador));
		if(jugador == jugadores.get(jugadorActual)) this.avanzar();
	}
	
}
