package model.casillero.propiedad;

import model.espacio.Posicion;

public class SantaFe extends Barrio {
	
	public static final String nombreClase = "Santa Fe"; 

	public SantaFe(Posicion posicionNueva) {
		super(nombreClase, 15000, posicionNueva);
		this.edificaciones = new Edificaciones(4000);
		this.alquilerSegunConstrucciones.put(0, 1500);
		this.alquilerSegunConstrucciones.put(1, 3500);
	}
	


}
