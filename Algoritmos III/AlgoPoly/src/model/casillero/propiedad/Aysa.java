package model.casillero.propiedad;

import model.espacio.Posicion;

public class Aysa extends Compania {
	
	public static final String nombreClase = "Aysa"; 

	public Aysa(Posicion posicionNueva) {
		super(nombreClase, 30000, posicionNueva);
		
		this.multiplicadorSegunCompaniasDeGrupoAdquiridas.put(0, 0);
		this.multiplicadorSegunCompaniasDeGrupoAdquiridas.put(1, 300);
		this.multiplicadorSegunCompaniasDeGrupoAdquiridas.put(2, 500);
	}

}
