package model.casillero.propiedad;

import model.espacio.Posicion;

public class Subte extends Compania {
	
	public static final String nombreClase = "Subtes"; 

	public Subte(Posicion posicionNueva) {
		super(nombreClase, 40000, posicionNueva);
		
		this.multiplicadorSegunCompaniasDeGrupoAdquiridas.put(0, 0);
		this.multiplicadorSegunCompaniasDeGrupoAdquiridas.put(1, 600);
		this.multiplicadorSegunCompaniasDeGrupoAdquiridas.put(2, 1100);
	}



}
