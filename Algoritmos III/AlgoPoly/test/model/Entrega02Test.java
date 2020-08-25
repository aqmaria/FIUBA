package model;

import org.junit.Assert;
import org.junit.Test;

import excepciones.BarrioDobleNoPuedeEdificarHotelSinHaberEdificadoMaximaCantidadDeCasasException;
import model.casillero.propiedad.Aysa;
import model.casillero.propiedad.Barrio;
import model.casillero.propiedad.BuenosAiresNorte;
import model.casillero.propiedad.BuenosAiresSur;
import model.casillero.propiedad.Compania;
import model.casillero.propiedad.CordobaNorte;
import model.casillero.propiedad.CordobaSur;
import model.casillero.propiedad.Edesur;
import model.casillero.propiedad.Neuquen;
import model.casillero.propiedad.SaltaNorte;
import model.casillero.propiedad.SaltaSur;
import model.casillero.propiedad.SantaFe;
import model.casillero.propiedad.Subte;
import model.casillero.propiedad.Tren;
import model.casillero.propiedad.Tucuman;
import model.command.ComprarPropiedadComando;
import model.espacio.Posicion;
import model.juego.Juego;
import model.juego.Jugador;

public class Entrega02Test {
	
	private static final int POS_BSASSUR = 2;
	private static final int POS_BSASNORTE = 4;
	private static final int POS_CORDOBASUR = 6;
	private static final int POS_CORDOBANORTE = 9;
	private static final int POS_SANTAFE = 11;
	private static final int POS_SALTANORTE = 13;
	private static final int POS_SALTASUR = 14;
	private static final int POS_NEUQUEN = 17;
	private static final int POS_TUCUMAN = 19;
	
	private static final int PRECIO_BSASSUR = 20000;
	private static final int PRECIO_BSASNORTE = 25000;
	private static final int PRECIO_CORDOBASUR = 18000;
	private static final int PRECIO_CORDOBANORTE = 20000;
	private static final int PRECIO_SANTAFE = 15000;
	private static final int PRECIO_SALTANORTE = 23000;
	private static final int PRECIO_SALTASUR = 23000;
	private static final int PRECIO_NEUQUEN = 17000;
	private static final int PRECIO_TUCUMAN = 25000;
	
	private static final int PRECIO_CASA_BSASSUR = 5000;
	private static final int PRECIO_CASA_BSASNORTE = 5500;
	private static final int PRECIO_CASA_SANTAFE = 4000;

	private static final int PRECIO_HOTEL_BSASSUR = 8000;
	private static final int PRECIO_HOTEL_BSASNORTE = 9000;

	
	
	private static final int PRECIO_ALQUILERHOTEL_BSASSUR = 5000;
	private static final int PRECIO_ALQUILERHOTEL_BSASNORTE = 6000;
	private static final int PRECIO_ALQUILERHOTEL_SALTASUR = 5500;
	private static final int PRECIO_ALQUILERHOTEL_SALTANORTE = 5500;
	private static final int PRECIO_ALQUILERHOTEL_CORDOBASUR = 3000;
	private static final int PRECIO_ALQUILERHOTEL_CORDOBANORTE = 3500;
	
	
	
	private static final int PRECIO_ALQUILER1CASA_BSASSUR=3000;
	private static final int PRECIO_ALQUILER1CASA_BSASNORTE=3500;
	private static final int PRECIO_ALQUILER1CASA_SALTASUR=3250;
	private static final int PRECIO_ALQUILER1CASA_SALTANORTE=3250;
	private static final int PRECIO_ALQUILER1CASA_SANTAFE=3500;
	private static final int PRECIO_ALQUILER1CASA_TUCUMAN=4500;
	private static final int PRECIO_ALQUILER1CASA_NEUQUEN=3800;
	private static final int PRECIO_ALQUILER1CASA_CORDOBASUR=1500;
	private static final int PRECIO_ALQUILER1CASA_CORDOBANORTE=1800;
	
	private static final int PRECIO_ALQUILER2CASA_BSASSUR=3500;
	private static final int PRECIO_ALQUILER2CASA_BSASNORTE=4000;
	private static final int PRECIO_ALQUILER2CASA_SALTASUR=3850;
	private static final int PRECIO_ALQUILER2CASA_SALTANORTE=3850;
	private static final int PRECIO_ALQUILERCASA_SANTAFE=3500;
	private static final int PRECIO_ALQUILER2CASA_CORDOBASUR=2500;
	private static final int PRECIO_ALQUILER2CASA_CORDOBANORTE=2900;
	
	private static final int POS_TRENES=16;
	private static final int POS_SUBTE=8;
	private static final int POS_EDESUR=3;
	private static final int POS_AYSA=12;
	
	private static final int POS_IMP_AL_LUJO = 10;
	
	
	/*
	 * 		TEST 01
	 */
	
	@Test
	public void test01_CompraBarrioSinDuenioReduceEfectivoAJugador_BuenosAiresSur() {
		Juego juego = new Juego();
		Jugador jugador = new Jugador("Pepe",juego);
		
		int saldoInicial = jugador.getEfectivo();
		
		jugador.moverA(new Posicion(POS_BSASSUR));
		
		jugador.getAccionesPosibles().get(ComprarPropiedadComando.prefijoAccion + BuenosAiresSur.nombreClase).ejecutar();

		Assert.assertEquals(PRECIO_BSASSUR, saldoInicial - jugador.getEfectivo());
	}
	
	@Test
	public void test01_CompraBarrioSinDuenioReduceEfectivoAJugador_BuenosAiresNorte() {
		Juego juego = new Juego();
		Jugador jugador = new Jugador("Pepe",juego);
		
		int saldoInicial = jugador.getEfectivo();
		
		jugador.moverA(new Posicion(POS_BSASNORTE));
		
		jugador.getAccionesPosibles().get(ComprarPropiedadComando.prefijoAccion + BuenosAiresNorte.nombreClase).ejecutar();

		Assert.assertEquals(PRECIO_BSASNORTE, saldoInicial - jugador.getEfectivo());
	}
	
	@Test
	public void test01_CompraBarrioSinDuenioReduceEfectivoAJugador_CordobaSur() {
		Juego juego = new Juego();
		Jugador jugador = new Jugador("Pepe",juego);
		
		int saldoInicial = jugador.getEfectivo();
		
		jugador.moverA(new Posicion(POS_CORDOBASUR));
		
		jugador.getAccionesPosibles().get(ComprarPropiedadComando.prefijoAccion + CordobaSur.nombreClase).ejecutar();

		Assert.assertEquals(PRECIO_CORDOBASUR, saldoInicial - jugador.getEfectivo());
	}
	
	@Test
	public void test01_CompraBarrioSinDuenioReduceEfectivoAJugador_CordobaNorte() {
		Juego juego = new Juego();
		Jugador jugador = new Jugador("Pepe",juego);
		
		int saldoInicial = jugador.getEfectivo();
		
		jugador.moverA(new Posicion(POS_CORDOBANORTE));
		
		jugador.getAccionesPosibles().get(ComprarPropiedadComando.prefijoAccion + CordobaNorte.nombreClase).ejecutar();

		Assert.assertEquals(PRECIO_CORDOBANORTE, saldoInicial - jugador.getEfectivo());
	}
	
	@Test
	public void test01_CompraBarrioSinDuenioReduceEfectivoAJugador_SantaFe() {
		Juego juego = new Juego();
		Jugador jugador = new Jugador("Pepe",juego);
		
		int saldoInicial = jugador.getEfectivo();
		
		jugador.moverA(new Posicion(POS_SANTAFE));
		
		jugador.getAccionesPosibles().get(ComprarPropiedadComando.prefijoAccion + SantaFe.nombreClase).ejecutar();

		Assert.assertEquals(PRECIO_SANTAFE, saldoInicial - jugador.getEfectivo());
	}
	
	@Test
	public void test01_CompraBarrioSinDuenioReduceEfectivoAJugador_SaltaNorte() {
		Juego juego = new Juego();
		Jugador jugador = new Jugador("Pepe",juego);
		
		int saldoInicial = jugador.getEfectivo();
		
		jugador.moverA(new Posicion(POS_SALTANORTE));
		
		jugador.getAccionesPosibles().get(ComprarPropiedadComando.prefijoAccion + SaltaNorte.nombreClase).ejecutar();

		Assert.assertEquals(PRECIO_SALTANORTE, saldoInicial - jugador.getEfectivo());
	}
	
	@Test
	public void test01_CompraBarrioSinDuenioReduceEfectivoAJugador_SaltaSur() {
		Juego juego = new Juego();
		Jugador jugador = new Jugador("Pepe",juego);
		
		int saldoInicial = jugador.getEfectivo();
		
		jugador.moverA(new Posicion(POS_SALTASUR));
		
		jugador.getAccionesPosibles().get(ComprarPropiedadComando.prefijoAccion + SaltaSur.nombreClase).ejecutar();

		Assert.assertEquals(PRECIO_SALTASUR, saldoInicial - jugador.getEfectivo());
	}
	
	@Test
	public void test01_CompraBarrioSinDuenioReduceEfectivoAJugador_Neuquen() {
		Juego juego = new Juego();
		Jugador jugador = new Jugador("Pepe",juego);
		
		int saldoInicial = jugador.getEfectivo();
		
		jugador.moverA(new Posicion(POS_NEUQUEN));
		
		jugador.getAccionesPosibles().get(ComprarPropiedadComando.prefijoAccion + Neuquen.nombreClase).ejecutar();

		Assert.assertEquals(PRECIO_NEUQUEN, saldoInicial - jugador.getEfectivo());
	}
	
	@Test
	public void test01_CompraBarrioSinDuenioReduceEfectivoAJugador_Tucuman() {
		Juego juego = new Juego();
		Jugador jugador = new Jugador("Pepe",juego);
		
		int saldoInicial = jugador.getEfectivo();
		
		jugador.moverA(new Posicion(POS_TUCUMAN));
		
		jugador.getAccionesPosibles().get(ComprarPropiedadComando.prefijoAccion + Tucuman.nombreClase).ejecutar();

		Assert.assertEquals(PRECIO_TUCUMAN, saldoInicial - jugador.getEfectivo());
	}
	
	/*
	 * 		TEST 02
	 */
	
	@Test
	public void test02_Barrio_JugadorTieneBsAsSurYNorte_EdificarUnaCasaDecrementaEfectivoEn5000() {
		//solo decrementa 5000 en bsassur
		
		Juego juego = new Juego();
		Jugador jugador = new Jugador("Putin Vladimir",juego);
		
		jugador.moverA(new Posicion(POS_BSASSUR));
		
		jugador.getAccionesPosibles().get(ComprarPropiedadComando.prefijoAccion + BuenosAiresSur.nombreClase).ejecutar();

		jugador.moverA(new Posicion(POS_BSASNORTE));
		
		jugador.getAccionesPosibles().get(ComprarPropiedadComando.prefijoAccion + BuenosAiresNorte.nombreClase).ejecutar();

		int saldoInicial = jugador.getEfectivo();
		
		Barrio bsassur = jugador.obtenerBarrios().get("Buenos Aires Sur");
		Barrio bsasnorte = jugador.obtenerBarrios().get("Buenos Aires Norte");
		
		bsassur.construir();
		Assert.assertEquals(saldoInicial - PRECIO_CASA_BSASSUR, jugador.getEfectivo());
		
		saldoInicial = jugador.getEfectivo();
		bsasnorte.construir();
		Assert.assertEquals(saldoInicial - PRECIO_CASA_BSASNORTE, jugador.getEfectivo());
	}
	
	@Test
	public void test03_Barrio_JugadorTieneBsAsSurYNorteConUnaCasaEnCadaBarrio_EfectivoContrincanteSeReduceEn3000() {
		//decrementa en 3000 solo para bsassur
		Juego juego = new Juego();
		Jugador jugador = new Jugador("Putin Vladimir",juego);
		
		jugador.moverA(new Posicion(POS_BSASSUR));
		
		jugador.getAccionesPosibles().get(ComprarPropiedadComando.prefijoAccion + BuenosAiresSur.nombreClase).ejecutar();

		jugador.moverA(new Posicion(POS_BSASNORTE));
		
		jugador.getAccionesPosibles().get(ComprarPropiedadComando.prefijoAccion + BuenosAiresNorte.nombreClase).ejecutar();

		Barrio bsassur = jugador.obtenerBarrios().get("Buenos Aires Sur");
		bsassur.construir();
		Barrio bsasnorte = jugador.obtenerBarrios().get("Buenos Aires Norte");
		bsasnorte.construir();
		
		Jugador jugador2 = new Jugador("ppp", juego);
		int saldoInicialJugador2 = jugador2.getEfectivo();
		jugador2.moverA(bsassur.getPosicion());
		Assert.assertEquals( jugador2.getEfectivo(), saldoInicialJugador2 -PRECIO_ALQUILER1CASA_BSASSUR );
		saldoInicialJugador2 = jugador2.getEfectivo();
		jugador2.moverA(bsasnorte.getPosicion());
		Assert.assertEquals( jugador2.getEfectivo(), saldoInicialJugador2 - PRECIO_ALQUILER1CASA_BSASNORTE );
	}
	
	@Test
	public void test04_Barrio_JugadorTieneBsAsSurYNorteConDosCasasEnCadaBarrio_EfectivoContrincanteSeReduceEn3500() {
		Juego juego = new Juego();
		Jugador jugador = new Jugador("Putin Vladimir",juego);
		
		jugador.moverA(new Posicion(POS_BSASSUR));
		
		jugador.getAccionesPosibles().get(ComprarPropiedadComando.prefijoAccion + BuenosAiresSur.nombreClase).ejecutar();

		jugador.moverA(new Posicion(POS_BSASNORTE));
		
		jugador.getAccionesPosibles().get(ComprarPropiedadComando.prefijoAccion + BuenosAiresNorte.nombreClase).ejecutar();
		
		Barrio bsassur = jugador.obtenerBarrios().get(BuenosAiresSur.nombreClase);
		Barrio bsasnorte = jugador.obtenerBarrios().get(BuenosAiresNorte.nombreClase);
		
		bsassur.construir();
		bsasnorte.construir();
		bsassur.construir();
		bsasnorte.construir();
		
		Jugador jugador2 = new Jugador("ppp", juego);
		int saldoInicialJugador2 = jugador2.getEfectivo();
		
		jugador2.moverA(bsassur.getPosicion());
		Assert.assertEquals( jugador2.getEfectivo(), saldoInicialJugador2 -PRECIO_ALQUILER2CASA_BSASSUR );
		
		saldoInicialJugador2 = jugador2.getEfectivo();
		jugador2.moverA(bsasnorte.getPosicion());
		Assert.assertEquals( jugador2.getEfectivo(), saldoInicialJugador2 - PRECIO_ALQUILER2CASA_BSASNORTE );		
	}
	
	@Test(expected = BarrioDobleNoPuedeEdificarHotelSinHaberEdificadoMaximaCantidadDeCasasException.class)
	public void test05_Barrio_JugadorSinMaximaCapacidadDeCasasEnBarrioDobleNoPuedeEdificarHotel() {
		
		Juego juego = new Juego();
		Jugador jugador = new Jugador("Putin Vladimir",juego);

		jugador.moverA(new Posicion(POS_BSASSUR));
		
		jugador.getAccionesPosibles().get(ComprarPropiedadComando.prefijoAccion + BuenosAiresSur.nombreClase).ejecutar();

		jugador.moverA(new Posicion(POS_BSASNORTE));
		
		jugador.getAccionesPosibles().get(ComprarPropiedadComando.prefijoAccion + BuenosAiresNorte.nombreClase).ejecutar();
		
		Barrio bsassur = jugador.obtenerBarrios().get(BuenosAiresSur.nombreClase);
		Barrio bsasnorte = jugador.obtenerBarrios().get(BuenosAiresNorte.nombreClase);
		
		bsasnorte.construir();
		bsasnorte.construir();
		bsassur.construir();
		int efectivoInicialJugador = jugador.getEfectivo();
		bsasnorte.construir();
		Assert.assertEquals( jugador.getEfectivo(), efectivoInicialJugador);		


	}
	
	@Test
	public void test06_Barrio_JugadorTieneBsAsSurYNorteConDosCasasEnCadaBarrio_EdificarHotelDecrementaSuEfectivoEn8000() {
		//decrementa 8000 en bsassur
		Juego juego = new Juego();
		Jugador jugador = new Jugador("Putin Vladimir",juego);
		
		jugador.moverA(new Posicion(POS_BSASSUR));
		
		jugador.getAccionesPosibles().get(ComprarPropiedadComando.prefijoAccion + BuenosAiresSur.nombreClase).ejecutar();

		jugador.moverA(new Posicion(POS_BSASNORTE));
		
		jugador.getAccionesPosibles().get(ComprarPropiedadComando.prefijoAccion + BuenosAiresNorte.nombreClase).ejecutar();
		
		Barrio bsassur = jugador.obtenerBarrios().get(BuenosAiresSur.nombreClase);
		Barrio bsasnorte = jugador.obtenerBarrios().get(BuenosAiresNorte.nombreClase);
		
		bsassur.construir();
		bsasnorte.construir();
		
		bsassur.construir();
		bsasnorte.construir();
		
		int efectivoInicialJugador = jugador.getEfectivo();
		bsassur.construir();
		
		Assert.assertEquals( jugador.getEfectivo(), efectivoInicialJugador - PRECIO_HOTEL_BSASSUR);
		efectivoInicialJugador = jugador.getEfectivo();
		bsasnorte.construir();
		Assert.assertEquals( jugador.getEfectivo(), efectivoInicialJugador - PRECIO_HOTEL_BSASNORTE);

	}
	
	@Test
	public void test07_Barrio_JugadorTieneBsAsSurYNorteConDosCasasEnCadaBarrioYunHotel_ReduceEfectivoContrincanteEn5000() {
		//reduce 5000 solo en bsassur
		Juego juego = new Juego();
		Jugador jugador = new Jugador("Putin Vladimir",juego);
		
		jugador.moverA(new Posicion(POS_BSASSUR));
		
		jugador.getAccionesPosibles().get(ComprarPropiedadComando.prefijoAccion + BuenosAiresSur.nombreClase).ejecutar();

		jugador.moverA(new Posicion(POS_BSASNORTE));
		
		jugador.getAccionesPosibles().get(ComprarPropiedadComando.prefijoAccion + BuenosAiresNorte.nombreClase).ejecutar();
		
		Barrio bsassur = jugador.obtenerBarrios().get(BuenosAiresSur.nombreClase);
		Barrio bsasnorte = jugador.obtenerBarrios().get(BuenosAiresNorte.nombreClase);
		
		bsassur.construir();
		bsasnorte.construir();
		bsassur.construir();
		bsasnorte.construir();
		
		bsassur.construir();
		bsasnorte.construir();

		
		Jugador jugador2 = new Jugador("pp",juego);
		int saldoInicialJugador2 = jugador2.getEfectivo();
		jugador2.moverA(bsassur.getPosicion());
		
		Assert.assertEquals( jugador2.getEfectivo(), saldoInicialJugador2 -PRECIO_ALQUILERHOTEL_BSASSUR );
		saldoInicialJugador2 = jugador2.getEfectivo();
		jugador2.moverA(bsasnorte.getPosicion());
		Assert.assertEquals( jugador2.getEfectivo(), saldoInicialJugador2 -PRECIO_ALQUILERHOTEL_BSASNORTE );
	}

	@Test
	public void test08a_Barrio_JugadorTieneCORDOBASurYNorteConDosCasasEnCadaBarrioyUnHotel_ReduceEfectivoContrincanteEn3500() {
		Juego juego = new Juego();
		Jugador jugador = new Jugador("Putin Vladimir",juego);
		
		jugador.moverA(new Posicion(POS_CORDOBASUR));
		
		jugador.getAccionesPosibles().get(ComprarPropiedadComando.prefijoAccion + CordobaSur.nombreClase).ejecutar();

		Barrio cs = jugador.obtenerBarrios().get(CordobaSur.nombreClase);
		
		jugador.moverA(new Posicion(POS_CORDOBANORTE));
		
		jugador.getAccionesPosibles().get(ComprarPropiedadComando.prefijoAccion + CordobaNorte.nombreClase).ejecutar();

		Barrio cn = jugador.obtenerBarrios().get(CordobaNorte.nombreClase);
		
		cs.construir();
		cn.construir();
		cs.construir();
		cn.construir();
		cs.construir();
		cn.construir();
		
		Jugador jugador2 = new Jugador("pp",juego);
		int saldoInicialJugador2 = jugador2.getEfectivo();
		jugador2.moverA(cs.getPosicion());
		
		Assert.assertEquals( jugador2.getEfectivo(), saldoInicialJugador2 - PRECIO_ALQUILERHOTEL_CORDOBASUR );
		
		saldoInicialJugador2 = jugador2.getEfectivo();
		jugador2.moverA(cn.getPosicion());
		
		Assert.assertEquals( jugador2.getEfectivo(), saldoInicialJugador2 - PRECIO_ALQUILERHOTEL_CORDOBANORTE );

	}
	
	@Test
	public void test08b_Barrio_JugadorTieneSALTASurYNorteConDosCasasEnCadaBarrioYunHotel_ReduceEfectivoContrincanteEn3500() {
		Juego juego = new Juego();
		Jugador jugador = new Jugador("Putin Vladimir",juego);
		
		jugador.moverA(new Posicion(POS_SALTASUR));
		jugador.getAccionesPosibles().get(ComprarPropiedadComando.prefijoAccion + SaltaSur.nombreClase).ejecutar();
		Barrio ss = jugador.obtenerBarrios().get(SaltaSur.nombreClase);
		
		jugador.moverA(new Posicion(POS_SALTANORTE));
		jugador.getAccionesPosibles().get(ComprarPropiedadComando.prefijoAccion + SaltaNorte.nombreClase).ejecutar();
		Barrio sn = jugador.obtenerBarrios().get(SaltaNorte.nombreClase);
		
		ss.construir();
		sn.construir();
		ss.construir();
		sn.construir();
		ss.construir();
		
		Jugador jugador2 = new Jugador("pp",juego);
		int saldoInicialJugador2 = jugador2.getEfectivo();
		jugador2.moverA(ss.getPosicion());
		
		Assert.assertEquals( jugador2.getEfectivo(), saldoInicialJugador2 - PRECIO_ALQUILERHOTEL_SALTASUR);
	}
	
	@Test
	public void test09_Barrio_JugadorTieneSantaFe_EdificarUnaCasaReduceSuEfectivoEn4000() {
		Juego juego = new Juego();
		Jugador jugador = new Jugador("Putin Vladimir",juego);
		
		jugador.moverA(new Posicion(POS_SANTAFE));
		jugador.getAccionesPosibles().get(ComprarPropiedadComando.prefijoAccion + SantaFe.nombreClase).ejecutar();
		Barrio sf = jugador.obtenerBarrios().get(SantaFe.nombreClase);
		
		int saldoInicialJugador = jugador.getEfectivo();
		sf.construir();		
		Assert.assertEquals( jugador.getEfectivo(), saldoInicialJugador - PRECIO_CASA_SANTAFE );

	}
	
	@Test
	public void test10a_Barrio_JugadorTieneSantaFeYUnaCasa_ReduceEfectivoContrincanteEn3500() {
		Juego juego = new Juego();
		Jugador jugador = new Jugador("Putin Vladimir",juego);		
		jugador.moverA(new Posicion(POS_SANTAFE));
		jugador.getAccionesPosibles().get(ComprarPropiedadComando.prefijoAccion + SantaFe.nombreClase).ejecutar();
		Barrio sf = jugador.obtenerBarrios().get(SantaFe.nombreClase);
		sf.construir();		
		Jugador jugador2 = new Jugador("x",juego);
		int saldoInicialJugador2 = jugador2.getEfectivo();
		jugador2.moverA(new Posicion(POS_SANTAFE));
		Assert.assertEquals( jugador2.getEfectivo(), saldoInicialJugador2 - PRECIO_ALQUILER1CASA_SANTAFE );

	}
		
	@Test
	public void test10b_Barrio_JugadorTieneNeuquenYUnaCasa_EfectivoContrincanteSeReduceEn3800() {
		Juego juego = new Juego();
		Jugador jugador = new Jugador("Putin Vladimir",juego);
		
		jugador.moverA(new Posicion(POS_NEUQUEN));
		jugador.getAccionesPosibles().get(ComprarPropiedadComando.prefijoAccion + Neuquen.nombreClase).ejecutar();
		Barrio n = jugador.obtenerBarrios().get(Neuquen.nombreClase);
		n.construir();	
		Jugador jugador2 = new Jugador("ppp", juego);
		int saldoInicialJugador2 = jugador2.getEfectivo();
		jugador2.moverA(n.getPosicion());
		Assert.assertEquals( jugador2.getEfectivo(), saldoInicialJugador2 - PRECIO_ALQUILER1CASA_NEUQUEN );

	}
	
	@Test
	public void test10c_Barrio_JugadorTieneTucumanYUnaCasa_EfectivoContrincanteSeReduceEn4500() {
		Juego juego = new Juego();
		Jugador jugador = new Jugador("Putin Vladimir",juego);
		
		jugador.moverA(new Posicion(POS_TUCUMAN));
		jugador.getAccionesPosibles().get(ComprarPropiedadComando.prefijoAccion + Tucuman.nombreClase).ejecutar();
		Barrio t = jugador.obtenerBarrios().get(Tucuman.nombreClase);
		t.construir();	
		Jugador jugador2 = new Jugador("ppp", juego);
		int saldoInicialJugador2 = jugador2.getEfectivo();
		jugador2.moverA(t.getPosicion());
		Assert.assertEquals( jugador2.getEfectivo(), saldoInicialJugador2 - PRECIO_ALQUILER1CASA_TUCUMAN );

	}
	
	@Test
	public void test11_Compania_JugadorTieneTRENES_EfectivoContrincanteSeReduceTiradaDadosX450() {
		Juego juego = new Juego();
		Jugador jugador = new Jugador("Putin Vladimir",juego);
		
		jugador.moverA(new Posicion(POS_TRENES));
		jugador.getAccionesPosibles().get(ComprarPropiedadComando.prefijoAccion + Tren.nombreClase).ejecutar();
		Jugador jugador2 = new Jugador("pp", juego);
		
		jugador2.setUltimaTiradaDados(2);
		int saldoInicialJugador2 = jugador2.getEfectivo();
		jugador2.moverA(new Posicion(POS_TRENES));
		Assert.assertEquals(jugador2.getEfectivo(), saldoInicialJugador2 -(2*450) );

	}
	
	@Test
	public void test12_Compania_JugadorTieneTRENESySUBTE_EfectivoContrincanteSeReduceTiradaDadosX850() {
		Juego juego = new Juego();
		Jugador jugador = new Jugador("Putin Vladimir",juego);
		
		jugador.moverA(new Posicion(POS_SUBTE));
		jugador.getAccionesPosibles().get(ComprarPropiedadComando.prefijoAccion + Subte.nombreClase).ejecutar();
		jugador.moverA(new Posicion(POS_TRENES));
		jugador.getAccionesPosibles().get(ComprarPropiedadComando.prefijoAccion + Tren.nombreClase).ejecutar();

		Jugador jugador2 = new Jugador("ANALIA", juego);
		jugador2.setUltimaTiradaDados(2);
		int saldoInicialJugador2 = jugador2.getEfectivo();
		jugador2.moverA(new Posicion(POS_TRENES));
		Assert.assertEquals(jugador2.getEfectivo(),saldoInicialJugador2-(2*800));

	}
	
	@Test
	public void test13_Compania_JugadorIntercambiaCompaniaAOtroJugador_NuevoDuenioCobraRentaDeCompania() {
		//el jugador vende su propiedad, un segundo jugador se aduenia de la propiedad sin duenio
		//y el alquiler que se le debita a un tercer jugador que cae en la misma se le acredita al ultimo propietario
		
		Juego juego = new Juego();
		Jugador jugador = new Jugador("pepe",juego);
		jugador.moverA(new Posicion(POS_TRENES));
		jugador.getAccionesPosibles().get(ComprarPropiedadComando.prefijoAccion + Tren.nombreClase).ejecutar();
		Compania trenes = jugador.obtenerCompanias().get("Trenes");
		
		Jugador jugador2 = new Jugador("pp",juego);
		jugador2.moverA(new Posicion(POS_AYSA));
		jugador2.getAccionesPosibles().get(ComprarPropiedadComando.prefijoAccion + Aysa.nombreClase).ejecutar();
		Compania aysa = jugador2.obtenerCompanias().get("Aysa");
		
		trenes.vender();		
		jugador2.moverA(new Posicion(POS_TRENES));
		jugador2.getAccionesPosibles().get(ComprarPropiedadComando.prefijoAccion + Tren.nombreClase).ejecutar();
		
		aysa.vender();
		jugador.moverA(new Posicion(POS_AYSA));
		jugador.getAccionesPosibles().get(ComprarPropiedadComando.prefijoAccion + Aysa.nombreClase).ejecutar();
		
		Jugador jugador3 = new Jugador("pp", juego);
		
		int saldoInicialJugador2 = jugador2.getEfectivo();
		jugador3.setUltimaTiradaDados(2);
		int saldoInicialJugador1 = jugador.getEfectivo();
		
		jugador3.moverA(new Posicion(POS_TRENES));
		Assert.assertEquals( jugador2.getEfectivo(), saldoInicialJugador2 + (2*450) );
		
		jugador3.moverA(new Posicion(POS_AYSA));
		Assert.assertEquals( jugador.getEfectivo(), saldoInicialJugador1 + (2*300) );
	}

	//el jugador vende su propiedad, un segundo jugador se aduenia de la propiedad sin duenio
	//y el alquiler que se le debita a un tercer jugador que cae en la misma se le acredita al ultimo propietario
	@Test
	public void test14_Barrio_JugadorIntercambiaBarrioAotroJugador_NuevoDuenioCobraAlquilerDeTerreno() {
		Juego juego = new Juego();
		Jugador jugador = new Jugador("pp",juego);
		
		jugador.moverA(new Posicion(POS_BSASNORTE));
		jugador.getAccionesPosibles().get(ComprarPropiedadComando.prefijoAccion + BuenosAiresNorte.nombreClase).ejecutar();
		Barrio bsn = jugador.obtenerBarrios().get("Buenos Aires Norte");
		
		Jugador jugador2 = new Jugador("s",juego);
		
		jugador2.moverA(new Posicion(POS_CORDOBASUR));
		jugador2.getAccionesPosibles().get(ComprarPropiedadComando.prefijoAccion + CordobaSur.nombreClase).ejecutar();
		Barrio cs = jugador2.obtenerBarrios().get("Cordoba Sur");
		
		bsn.vender();
		jugador2.moverA(new Posicion(POS_BSASNORTE));
		jugador2.getAccionesPosibles().get(ComprarPropiedadComando.prefijoAccion + BuenosAiresNorte.nombreClase).ejecutar();
		
		cs.vender();
		jugador.moverA(new Posicion(POS_CORDOBASUR));
		jugador.getAccionesPosibles().get(ComprarPropiedadComando.prefijoAccion + CordobaSur.nombreClase).ejecutar();
		
		Jugador jugador3 = new Jugador("pp",juego);
		
		int efvo1 = jugador.getEfectivo();
		int efvo2 = jugador2.getEfectivo();
		
		jugador3.moverA(new Posicion(POS_BSASNORTE));
		Assert.assertEquals(jugador2.getEfectivo(), efvo2 + 2500);
		
		jugador3.moverA(new Posicion(POS_CORDOBASUR));
		Assert.assertEquals(jugador.getEfectivo(), efvo1 + 1000);
		
	}	
	
	@Test
	public void test15_ImpuestoAlLujo_EfectivoJugadorSeReduce10PorCiento() {
		Juego juego = new Juego();
		Jugador jugador = new Jugador("Putin Vladimir",juego);
		int saldoInicialJugador = jugador.getEfectivo();		
		jugador.moverA(new Posicion(POS_IMP_AL_LUJO));
		Assert.assertEquals( jugador.getEfectivo(), saldoInicialJugador - 10000 );

	}
	
	@Test
	public void test16_Compania_JugadorTieneAYSA_EfectivoContrincanteSeReduceTiradaDadosX500() {
		Juego juego = new Juego();
		Jugador jugador = new Jugador("Putin Vladimir",juego);
		
		jugador.moverA(new Posicion(POS_AYSA));
		jugador.getAccionesPosibles().get(ComprarPropiedadComando.prefijoAccion + Aysa.nombreClase).ejecutar();
		
		Jugador jugador2 = new Jugador("ANALIA", juego);
		jugador2.setUltimaTiradaDados(2);
		int saldoInicialJugador2 = jugador2.getEfectivo();
		jugador2.moverA(new Posicion(POS_AYSA));
		Assert.assertEquals( jugador2.getEfectivo(), saldoInicialJugador2 -(2*300) );

	}
	
	@Test
	public void test17_Compania_JugadorTieneAYSAyEDESUR_EfectivoContrincanteSeReduceTiradaDadosX1000() {
		Juego juego = new Juego();
		Jugador jugador = new Jugador("Putin Vladimir",juego);
		
		jugador.moverA(new Posicion(POS_AYSA));
		jugador.getAccionesPosibles().get(ComprarPropiedadComando.prefijoAccion + Aysa.nombreClase).ejecutar();
		
		jugador.moverA(new Posicion(POS_EDESUR));
		jugador.getAccionesPosibles().get(ComprarPropiedadComando.prefijoAccion + Edesur.nombreClase).ejecutar();
		
		Jugador jugador2 = new Jugador("ANALIA", juego);
		jugador2.setUltimaTiradaDados(2);
		int saldoInicialJugador2 = jugador2.getEfectivo();
		jugador2.moverA(new Posicion(POS_AYSA));
		Assert.assertEquals( jugador2.getEfectivo(), saldoInicialJugador2 -(2*500) );

	}
	


}
