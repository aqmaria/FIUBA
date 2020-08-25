package model.casillero.propiedad;

import java.util.Hashtable;

import model.espacio.Posicion;
import model.juego.Jugador;

public abstract class Compania extends Propiedad {
	

	protected GrupoCompanias grupo;
	protected Hashtable<Integer, Integer> multiplicadorSegunCompaniasDeGrupoAdquiridas;

	public Compania(String nombreNuevo, int PrecioVentaNuevo, Posicion posicionNueva) {
		super(nombreNuevo, PrecioVentaNuevo, posicionNueva);
		this.grupo = new GrupoCompanias();
		this.multiplicadorSegunCompaniasDeGrupoAdquiridas = new Hashtable<Integer, Integer>();
	}
	
	public void setState(PropiedadState companiaState) {
		this.state = companiaState;
	}
	
	public void setGrupo(GrupoCompanias nuevoGrupo) {
		this.grupo = nuevoGrupo;
	}
	
	public int getRenta(Jugador jugador) {
		int tiradaJugadorQueCayo = jugador.getUltimaTiradaDados();
		int multiplicador = multiplicadorSegunCompaniasDeGrupoAdquiridas.get(this.grupo.getCantidadPropiedadDe(this.propietario));
		
		return tiradaJugadorQueCayo * multiplicador;
	}
	
	public void resetearRenta() {
		return;
	}
	
	public int getPrecioVenta() {
		return this.precioVenta;
	}
}