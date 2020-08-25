package model.casillero.propiedad;

import excepciones.BarrioLimiteEdificacionesException;
import excepciones.JugadorEfectivoInsuficienteException;
import model.juego.Jugador;

public class Edificaciones {
	
	private int cantidad;
	private int precio;
	private int precioUltima;
	private int limite;
	private int precioVenta;

	public Edificaciones(int nuevoPrecio, int nuevoPrecioUltima, int nuevoLimite){
		this.precio = nuevoPrecio;
		this.precioUltima = nuevoPrecioUltima;
		this.cantidad = 0;
		this.limite = nuevoLimite;
		this.precioVenta = 0;
	}
	
	public Edificaciones(int nuevoPrecioUltima){
		this.precio = 0;
		this.precioUltima = nuevoPrecioUltima;
		this.cantidad = 0;
		this.limite = 1;
		this.precioVenta = 0;
	}
	
	public int getCantidad() {
		return this.cantidad;
	}
	
	public void construir(Jugador jugador) {
		if(cantidad == this.limite) throw new BarrioLimiteEdificacionesException();
		
		int monto = this.precio;
		
		if(cantidad == this.limite - 1) monto = this.precioUltima;
		
		if( jugador.getEfectivo() < monto) throw new JugadorEfectivoInsuficienteException();
		
		jugador.debitar(monto);

		this.cantidad += 1;
		
		this.precioVenta += monto;
	}
	
	public void destruir() {
		this.cantidad = 0;
	}
	
	//lo tuve que agregar
	public int getCantidadLimite() {
		return this.limite;
	} 
	
	public int getPrecioVenta() {
		return this.precioVenta;
	}
}
