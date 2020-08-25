package model.juego;

import java.util.Hashtable;

import org.junit.Assert;
import org.junit.Test;

import model.TiradaDosDadosSiempreNumerosIguales;
import model.TiradaSiempreIgualAValor;
import model.command.Comando;
import model.command.PagarFianzaComando;
import model.command.TerminarTurnoComando;
import model.command.TirarDadosYAvanzarComando;
import model.espacio.Posicion;

public class JugadorTest {

	private static final String DADOS = TirarDadosYAvanzarComando.nombreAccion;
	private static final String TERMINAR = TerminarTurnoComando.nombreAccion;
	private static final String FIANZA = PagarFianzaComando.nombreAccion;
	
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
	
	@Test
	public void testCrearJugador() {
		Jugador jugador = new Jugador("Pepe", new Juego());
		Assert.assertNotNull(jugador);
	}
	
	@Test
	public void testJugadorNuevoPuedeMoverse() {
		Jugador jugador = new Jugador("Pepe", new Juego());
		jugador.moverA(new Posicion(5));
		Assert.assertEquals(5, jugador.getPosicion().getValor());
	}
	
	@Test
	public void testJugadorNombreCorrecto() {
		Jugador jugador = new Jugador("Pepe", new Juego());
		Assert.assertEquals("Pepe", jugador.getNombre());
	}
	
	@Test
	public void testJugadorBloqueoMovimiento() {
		Jugador jugador = new Jugador("Pepe", new Juego());
		jugador.impedirMovimiento();
		Assert.assertEquals("Pepe", jugador.getNombre());
	}
	
	@Test
	public void testJugadorAccionesInicioTurno() {
		Jugador jugador = new Jugador("Pepe", new Juego());
		jugador.iniciarTurno();
		
		
		Hashtable<String, Comando> acciones = jugador.getAccionesPosibles();
		
		Assert.assertTrue(acciones.containsKey(DADOS));
		Assert.assertFalse(acciones.containsKey(TERMINAR));
		Assert.assertFalse(acciones.containsKey(FIANZA));
	}
	
	@Test
	public void testJugadorAccionesPostTiradaDadosDobles() {
		Jugador jugador = new Jugador("Pepe", new Juego());
		jugador.iniciarTurno();
		jugador.setTirada(new TiradaDosDadosSiempreNumerosIguales(3));
		
		jugador.getAccionesPosibles().get(DADOS).ejecutar();
		Hashtable<String, Comando> acciones = jugador.getAccionesPosibles();
		
		Assert.assertTrue(acciones.containsKey(DADOS));
		Assert.assertFalse(acciones.containsKey(TERMINAR));
		Assert.assertFalse(acciones.containsKey(FIANZA));
		
		acciones.get(DADOS).ejecutar();
		
		acciones = jugador.getAccionesPosibles();
		
		Assert.assertFalse(acciones.containsKey(DADOS));
		Assert.assertTrue(acciones.containsKey(TERMINAR));
		Assert.assertFalse(acciones.containsKey(FIANZA));
	}
	
	@Test
	public void testJugadorAccionesPostTiradaDados() {
		Jugador jugador = new Jugador("Pepe", new Juego());
		jugador.iniciarTurno();
		jugador.setTirada(new TiradaSiempreIgualAValor(6));
		
		jugador.getAccionesPosibles().get(DADOS).ejecutar();
		Hashtable<String, Comando> acciones = jugador.getAccionesPosibles();
		
		Assert.assertFalse(acciones.containsKey(DADOS));
		Assert.assertTrue(acciones.containsKey(TERMINAR));
		Assert.assertFalse(acciones.containsKey(FIANZA));
	}
	
	@Test
	public void testJugadorAccionesCarcel() {
		Jugador jugador = new Jugador("Pepe", new Juego());
		jugador.iniciarTurno();
		
		Assert.assertEquals(POS_SALIDA, jugador.getPosicion().getValor());
		
		jugador.setTirada(new TiradaSiempreIgualAValor(POS_CARCEL));
		
		jugador.getAccionesPosibles().get(DADOS).ejecutar();
		
		Assert.assertEquals(POS_CARCEL, jugador.getPosicion().getValor());
		
		Hashtable<String, Comando> acciones = jugador.getAccionesPosibles();
		
		Assert.assertFalse(acciones.containsKey(DADOS));
		Assert.assertTrue(acciones.containsKey(TERMINAR));
		Assert.assertFalse(acciones.containsKey(FIANZA));
	}
	
	@Test
	public void testJugadorAccionesCarcelPagarFianza() {
		Jugador jugador = new Jugador("Pepe", new Juego());
		jugador.iniciarTurno();
		
		Assert.assertEquals(POS_SALIDA, jugador.getPosicion().getValor());
		
		jugador.setTirada(new TiradaSiempreIgualAValor(POS_CARCEL));
		
		jugador.getAccionesPosibles().get(DADOS).ejecutar();
		
		Assert.assertEquals(POS_CARCEL, jugador.getPosicion().getValor());
		
		Hashtable<String, Comando> acciones = jugador.getAccionesPosibles();
		
		Assert.assertFalse(acciones.containsKey(DADOS));
		Assert.assertTrue(acciones.containsKey(TERMINAR));
		Assert.assertFalse(acciones.containsKey(FIANZA));
		
		jugador.terminarTurno();
		jugador.iniciarTurno();
		
		acciones = jugador.getAccionesPosibles();
		
		Assert.assertTrue(acciones.containsKey(DADOS));
		Assert.assertFalse(acciones.containsKey(TERMINAR));
		Assert.assertFalse(acciones.containsKey(FIANZA));
		
		acciones.get(DADOS).ejecutar();
		
		Assert.assertEquals(POS_CARCEL, jugador.getPosicion().getValor());
		
		acciones = jugador.getAccionesPosibles();
		
		Assert.assertFalse(acciones.containsKey(DADOS));
		Assert.assertTrue(acciones.containsKey(TERMINAR));
		Assert.assertFalse(acciones.containsKey(FIANZA));
		
		jugador.terminarTurno();
		jugador.iniciarTurno();
		
		acciones = jugador.getAccionesPosibles();
		
		Assert.assertTrue(acciones.containsKey(DADOS));
		Assert.assertFalse(acciones.containsKey(TERMINAR));
		Assert.assertTrue(acciones.containsKey(FIANZA));
		
		acciones.get(DADOS).ejecutar();
		
		Assert.assertEquals(POS_CARCEL, jugador.getPosicion().getValor());
		
		acciones = jugador.getAccionesPosibles();
		
		Assert.assertFalse(acciones.containsKey(DADOS));
		Assert.assertTrue(acciones.containsKey(TERMINAR));
		Assert.assertFalse(acciones.containsKey(FIANZA));
		
		jugador.terminarTurno();
		jugador.iniciarTurno();
		
		acciones = jugador.getAccionesPosibles();
		
		Assert.assertTrue(acciones.containsKey(DADOS));
		Assert.assertFalse(acciones.containsKey(TERMINAR));
		Assert.assertTrue(acciones.containsKey(FIANZA));
		
		acciones.get(FIANZA).ejecutar();
		
		acciones = jugador.getAccionesPosibles();
		
		Assert.assertTrue(acciones.containsKey(DADOS));
		Assert.assertFalse(acciones.containsKey(TERMINAR));
		Assert.assertFalse(acciones.containsKey(FIANZA));
		
		Assert.assertEquals(POS_CARCEL, jugador.getPosicion().getValor());
		
		acciones.get(DADOS).ejecutar();
		
		Assert.assertTrue(jugador.getUltimaTiradaDados() != 0);
	}
	
	@Test
	public void accionesCaerEnPoliciaDesdeCarcelDespuesDeFianza() {
		Jugador jugador = new Jugador("Pepe", new Juego());
		jugador.iniciarTurno();
		
		Assert.assertEquals(POS_SALIDA, jugador.getPosicion().getValor());
		
		jugador.setTirada(new TiradaSiempreIgualAValor(POS_CARCEL));
		
		jugador.getAccionesPosibles().get(DADOS).ejecutar();
		
		Assert.assertEquals(POS_CARCEL, jugador.getPosicion().getValor());
		
		Hashtable<String, Comando> acciones = jugador.getAccionesPosibles();
		
		Assert.assertFalse(acciones.containsKey(DADOS));
		Assert.assertTrue(acciones.containsKey(TERMINAR));
		Assert.assertFalse(acciones.containsKey(FIANZA));
		Assert.assertEquals(1, acciones.size());
		
		jugador.terminarTurno();
		jugador.iniciarTurno();
		
		acciones = jugador.getAccionesPosibles();
		
		Assert.assertTrue(acciones.containsKey(DADOS));
		Assert.assertFalse(acciones.containsKey(TERMINAR));
		Assert.assertFalse(acciones.containsKey(FIANZA));
		Assert.assertEquals(1, acciones.size());
		
		acciones.get(DADOS).ejecutar();
		
		Assert.assertEquals(POS_CARCEL, jugador.getPosicion().getValor());
		
		acciones = jugador.getAccionesPosibles();
		
		Assert.assertFalse(acciones.containsKey(DADOS));
		Assert.assertTrue(acciones.containsKey(TERMINAR));
		Assert.assertFalse(acciones.containsKey(FIANZA));
		Assert.assertEquals(1, acciones.size());
		
		jugador.terminarTurno();
		jugador.iniciarTurno();
		
		acciones = jugador.getAccionesPosibles();
		
		Assert.assertTrue(acciones.containsKey(DADOS));
		Assert.assertFalse(acciones.containsKey(TERMINAR));
		Assert.assertTrue(acciones.containsKey(FIANZA));
		Assert.assertEquals(2, acciones.size());
		
		acciones.get(FIANZA).ejecutar();
		
		jugador.setTirada(new TiradaDosDadosSiempreNumerosIguales(5));
		
		acciones = jugador.getAccionesPosibles();
		
		Assert.assertTrue(acciones.containsKey(DADOS));
		Assert.assertFalse(acciones.containsKey(TERMINAR));
		Assert.assertFalse(acciones.containsKey(FIANZA));
		Assert.assertEquals(1, acciones.size());
		
		acciones.get(DADOS).ejecutar();
		
		Assert.assertEquals(POS_CARCEL, jugador.getPosicion().getValor());
		
		acciones = jugador.getAccionesPosibles();
		
		Assert.assertTrue(acciones.containsKey(DADOS));
		Assert.assertFalse(acciones.containsKey(TERMINAR));
		Assert.assertFalse(acciones.containsKey(FIANZA));
		Assert.assertEquals(1, acciones.size());
		
		acciones.get(DADOS).ejecutar();
		
		Assert.assertEquals(POS_CARCEL, jugador.getPosicion().getValor());
		

		acciones = jugador.getAccionesPosibles();
		
		Assert.assertFalse(acciones.containsKey(DADOS));
		Assert.assertTrue(acciones.containsKey(TERMINAR));
		Assert.assertFalse(acciones.containsKey(FIANZA));
		Assert.assertEquals(1, acciones.size());
	}
}
