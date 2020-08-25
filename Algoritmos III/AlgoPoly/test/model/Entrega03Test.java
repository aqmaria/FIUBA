package model;

import java.util.Hashtable;

import org.junit.Assert;
import org.junit.Test;

import excepciones.BarrioLimiteEdificacionesException;
import model.casillero.propiedad.Barrio;
import model.casillero.propiedad.Neuquen;
import model.casillero.propiedad.SantaFe;
import model.casillero.propiedad.Tucuman;
import model.command.Comando;
import model.command.ComprarPropiedadComando;
import model.command.TerminarTurnoComando;
import model.command.TirarDadosYAvanzarComando;
import model.espacio.Posicion;
import model.juego.Juego;
import model.juego.Jugador;

public class Entrega03Test {
	
	private static final int POS_SALIDA = 0;
	private static final int POS_QUINI = 1;
	private static final int POS_BSASSUR = 2;
	private static final int POS_EDESUR = 3;
	private static final int POS_BSASNORTE = 4;
	private static final int POS_CARCEL = 5;
	private static final int POS_CORDOBASUR = 6;
	private static final int POS_AVANCE = 7;
	private static final int POS_SUBTE = 8;
	private static final int POS_CORDOBANORTE = 9;
	private static final int POS_IMPUESTO = 10;
	private static final int POS_SANTAFE = 11;
	private static final int POS_AYSA = 12;
	private static final int POS_SALTANORTE = 13;
	private static final int POS_SALTASUR = 14;
	private static final int POS_POLICIA = 15;
	private static final int POS_TRENES = 16;
	private static final int POS_NEUQUEN = 17;
	private static final int POS_RETROCESO = 18;
	private static final int POS_TUCUMAN = 19;
	
	private static final int PRECIO_BSASSUR = 20000;
	private static final int PRECIO_SANTAFE = 15000;
	private static final int PRECIO_ALQUILER_SANTAFE = 1500;
	
	private static final String DADOS = TirarDadosYAvanzarComando.nombreAccion;
	private static final String TERMINAR = TerminarTurnoComando.nombreAccion;
	private static final String VENDERSANTAFE = "Vender Santa Fe";
	
	@Test
	public void test01_DadosIgualesTiraDosVeces() {
	    Juego juego = new Juego();
	    juego.agregarJugador("Pepe");
	    juego.agregarJugador("Cacho");
	    
	    juego.establecerTurnosAlAzar();
	    juego.iniciarJuego();
	    
	    Jugador jugador1 = juego.getJugadorActual();
	    
	    jugador1.setTirada(new TiradaDosDadosSiempreNumerosIguales(1));
	    
	    Hashtable<String, Comando> acciones = jugador1.getAccionesPosibles();
	    
	    Assert.assertTrue(acciones.containsKey(DADOS));
	    
	    acciones.get(DADOS).ejecutar();
	    
	    acciones = jugador1.getAccionesPosibles();
	    
	    Assert.assertTrue(acciones.containsKey(DADOS));
	}
	
	@Test
	public void test02_DadosIgualesDosVecesSoloRepiteUna() {
	    Juego juego = new Juego();
	    juego.agregarJugador("Pepe");
	    juego.agregarJugador("Cacho");
	    
	    juego.establecerTurnosAlAzar();
	    juego.iniciarJuego();
	    
	    Jugador jugador1 = juego.getJugadorActual();
	    
	    jugador1.setTirada(new TiradaDosDadosSiempreNumerosIguales(1));
	    
	    Hashtable<String, Comando> acciones = jugador1.getAccionesPosibles();
	    
	    Assert.assertTrue(acciones.containsKey(DADOS));
	    
	    acciones.get(DADOS).ejecutar();
	    
	    jugador1.setTirada(new TiradaDosDadosSiempreNumerosIguales(2));
	    
	    acciones = jugador1.getAccionesPosibles();
	    
	    Assert.assertTrue(acciones.containsKey(DADOS));
	    
	    acciones.get(DADOS).ejecutar();
	    
	    jugador1.setTirada(new TiradaDosDadosSiempreNumerosIguales(3));
	    
	    acciones = jugador1.getAccionesPosibles();
	    
	    Assert.assertFalse(acciones.containsKey(DADOS));
	}
	
	@Test
	public void test03_VentaPorDeudaDestruyeEdificaciones() {
		Juego juego = new Juego();
	    juego.agregarJugador("Pepe");
	    juego.agregarJugador("Cacho");
	    juego.agregarJugador("Norma");
	    
	    juego.establecerTurnosAlAzar();
	    juego.iniciarJuego();
	    
	    Jugador jugador1 = juego.getJugadorActual();
	    jugador1.setEfectivo(100000000);
	    jugador1.moverA(new Posicion(POS_SANTAFE));
	    jugador1.getAccionesPosibles().get(ComprarPropiedadComando.prefijoAccion + SantaFe.nombreClase).ejecutar();
	    Barrio santafe = (Barrio)juego.getCasilleroEnPosicion(new Posicion(POS_SANTAFE));
	    santafe.construir();
	    
	    Assert.assertTrue(santafe.getCantidadEdificaciones() == 1);
	    
	    jugador1.setEfectivo(0);
	    jugador1.debitar(1);//Entra en deuda
	    
	    Hashtable<String, Comando> acciones = jugador1.getAccionesPosibles();
	    
	    Assert.assertTrue(acciones.containsKey(VENDERSANTAFE) && !acciones.containsKey(TERMINAR));
	    
	    jugador1.getAccionesPosibles().get(VENDERSANTAFE).ejecutar();
	    
	    Assert.assertTrue(santafe.getCantidadEdificaciones() == 0);
	    
	    juego.avanzarTurno();
	    
	    Jugador jugador2 = juego.getJugadorActual();
	    
	    jugador2.moverA(new Posicion(POS_SANTAFE));
	    jugador2.getAccionesPosibles().get(ComprarPropiedadComando.prefijoAccion + SantaFe.nombreClase).ejecutar();
	    juego.avanzarTurno();
	    
	    jugador2.setEfectivo(0);
	    jugador1.setEfectivo(0);
	    
	    Jugador jugador3 = juego.getJugadorActual();
	    jugador3.moverA(new Posicion(POS_SANTAFE));
	    
	    Assert.assertTrue(jugador2.getEfectivo() == PRECIO_ALQUILER_SANTAFE && jugador1.getEfectivo() == 0);
	}
	
	@Test
	public void test04_JugadorTiraDadosSeMueveAlLugarIndicadoPorEstos() {
	    Juego juego = new Juego();
	    juego.agregarJugador("Pepe");
	    juego.agregarJugador("Cacho");
	    
	    juego.establecerTurnosAlAzar();
	    juego.iniciarJuego();
	    
	    Jugador jugador = juego.getJugadorActual();
	    
	    Assert.assertEquals(POS_SALIDA, jugador.getPosicion().getValor());
	    
	    jugador.getAccionesPosibles().get(DADOS).ejecutar();
	    
	    if(jugador.getUltimaTiradaDados() == POS_AVANCE) {}
	    else
	    	Assert.assertEquals(POS_SALIDA, jugador.getPosicion().getValor() - jugador.getUltimaTiradaDados());
	}
	
	@Test(expected = BarrioLimiteEdificacionesException.class)
	public void test05_NoSePuedeEdificarEnBarrioSimple_SantaFe() {
		Juego juego = new Juego();
	    juego.agregarJugador("Pepe");
	    
	    juego.establecerTurnosAlAzar();
	    juego.iniciarJuego();
	    
	    Posicion posicionBarrio = new Posicion(POS_SANTAFE);
	    
	    Jugador jugador1 = juego.getJugadorActual();
	    jugador1.setEfectivo(100000000);
	    jugador1.moverA(posicionBarrio);
	    jugador1.getAccionesPosibles().get(ComprarPropiedadComando.prefijoAccion + SantaFe.nombreClase).ejecutar();
	    Barrio barrio = (Barrio)juego.getCasilleroEnPosicion(posicionBarrio);
	    
	    Assert.assertEquals(0, barrio.getCantidadEdificaciones());
	    
	    barrio.construir();
	    
	    Assert.assertEquals(1, barrio.getCantidadEdificaciones());
	    
	    barrio.construir();
	    
	    Assert.assertEquals(1, barrio.getCantidadEdificaciones());
	}
	
	@Test(expected = BarrioLimiteEdificacionesException.class)
	public void test05_NoSePuedeEdificarEnBarrioSimple_Tucuman() {
		Juego juego = new Juego();
	    juego.agregarJugador("Pepe");
	    
	    juego.establecerTurnosAlAzar();
	    juego.iniciarJuego();
	    
	    Posicion posicionBarrio = new Posicion(POS_TUCUMAN);
	    
	    Jugador jugador1 = juego.getJugadorActual();
	    jugador1.setEfectivo(100000000);
	    jugador1.moverA(posicionBarrio);
	    jugador1.getAccionesPosibles().get(ComprarPropiedadComando.prefijoAccion + Tucuman.nombreClase).ejecutar();
	    Barrio barrio = (Barrio)juego.getCasilleroEnPosicion(posicionBarrio);
	    
	    Assert.assertEquals(0, barrio.getCantidadEdificaciones());
	    
	    barrio.construir();
	    
	    Assert.assertEquals(1, barrio.getCantidadEdificaciones());
	    
	    barrio.construir();
	    
	    Assert.assertEquals(1, barrio.getCantidadEdificaciones());
	}
	
	@Test(expected = BarrioLimiteEdificacionesException.class)
	public void test05_NoSePuedeEdificarEnBarrioSimple_Neuquen() {
		Juego juego = new Juego();
	    juego.agregarJugador("Pepe");
	    
	    juego.establecerTurnosAlAzar();
	    juego.iniciarJuego();
	    
	    Posicion posicionBarrio = new Posicion(POS_NEUQUEN);
	    
	    Jugador jugador1 = juego.getJugadorActual();
	    jugador1.setEfectivo(100000000);
	    jugador1.moverA(posicionBarrio);
	    jugador1.getAccionesPosibles().get(ComprarPropiedadComando.prefijoAccion + Neuquen.nombreClase).ejecutar();
	    Barrio barrio = (Barrio)juego.getCasilleroEnPosicion(posicionBarrio);
	    
	    Assert.assertEquals(0, barrio.getCantidadEdificaciones());
	    
	    barrio.construir();
	    
	    Assert.assertEquals(1, barrio.getCantidadEdificaciones());
	    
	    barrio.construir();
	    
	    Assert.assertEquals(1, barrio.getCantidadEdificaciones());
	}
	
	@Test
	public void test06_FinDelJuego() {
	    Juego juego = new Juego();
	    juego.agregarJugador("Pepe");
	    juego.agregarJugador("Cacho");
	    juego.agregarJugador("Marta");
	    
	    juego.establecerTurnosAlAzar();
	    juego.iniciarJuego();
	    
	    Assert.assertEquals(3, juego.getCantidadJugadores());
	    
	    Jugador jugador = juego.getJugadorActual();
	    jugador.setEfectivo(0);
	    jugador.debitar(1);
	    jugador.getAccionesPosibles();
	    
	    Assert.assertEquals(2, juego.getCantidadJugadores());
	    
	    jugador = juego.getJugadorActual();
	    jugador.setEfectivo(0);
	    jugador.debitar(1);
	    jugador.getAccionesPosibles();
	    
	    Assert.assertEquals(1, juego.getCantidadJugadores());
	    
	    Assert.assertTrue(juego.estaTerminado());
	}
}
