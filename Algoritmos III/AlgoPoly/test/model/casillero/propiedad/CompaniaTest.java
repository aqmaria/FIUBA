package model.casillero.propiedad;

import org.junit.Assert;
import org.junit.Test;

import model.casillero.propiedad.Barrio;
import model.casillero.propiedad.Compania;
import model.espacio.Posicion;
import model.juego.Juego;
import model.juego.Jugador;

public class CompaniaTest {
    private static final double DELTA = 1e-15;
	private static final int POS_TRENES=16;
	private static final int PRECIO_TRENES = 38000;
	private static final double PORCENTAJE_DESCUENTO = 0.15;
	
	@Test
	public void test_1JugadorDespuesDeVenderCompaniaNoLaTieneMasYSeLeAcreditaUn15PorCientoMenosDelValor() {
		Juego juego = new Juego();
		Jugador jugador = new Jugador("Putin Vladimir",juego);
		jugador.moverA(new Posicion(POS_TRENES));
		jugador.getAccionesPosibles().get("Comprar Trenes").ejecutar();

		Compania trenes = jugador.obtenerCompanias().get("Trenes");
		
		int efvoInicial = jugador.getEfectivo();
		trenes.vender();
	
		Assert.assertEquals( jugador.getEfectivo(),efvoInicial + PRECIO_TRENES - (PRECIO_TRENES*PORCENTAJE_DESCUENTO),DELTA );
		
		Assert.assertNotEquals(jugador, trenes.getPropietario());
		
		Assert.assertNull( jugador.getPropiedad("Trenes"));
		
	}
	
	public void test_2JugadorCaeEnUnaCompaniaQueFueVendidaAnteriormenteYSeHaceDuenio() {
		Juego juego = new Juego();
		Jugador jugador = new Jugador("pepe",juego);
		jugador.moverA(new Posicion(POS_TRENES));
		Compania trenes = jugador.obtenerCompanias().get("Trenes");
		trenes.vender();
		Jugador jugador2 = new Jugador("pepex",juego);
		jugador2.moverA(new Posicion(POS_TRENES));
		Assert.assertEquals( jugador2, trenes.getPropietario() );
		
	}

}
