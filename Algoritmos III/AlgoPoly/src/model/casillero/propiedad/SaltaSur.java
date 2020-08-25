package model.casillero.propiedad;

import model.espacio.Posicion;

public class SaltaSur extends Barrio {
	
	public static final String nombreClase = "Salta Sur"; 

	public SaltaSur(Posicion posicionNueva) {
		super(nombreClase, 23000, posicionNueva);
		this.edificaciones = new Edificaciones(4500, 7500, 3);
		this.alquilerSegunConstrucciones.put(0, 2000);
		this.alquilerSegunConstrucciones.put(1, 3250);
		this.alquilerSegunConstrucciones.put(2, 3850);
		this.alquilerSegunConstrucciones.put(3, 5500);
	}
	


}
