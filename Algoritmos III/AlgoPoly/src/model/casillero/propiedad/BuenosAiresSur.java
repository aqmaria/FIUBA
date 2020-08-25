package model.casillero.propiedad;

import model.espacio.Posicion;

public class BuenosAiresSur extends Barrio {
	
	public static final String nombreClase = "Buenos Aires Sur"; 
	
	public BuenosAiresSur(Posicion posicionNueva) {
		
		super(nombreClase,20000, posicionNueva);
		this.edificaciones = new Edificaciones(5000, 8000, 3);
		this.alquilerSegunConstrucciones.put(0, 2000);
		this.alquilerSegunConstrucciones.put(1, 3000);
		this.alquilerSegunConstrucciones.put(2, 3500);
		this.alquilerSegunConstrucciones.put(3, 5000);
	}
	
}
