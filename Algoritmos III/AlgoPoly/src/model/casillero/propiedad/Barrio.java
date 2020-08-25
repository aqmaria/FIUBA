package model.casillero.propiedad;


import java.util.Hashtable;

import model.espacio.Posicion;
import model.juego.Jugador;

public abstract class Barrio extends Propiedad {
	protected Edificaciones edificaciones;
	public GrupoBarrios grupo;
	
	protected Hashtable<Integer, Integer> alquilerSegunConstrucciones;
	
	public Barrio(String nombreNuevo, int PrecioVentaNuevo, Posicion posicionNueva) {
		super(nombreNuevo, PrecioVentaNuevo, posicionNueva);
		this.alquilerSegunConstrucciones = new Hashtable<Integer,Integer>();
		this.grupo = new GrupoBarrios();
	}
	
	public void setState(PropiedadState barrioState) {
		this.state = barrioState;
	}
	
	public int getCantidadEdificaciones() {
		return this.edificaciones.getCantidad();
	}
	
	public int getPrecioVenta() {
		return this.precioVenta + this.edificaciones.getPrecioVenta();
		
		//return this.precioVenta;
	}
	
	
	public void construir() {
		if(grupo.puedeEdificar(this))
			this.edificaciones.construir(this.propietario);
	}
	
	public void quitarConstrucciones() {
		this.edificaciones.destruir();
	}
	
	public int getRenta(Jugador jugador) {
		return alquilerSegunConstrucciones.get(edificaciones.getCantidad());
	}
	
	public void setGrupo(GrupoBarrios grupoNuevo) {
		this.grupo = grupoNuevo;
	}
	
	public int getLimiteEdificaciones() {
		return edificaciones.getCantidadLimite();
	}

	public void resetearRenta() {
		this.edificaciones.destruir();
	}
	
}
