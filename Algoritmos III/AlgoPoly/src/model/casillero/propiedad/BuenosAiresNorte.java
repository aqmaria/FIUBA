package model.casillero.propiedad;

import model.espacio.Posicion;

public class BuenosAiresNorte extends Barrio {
	
	public static final String nombreClase = "Buenos Aires Norte"; 
	
	public BuenosAiresNorte(Posicion posicionNueva) {
		super(nombreClase, 25000, posicionNueva);
		this.edificaciones = new Edificaciones(5500, 9000, 3);
		this.alquilerSegunConstrucciones.put(0, 2500);
		this.alquilerSegunConstrucciones.put(1, 3500);
		this.alquilerSegunConstrucciones.put(2, 4000);
		this.alquilerSegunConstrucciones.put(3, 6000);
	}

}
