package model.casillero.propiedad;

import model.espacio.Posicion;

public class CordobaNorte extends Barrio {
	
	public static final String nombreClase = "Cordoba Norte"; 

	public CordobaNorte(Posicion posicionNueva) {
		super(nombreClase, 20000, posicionNueva);
		this.edificaciones = new Edificaciones(2200, 3500, 3);
		this.alquilerSegunConstrucciones.put(0, 1300);
		this.alquilerSegunConstrucciones.put(1, 1800);
		this.alquilerSegunConstrucciones.put(2, 2900);
		this.alquilerSegunConstrucciones.put(3, 3500);
	}
	
}
