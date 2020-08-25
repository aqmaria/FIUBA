package model.casillero.propiedad;

import model.espacio.Posicion;

public class Edesur extends Compania {
	
	public static final String nombreClase = "Edesur"; 

	public Edesur(Posicion posicionNueva) {
		super(nombreClase, 35000, posicionNueva);
		
		this.multiplicadorSegunCompaniasDeGrupoAdquiridas.put(0, 0);
		this.multiplicadorSegunCompaniasDeGrupoAdquiridas.put(1, 500);
		this.multiplicadorSegunCompaniasDeGrupoAdquiridas.put(2, 1000);
	}


}
