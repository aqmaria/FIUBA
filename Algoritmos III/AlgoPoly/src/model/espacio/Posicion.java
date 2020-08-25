package model.espacio;

/*
	Representa una posicion en tablero, solo puede estar entre 0 y 19. Si se intenta avanzar mas alla del 19, volvera a empezar en 0
	y si retrocede mas alla de 0 seguira por el 19. Si se crea un posicion "n", es lo mismo que avanzar n posiciones desde el 0.
*/

public class Posicion {
	
	private static final int tamanioTablero = 20;
	
	private int valor;
	
	public Posicion(int valorNuevo){
		this();
		this.avanzar(valorNuevo);
	}

	public Posicion() {
		valor = 0;
	}

	public int getValor() {
		return valor;
	}

	public void avanzar(int cant) {
		int tmp = valor;
		tmp = (tmp + cant) % tamanioTablero;
		if(tmp < 0)
			tmp = tamanioTablero + tmp;
		
		valor = tmp;
	}

	public void retroceder(int cant) {
		this.avanzar(-cant);
	}
	
	
}
