package model.juego;

import java.util.Random;

public class Dado {
	
	private int primero;
	private int ultimo;

	public Dado(int primeroNuevo, int ultimoNuevo) {
		this.primero = primeroNuevo;
		this.ultimo = ultimoNuevo;
	}

	public int tirar() {
		Random rand = new Random();
		return (rand.nextInt((ultimo - primero) + 1) + primero);
	}
	
	
}
