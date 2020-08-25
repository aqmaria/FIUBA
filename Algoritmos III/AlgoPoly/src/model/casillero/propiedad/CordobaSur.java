package model.casillero.propiedad;

import model.espacio.Posicion;

public class CordobaSur extends Barrio {
	
	public static final String nombreClase = "Cordoba Sur"; 

	public CordobaSur(Posicion posicionNueva) {
		super(nombreClase, 18000, posicionNueva);
		this.edificaciones = new Edificaciones(2000, 3500, 3);
		this.alquilerSegunConstrucciones.put(0, 1000);
		this.alquilerSegunConstrucciones.put(1, 1500);
		this.alquilerSegunConstrucciones.put(2, 2500);
		this.alquilerSegunConstrucciones.put(3, 3000);
	}
	


}
