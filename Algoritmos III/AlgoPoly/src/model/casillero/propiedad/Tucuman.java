package model.casillero.propiedad;

import model.espacio.Posicion;

public class Tucuman extends Barrio {
	
	public static final String nombreClase = "Tucuman"; 

	public Tucuman(Posicion posicionNueva) {
		super(nombreClase, 25000, posicionNueva);
		this.edificaciones = new Edificaciones(7000);
		this.alquilerSegunConstrucciones.put(0, 2500);
		this.alquilerSegunConstrucciones.put(1, 4500);	
	}

}
