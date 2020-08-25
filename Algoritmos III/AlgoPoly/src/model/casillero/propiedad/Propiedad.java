package model.casillero.propiedad;

import excepciones.NoPuedeVendersePropiedadSinDuenioException;
import model.casillero.Casillero;
import model.espacio.Posicion;
import model.juego.Jugador;

public abstract class Propiedad extends Casillero{
	protected PropiedadState state;
	protected int precioVenta;
	protected Jugador propietario;
	protected String nombre;
	
	public Propiedad(String nombreNuevo, int PrecioVentaNuevo, Posicion posicionNueva) {
		
		super(posicionNueva);
		nombre = nombreNuevo;
		this.propietario = null ;
		precioVenta = PrecioVentaNuevo;
		this.state = new PropiedadSinDuenioState();
	}
	
	
	public Jugador getPropietario(){
		return propietario;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setPropietario(Jugador jugador) {
		this.propietario= jugador;
		
	}
	
	public abstract int getPrecioVenta();
	
	public void setState(PropiedadState propiedadState) {
		this.state = propiedadState;
	}
	
	protected void accionar(Jugador jugador) {
		this.state.accionar(jugador, this ) ;
	}
	
	
	public void vender() {
		if(propietario == null) throw new NoPuedeVendersePropiedadSinDuenioException();
		
		int precioVentaConDescuento = (int)((double)this.getPrecioVenta() * 0.85);
		this.propietario.acreditar(precioVentaConDescuento);
		this.propietario.quitarPropiedad(this);
		this.resetearRenta();
		this.quitarPropietario();
		this.setState(new PropiedadSinDuenioState());
	}
	

	public abstract int getRenta(Jugador jugador);


	public abstract void resetearRenta();


	public void quitarPropietario() {
		this.propietario = null;
	}
	
}
