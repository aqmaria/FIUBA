package model.casillero.propiedad;

import model.espacio.Posicion;

public class SaltaNorte extends Barrio {
	
	public static final String nombreClase = "Salta Norte"; 

	public SaltaNorte(Posicion posicionNueva) {
		super(nombreClase, 23000, posicionNueva);
		this.edificaciones = new Edificaciones(4500, 7500, 3);
		this.alquilerSegunConstrucciones.put(0, 2000);
		this.alquilerSegunConstrucciones.put(1, 3250);
		this.alquilerSegunConstrucciones.put(2, 3850);
		this.alquilerSegunConstrucciones.put(3, 5500);
	}
	


}
