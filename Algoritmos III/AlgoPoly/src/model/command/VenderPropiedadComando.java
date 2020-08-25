package model.command;

import model.casillero.propiedad.Propiedad;

public class VenderPropiedadComando extends Comando{
	
	private Propiedad propiedad;
	public static String prefijoAccion = "Vender ";
	
	public VenderPropiedadComando(Propiedad propiedadNueva) {
		super(prefijoAccion + propiedadNueva.getNombre());
		this.propiedad = propiedadNueva;
	}
	
	@Override
	public void ejecutar() {
		propiedad.vender();
	}

}
