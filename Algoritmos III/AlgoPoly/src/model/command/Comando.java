package model.command;

public abstract class Comando {
	
	protected String nombre;
	
	protected Comando(String nombreNuevo) {
		nombre = nombreNuevo;
	}
	
	public abstract void ejecutar();

	public String getNombre() {
		return nombre;
	}
}
