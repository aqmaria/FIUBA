package model.casillero.propiedad;

import java.util.LinkedList;

import excepciones.BarrioDobleNoPuedeEdificarHotelSinHaberEdificadoMaximaCantidadDeCasasException;
import excepciones.BarrioDobleNoPuedeEdificarSiPropietarioTieneUnaDeLasDosPropiedadesException;

public class GrupoBarrios {
	
		
	private LinkedList<Barrio> grupo;
	
	public GrupoBarrios() {
		
		this.grupo = new LinkedList<Barrio>();
	}
	
	public void agregar(Barrio barrio) {
		grupo.add(barrio);
		barrio.setGrupo(this);
	}

	public boolean puedeEdificar(Barrio barrio) {

		boolean quiereEdificarHotel = barrio.getCantidadEdificaciones() == barrio.getLimiteEdificaciones() - 1;
		
		for(Barrio barrioGrupo : grupo) {
			boolean jugadorNoTieneTodoElGrupo = barrioGrupo.getPropietario()!= barrio.getPropietario();
			boolean noTieneMaximaCapacidadDeCasas = barrioGrupo.getCantidadEdificaciones() < barrio.getCantidadEdificaciones();
			
			if(jugadorNoTieneTodoElGrupo) throw new BarrioDobleNoPuedeEdificarSiPropietarioTieneUnaDeLasDosPropiedadesException() ;

			if(quiereEdificarHotel && noTieneMaximaCapacidadDeCasas) throw new BarrioDobleNoPuedeEdificarHotelSinHaberEdificadoMaximaCantidadDeCasasException(); 

		}
		return true;
	}
}
