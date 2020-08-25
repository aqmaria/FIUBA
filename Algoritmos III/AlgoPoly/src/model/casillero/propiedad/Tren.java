package model.casillero.propiedad;

import model.espacio.Posicion;

public class Tren extends Compania {
	
	public static final String nombreClase = "Trenes"; 

	public Tren(Posicion posicionNueva) {
		super(nombreClase, 38000, posicionNueva);
		
		this.multiplicadorSegunCompaniasDeGrupoAdquiridas.put(0, 0);
		this.multiplicadorSegunCompaniasDeGrupoAdquiridas.put(1, 450);
		this.multiplicadorSegunCompaniasDeGrupoAdquiridas.put(2, 800);
	}



}
