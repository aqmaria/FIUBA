package model.casillero.propiedad;

import org.junit.Assert;
import org.junit.Test;

import excepciones.BarrioLimiteEdificacionesException;
import model.casillero.propiedad.Barrio;
import model.espacio.Posicion;
import model.juego.Juego;
import model.juego.Jugador;

public class BarrioTest {
	private static final int POS_TUCUMAN = 19;
	private static final int POS_BSASSUR = 2;
	private static final int POS_BSASNORTE = 4;
	
	private static final double PORCENTAJE_DESCUENTO = 0.15;
	
	private static final int PRECIO_BSASSUR = 20000;
	private static final int PRECIO_TUCUMAN = 25000;
	private static final int PRECIO_CASA_TUCUMAN = 7000;
	
	private static final int PRECIO_CASA_BSASSUR = 5000;

	
    private static final double DELTA = 1e-15;

	
	@Test(expected = BarrioLimiteEdificacionesException.class )
	public void test_1BarrioQuererEdificarMasDelLimiteLanzaExcepcion() {
		Juego juego = new Juego();
		Jugador jugador = new Jugador("pp",juego);
		jugador.moverA(new Posicion(POS_TUCUMAN));
		jugador.getAccionesPosibles().get("Comprar Tucuman").ejecutar();
		
		Barrio tucuman = jugador.obtenerBarrios().get("Tucuman");
		tucuman.construir();
		tucuman.construir();
	}
	
	@Test(expected = BarrioLimiteEdificacionesException.class )
	public void test_2BarrioQuererEdificarMasDelLimiteLanzaExcepcion() {
		Juego juego = new Juego();
		Jugador jugador = new Jugador("Putin Vladimir",juego);
		jugador.moverA(new Posicion(POS_BSASSUR));
		jugador.getAccionesPosibles().get("Comprar Buenos Aires Sur").ejecutar();
		Barrio bsassur = jugador.obtenerBarrios().get("Buenos Aires Sur");
		jugador.moverA(new Posicion(POS_BSASNORTE));
		jugador.getAccionesPosibles().get("Comprar Buenos Aires Norte").ejecutar();
		Barrio bsasnorte = jugador.obtenerBarrios().get("Buenos Aires Norte");

		//construye 1 casa
		bsassur.construir();
		bsasnorte.construir();
		//construye 2
		bsassur.construir();
		bsasnorte.construir();
		//construye hotel
		bsasnorte.construir();
		bsassur.construir();
		//lanza excepcion
		bsasnorte.construir();
		bsassur.construir();

	}
	
	@Test
	public void test_3JugadorDespuesDeVenderBarrioSinConstruccionesNoLoTieneMasYSeLeAcreditaUn15PorCientoMenosDelValor() {
		Juego juego = new Juego();
		Jugador jugador = new Jugador(":(",juego);
		jugador.moverA(new Posicion(POS_BSASSUR));
		jugador.getAccionesPosibles().get("Comprar Buenos Aires Sur").ejecutar();
		Barrio bsassur = jugador.obtenerBarrios().get("Buenos Aires Sur");
		int efvoInicial = jugador.getEfectivo();
		bsassur.vender();
	
		Assert.assertEquals( jugador.getEfectivo(),efvoInicial + PRECIO_BSASSUR - (PRECIO_BSASSUR*PORCENTAJE_DESCUENTO),DELTA );
		
		Assert.assertNotEquals(jugador, bsassur.getPropietario());
		
		Assert.assertNull( jugador.getPropiedad("Buenos Aires Sur"));
		
	}
	
	@Test
	public void test_4JugadorDespuesDeVenderBarrioCon1CasaNoLoTieneMasYSeLeAcreditaUn15PorCientoMenosDelValorTotalConConstrucciones() {
		Juego juego = new Juego();
		Jugador jugador = new Jugador("pepe",juego);
		jugador.moverA(new Posicion(POS_TUCUMAN));
		jugador.getAccionesPosibles().get("Comprar Tucuman").ejecutar();
		Barrio tucuman = jugador.obtenerBarrios().get("Tucuman");
		
		tucuman.construir();

		int efvoInicial = jugador.getEfectivo();

		tucuman.vender();
	
		Assert.assertEquals( jugador.getEfectivo(),efvoInicial + PRECIO_TUCUMAN + PRECIO_CASA_TUCUMAN - ( (PRECIO_TUCUMAN + PRECIO_CASA_TUCUMAN )* PORCENTAJE_DESCUENTO),DELTA );
		
		Assert.assertNotEquals(jugador, tucuman.getPropietario());
		
		Assert.assertNull( jugador.getPropiedad("Tucuman"));
		
	}
	
	@Test
	public void test_5JugadorDespuesDeVender1BarrioDobleCon2CasasNoLoTieneMasYSeLeAcreditaUn15PorCientoMenosDelValorTotalConConstrucciones() {
		Juego juego = new Juego();
		Jugador jugador = new Jugador("pepe",juego);
		jugador.moverA(new Posicion(POS_BSASNORTE));
		jugador.getAccionesPosibles().get("Comprar Buenos Aires Norte").ejecutar();
		jugador.moverA(new Posicion(POS_BSASSUR));
		jugador.getAccionesPosibles().get("Comprar Buenos Aires Sur").ejecutar();
		Barrio bsassur = jugador.obtenerBarrios().get("Buenos Aires Sur");

		bsassur.construir();
		bsassur.construir();
		
		int efvoInicial = jugador.getEfectivo();
		
		bsassur.vender();
	
		Assert.assertEquals( jugador.getEfectivo(),efvoInicial + PRECIO_BSASSUR + (PRECIO_CASA_BSASSUR*2) - ( (PRECIO_BSASSUR + ( PRECIO_CASA_BSASSUR*2 ) )* PORCENTAJE_DESCUENTO),DELTA );
		
		Assert.assertNotEquals(jugador, bsassur.getPropietario());
		
		Assert.assertNull( jugador.getPropiedad("Buenos Aires Sur"));
		
	}
	
	@Test
	public void test_6DespuesDeQueJugadorVendeBarrioCon1ConstruccionEstaDesaparece() {
		Juego juego = new Juego();
		Jugador jugador = new Jugador("pepe",juego);
		jugador.moverA(new Posicion(POS_TUCUMAN));
		jugador.getAccionesPosibles().get("Comprar Tucuman").ejecutar();
		Barrio tucuman = jugador.obtenerBarrios().get("Tucuman");
		
		tucuman.construir();		
		tucuman.vender();
	
		Assert.assertEquals( tucuman.getCantidadEdificaciones(), 0 );
		
	}
	
	@Test
	public void test_7JugadorCaeEnUnBarrioQueFueVendidoAnteriormenteYSeHaceDuenio() {
		Juego juego = new Juego();
		Jugador jugador = new Jugador("pepe",juego);
		jugador.moverA(new Posicion(POS_TUCUMAN));
		jugador.getAccionesPosibles().get("Comprar Tucuman").ejecutar();
		Barrio tucuman = jugador.obtenerBarrios().get("Tucuman");
		tucuman.vender();
		Jugador jugador2 = new Jugador("pepex",juego);
		jugador2.moverA(new Posicion(POS_TUCUMAN));
		jugador2.getAccionesPosibles().get("Comprar Tucuman").ejecutar();
		Assert.assertEquals( jugador2, tucuman.getPropietario() );
		
	}

	
	@Test
	public void test_6DespuesDeQueJugadorVendeBarrioConHotelEsteDesaparece() {
		Juego juego = new Juego();
		Jugador jugador = new Jugador("pepe",juego);
		jugador.moverA(new Posicion(POS_BSASNORTE));
		jugador.moverA(new Posicion(POS_BSASSUR));
		jugador.getAccionesPosibles().get("Comprar Buenos Aires Norte").ejecutar();
		jugador.getAccionesPosibles().get("Comprar Buenos Aires Sur").ejecutar();
		Barrio bsassur = jugador.obtenerBarrios().get("Buenos Aires Sur");
		Barrio bsasnor = jugador.obtenerBarrios().get("Buenos Aires Norte");
		
		bsassur.construir();
		bsassur.construir();
		
		bsasnor.construir();
		bsasnor.construir();
		
		bsassur.construir();
		
		
		bsassur.vender();
	
		Assert.assertEquals( bsassur.getCantidadEdificaciones(), 0 );
		
	}
	
	
}
