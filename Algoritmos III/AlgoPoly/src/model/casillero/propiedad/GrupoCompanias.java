package model.casillero.propiedad;

import java.util.LinkedList;
import java.util.List;

import model.juego.Jugador;

public class GrupoCompanias{

	private List<Compania> grupo;
	
	public GrupoCompanias() {
		this.grupo = new LinkedList<Compania>();
	}
	
	public void agregar(Compania compania) {
		this.grupo.add(compania);
		compania.setGrupo(this);
	}
	
	public int getCantidadPropiedadDe(Jugador jugador) {
		int cant = 0;
		for(Compania compania : grupo) {
			if(compania.getPropietario() == jugador) cant++;
		}
		
		return cant;
	}
}
