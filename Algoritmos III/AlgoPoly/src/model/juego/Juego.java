package model.juego;

import java.util.ArrayList;
import java.util.Collections;

import model.casillero.Casillero;
import model.espacio.Posicion;
import model.espacio.Tablero;

public class Juego {
	
	private Turno turno;
	
	private ArrayList<Jugador> jugadores;
	
	private Dado dadoJuego;
	private TiradaDados tiradaJuego;

	private Tablero tablero;
	private int efectivoInicial;
	private int fianza;
	
	private boolean hayGanador;
	
	public Juego() {
		jugadores = new ArrayList<Jugador>();
		turno = new Turno();
		dadoJuego = new Dado(1, 6);		
		tiradaJuego = new TiradaDados();
		this.construirTirada();
		tablero = new Tablero();
		this.efectivoInicial = 100000; //lo pongo aca por si queremos en un futuro que sea cambiable por constructor o setters
		this.fianza = 45000;
		this.hayGanador = false;
	}
	
	public void iniciarJuego() {
		turno.getJugadorActual().iniciarTurno();
	}

	private void construirTirada() {
		tiradaJuego.agregarDado(dadoJuego);
		tiradaJuego.agregarDado(dadoJuego);
	}
	
	public Jugador agregarJugador(String nombre){
		Jugador jugador = new Jugador(nombre, this);
		jugadores.add(jugador);
		return jugador;
	}
	
	
	//carga la maxima cantidad de jugadores con nombres predeterminados
	//este metodo es archirecontrasuperprovisorio
	public void cargarJugadores(){
		agregarJugador("Amarillo");
		agregarJugador("Rojo");
		agregarJugador("Azul");
	}
	
	public void avanzarTurno() {
		turno.getJugadorActual().terminarTurno();
		turno.avanzar();
		turno.getJugadorActual().iniciarTurno();
	}
	
	public Jugador getJugadorActual() {
		return turno.getJugadorActual();
	}
	
	public void establecerTurnosAlAzar() {
		Collections.shuffle(jugadores);
		this.turno.agregarJugadores(jugadores);
	}

	public TiradaDados getTiradaJuego() {
		return tiradaJuego;
	}

	public Casillero getCasilleroEnPosicion(Posicion posicion) {
		return tablero.getCasilleroEnPosicion(posicion);
	}



	public int getPrecioFianza() {
		return this.fianza;
	}

	public ArrayList<Jugador> getJugadores(){
		return new ArrayList<Jugador>(this.jugadores);
	}
	
	
	public void hacerPerder(Jugador jugador) {
		this.jugadores.remove(jugador);
		this.turno.eliminar(jugador);
		
		if(this.jugadores.size() == 1) terminarJuego();
	}
	
	
	private void terminarJuego() {
		this.hayGanador= true;
		
	}
	
	public boolean estaTerminado() {
		return this.hayGanador;
	}

	public int getEfectivoInicial() {
		return this.efectivoInicial;
	}

	public int getCantidadJugadores() {
		return this.jugadores.size();
	}
	
}
