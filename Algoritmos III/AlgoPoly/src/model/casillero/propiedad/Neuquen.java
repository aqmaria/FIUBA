package model.casillero.propiedad;

import model.espacio.Posicion;

public class Neuquen extends Barrio {
	
	public static final String nombreClase = "Neuquen"; 
	
	public Neuquen(Posicion posicionNueva) {
		super(nombreClase, 17000, posicionNueva);
		this.edificaciones = new Edificaciones(4800);
		this.alquilerSegunConstrucciones.put(0, 1800);
		this.alquilerSegunConstrucciones.put(1, 3800);
	}
	

}
