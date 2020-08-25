package model.command;

import model.casillero.propiedad.Barrio;

public class EdificarComando extends Comando{
	
	private Barrio barrio;
	public static String prefijoAccion = "Edificar en ";
	
	public EdificarComando(Barrio barrio) {
		super(prefijoAccion + barrio.getNombre());
		this.barrio = barrio;
	}
	
	@Override
	public void ejecutar() {
		barrio.construir();
	}

}
