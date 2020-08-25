package model;

import org.junit.Assert;
import org.junit.Test;

import excepciones.JugadorNoPuedePagarFianzaEnSegundoTurnoDeCarcelException;
import excepciones.NoPuedeVendersePropiedadSinDuenioException;
import model.casillero.*;
import model.casillero.propiedad.Barrio;
import model.casillero.propiedad.Tucuman;
import model.espacio.*;
import model.juego.*;

public class Entrega01Test {
	
	private static final int POS_POLICIA = 15;
	private static final int POS_CARCEL = 5;
	private static final int POS_QUINI = 1;
	private static final int POS_AVANCE = 7;
	private static final int POS_RETROCESO = 18;
	private static final int POS_TUCUMAN = 19;
	
	private static final int PRIMER_QUINI = 50000;
	private static final int SEGUNDO_QUINI = 30000;
	
	private static final int POS_BSASNORTE = 4;

    
	@Test
	public void test01_Quini6_PrimeraVezIncrementaEfectivoJugadorEn50000() {
	    Juego juego = new Juego();
	    Jugador jugador = juego.agregarJugador("Pepe");
	    
	    int efectivoInicial = jugador.getEfectivo();
	    
		jugador.moverA(new Posicion(POS_QUINI));
		Assert.assertEquals(PRIMER_QUINI, jugador.getEfectivo() - efectivoInicial); //assertEquals(expected, actual);
		
	}
	
	@Test
	public void test02_Quini6_SegundaVezIncrementaEfectivoJugadorEn30000() {
		Juego juego = new Juego();
	    Jugador jugador = juego.agregarJugador("Pepe");
	    
		int efectivoInicial = jugador.getEfectivo();
		Posicion posQuini = new Posicion(POS_QUINI);
		
		jugador.moverA(posQuini);
		jugador.moverA(posQuini);

		Assert.assertEquals(SEGUNDO_QUINI, jugador.getEfectivo() - (efectivoInicial + PRIMER_QUINI));
	}
	
	@Test
	public void test03_Quini6_TerceraVezNoIncrementaEfectivoJugador() {
		Juego juego = new Juego();
	    Jugador jugador = juego.agregarJugador("Pepe");
	    
		int efectivoInicial = jugador.getEfectivo();
		Posicion posQuini = new Posicion(POS_QUINI);
		
		jugador.moverA(posQuini);
		jugador.moverA(posQuini);
		jugador.moverA(posQuini);
		
		Assert.assertEquals(SEGUNDO_QUINI, jugador.getEfectivo() - (efectivoInicial + PRIMER_QUINI));
	}
	

	@Test
	public void test04_Barrio_JugadorEnBarrioSinDuenioSeAduenia() {

		Juego juego = new Juego();
		Jugador jugador = new Jugador("Pepe",juego);
		jugador.moverA(new Posicion(POS_BSASNORTE));
		Barrio bsasnorte = (Barrio)juego.getCasilleroEnPosicion(new Posicion(POS_BSASNORTE));
		
		jugador.getAccionesPosibles().get("Comprar Buenos Aires Norte").ejecutar();
		
		Assert.assertEquals(jugador, bsasnorte.getPropietario());
	}
	
	
	// falla jugador se puede mover a la carcel 
	
	@Test
	public void test05_Carcel_InmovilizaAJugador(){
		Juego juego = new Juego();
		Jugador jugador = juego.agregarJugador("Pepe");
		
		jugador.moverA(new Posicion(POS_CARCEL));
		Assert.assertEquals(POS_CARCEL, jugador.getPosicion().getValor());
		
		
		jugador.tirarDados();
		Assert.assertEquals(0, jugador.getUltimaTiradaDados());
	}
	
	@Test(expected = JugadorNoPuedePagarFianzaEnSegundoTurnoDeCarcelException.class)
	public void test06_Carcel_PagarFianzaEnTurno2DevuelveMovimientoJugador() {
		
		Juego juego = new Juego();
		Jugador jugador = juego.agregarJugador("Pepe");
		
		jugador.moverA(new Posicion(POS_CARCEL));
		Assert.assertEquals(POS_CARCEL, jugador.getPosicion().getValor());
		
		jugador.pagarFianza();
		jugador.tirarDados();
		Assert.assertEquals(0, jugador.getUltimaTiradaDados());
		jugador.avanzar(0);
		
		jugador.pagarFianza();
		jugador.tirarDados();
		Assert.assertNotEquals(0, jugador.getUltimaTiradaDados());
	}
	
	@Test
	public void test06_b_Carcel_PagarFianzaEnTurno3DevuelveMovimientoJugador() {
	
		Juego juego = new Juego();
		Jugador jugador = juego.agregarJugador("Pepe");
		
		jugador.moverA(new Posicion(POS_CARCEL));
		Assert.assertEquals(POS_CARCEL, jugador.getPosicion().getValor());
		
		
		jugador.tirarDados();
		Assert.assertEquals(0, jugador.getUltimaTiradaDados());
		jugador.avanzar(0);
		
		jugador.tirarDados();
		Assert.assertEquals(0, jugador.getUltimaTiradaDados());
		jugador.avanzar(0);
		
		jugador.pagarFianza();
		jugador.tirarDados();
		Assert.assertNotEquals(0, jugador.getUltimaTiradaDados());
	}
	
	@Test
	public void test07_Carcel_JugadorSinFondosNoPuedePagarFianzaEnTurno2o3NoSeMueve() {
		
		Juego juego = new Juego();
		Jugador jugador = juego.agregarJugador("Pepe");
		jugador.setEfectivo(0);
		
		jugador.moverA(new Posicion(POS_CARCEL));
		Assert.assertEquals(POS_CARCEL, jugador.getPosicion().getValor());
		
		jugador.tirarDados();
		Assert.assertEquals(0, jugador.getUltimaTiradaDados());
		jugador.avanzar(0);
		
		try {jugador.pagarFianza();}
		catch(Exception e) {};		
		jugador.tirarDados();
		Assert.assertEquals(0, jugador.getUltimaTiradaDados());
		jugador.avanzar(0);
		try {jugador.pagarFianza();}
		catch(Exception e) {};
		jugador.tirarDados();
		Assert.assertEquals(0, jugador.getUltimaTiradaDados());
		jugador.avanzar(0);
		
		jugador.tirarDados();
		Assert.assertNotEquals(0, jugador.getUltimaTiradaDados());;
	}
	
	@Test
	public void test08_AvanceDinamico_TiradaMayorA10_DesplazamientoJugadorIgualATiradaMenosSumatoriaPropiedades() {
	
		Juego juego = new Juego();
		Jugador jugador = juego.agregarJugador("ALF");
		AvanceDinamico avanceDinamico = new AvanceDinamico(new Posicion(7));
		jugador.setUltimaTiradaDados(11);
		jugador.agregarPropiedad(new Tucuman(new Posicion(2)));
		jugador.moverA(avanceDinamico.getPosicion());
		Assert.assertEquals( jugador.getPosicion().getValor(), 17 );
	}
	
	@Test
	public void test09_AvanceDinamico_TiradaMenorA11MayorA7_DesplazamientoJugadorIgualAEfectivoJugadorModuloTirada() {
		
		Juego juego = new Juego();
		Jugador jugador = juego.agregarJugador("ALF");

		jugador.setUltimaTiradaDados(9);
		jugador.moverA(new Posicion(POS_AVANCE));
		Assert.assertEquals( jugador.getPosicion().getValor(), 8 );

	}
	
	@Test
	public void test10_AvanceDinamico_TiradaMenorA7MayorA1_DesplazamientoJugadorIgualATiradaMenos2Unidades() {
		
		Juego juego = new Juego();
		Jugador jugador = juego.agregarJugador("Alf");
		
		jugador.setUltimaTiradaDados(6);
		jugador.moverA(new Posicion(POS_AVANCE));
		Assert.assertEquals(POS_AVANCE + (6 - 2), jugador.getPosicion().getValor());
		
		jugador.setUltimaTiradaDados(3);
		jugador.moverA(new Posicion(POS_AVANCE));
		Assert.assertEquals(POS_AVANCE + (3 - 2), jugador.getPosicion().getValor());
		
	}
		
		
	@Test
	public void test11_RetrocesoDinamico_TiradaMayorA10_DesplazamientoJugadorIgualATiradaMenos2Unidades() {
		
		Juego juego = new Juego();
		Jugador jugador = juego.agregarJugador("Alf");
		
		jugador.setUltimaTiradaDados(11);
		jugador.moverA(new Posicion(POS_RETROCESO));
		Assert.assertEquals(POS_RETROCESO - (11 - 2), jugador.getPosicion().getValor());
		
		jugador.setUltimaTiradaDados(12);
		jugador.moverA(new Posicion(POS_RETROCESO));
		Assert.assertEquals(POS_RETROCESO - (12 - 2), jugador.getPosicion().getValor());
	}
	
	@Test
	public void test12_RetrocesoDinamico_Tirada7_DesplazamientoJugadorIgualAEfectivoJugadorModuloTirada(){
		
		Juego juego = new Juego();
		Jugador jugador = juego.agregarJugador("ALF");
		int efectivo = jugador.getEfectivo();
		int tirada = 7;
		
		jugador.setUltimaTiradaDados(tirada);
		jugador.moverA(new Posicion(POS_RETROCESO));
		Assert.assertEquals(POS_RETROCESO - (efectivo % tirada), jugador.getPosicion().getValor());
	}
	
	@Test
	public void test12_RetrocesoDinamico_Tirada8_DesplazamientoJugadorIgualAEfectivoJugadorModuloTirada(){
		
		Juego juego = new Juego();
		Jugador jugador = juego.agregarJugador("ALF");
		int efectivo = jugador.getEfectivo();
		int tirada = 8;
		
		jugador.setUltimaTiradaDados(tirada);
		jugador.moverA(new Posicion(POS_RETROCESO));
		Assert.assertEquals(POS_RETROCESO - (efectivo % tirada), jugador.getPosicion().getValor());
	}
	
	@Test
	public void test12_RetrocesoDinamico_Tirada9_DesplazamientoJugadorIgualAEfectivoJugadorModuloTirada(){
		
		Juego juego = new Juego();
		Jugador jugador = juego.agregarJugador("ALF");
		int efectivo = jugador.getEfectivo();
		int tirada = 9;
		
		jugador.setUltimaTiradaDados(tirada);
		jugador.moverA(new Posicion(POS_RETROCESO));
		Assert.assertEquals(POS_RETROCESO - (efectivo % tirada), jugador.getPosicion().getValor());
	}
	
	@Test
	public void test12_RetrocesoDinamico_Tirada10_DesplazamientoJugadorIgualAEfectivoJugadorModuloTirada(){
		
		Juego juego = new Juego();
		Jugador jugador = juego.agregarJugador("ALF");
		int efectivo = jugador.getEfectivo();
		int tirada = 10;
		
		jugador.setUltimaTiradaDados(tirada);
		jugador.moverA(new Posicion(POS_RETROCESO));
		Assert.assertEquals(POS_RETROCESO - (efectivo % tirada), jugador.getPosicion().getValor());
	}
	
	@Test
	public void test13_RestrocesoDinamico_Tirada6_DesplazamientoJugadorIgualATiradaMenosSumatoriaDeSusPropiedades() {
		Juego juego = new Juego();
		Jugador jugador = juego.agregarJugador("Pepe");
		jugador.agregarPropiedad(new Tucuman(new Posicion(19)));
		int cantPropiedades = jugador.getSumaPropiedades();
		int tirada = 6;
		
		jugador.setUltimaTiradaDados(tirada);
		jugador.moverA(new Posicion(POS_RETROCESO));
		Assert.assertEquals(POS_RETROCESO - (tirada - cantPropiedades), jugador.getPosicion().getValor());
	}
	
	@Test
	public void test13_RestrocesoDinamico_Tirada5_DesplazamientoJugadorIgualATiradaMenosSumatoriaDeSusPropiedades() {
		Juego juego = new Juego();
		Jugador jugador = juego.agregarJugador("Pepe");
		jugador.agregarPropiedad(new Tucuman(new Posicion(19)));
		int cantPropiedades = jugador.getSumaPropiedades();
		int tirada = 5;
		
		jugador.setUltimaTiradaDados(tirada);
		jugador.moverA(new Posicion(POS_RETROCESO));
		Assert.assertEquals(POS_RETROCESO - (tirada - cantPropiedades), jugador.getPosicion().getValor());
	}
	
	@Test
	public void test13_RestrocesoDinamico_Tirada4_DesplazamientoJugadorIgualATiradaMenosSumatoriaDeSusPropiedades() {
		Juego juego = new Juego();
		Jugador jugador = juego.agregarJugador("Pepe");
		jugador.agregarPropiedad(new Tucuman(new Posicion(19)));
		int cantPropiedades = jugador.getSumaPropiedades();
		int tirada = 4;
		
		jugador.setUltimaTiradaDados(tirada);
		jugador.moverA(new Posicion(POS_RETROCESO));
		Assert.assertEquals(POS_CARCEL, jugador.getPosicion().getValor());
	}
	
	@Test
	public void test13_RestrocesoDinamico_Tirada3_DesplazamientoJugadorIgualATiradaMenosSumatoriaDeSusPropiedades() {
		Juego juego = new Juego();
		Jugador jugador = juego.agregarJugador("Pepe");
		jugador.agregarPropiedad(new Tucuman(new Posicion(19)));
		int cantPropiedades = jugador.getSumaPropiedades();
		int tirada = 3;
		
		jugador.setUltimaTiradaDados(tirada);
		jugador.moverA(new Posicion(POS_RETROCESO));
		Assert.assertEquals(POS_RETROCESO - (tirada - cantPropiedades), jugador.getPosicion().getValor());
	}
	
	@Test
	public void test13_RestrocesoDinamico_Tirada2_DesplazamientoJugadorIgualATiradaMenosSumatoriaDeSusPropiedades() {
		Juego juego = new Juego();
		Jugador jugador = juego.agregarJugador("Pepe");
		jugador.agregarPropiedad(new Tucuman(new Posicion(19)));
		int cantPropiedades = jugador.getSumaPropiedades();
		int tirada = 2;
		
		jugador.setUltimaTiradaDados(tirada);
		jugador.moverA(new Posicion(POS_RETROCESO));
		Assert.assertEquals(POS_RETROCESO - (tirada - cantPropiedades), jugador.getPosicion().getValor());
	}
	
	@Test
	public void test14_Policia_JugadorEsMovidoACarcelNoSeMueve() {
		
		Juego juego = new Juego();
		Jugador jugador = juego.agregarJugador("kevin");
		
		jugador.moverA(new Posicion(POS_POLICIA));
		Assert.assertEquals(POS_CARCEL, jugador.getPosicion().getValor());
		
		jugador.avanzar(jugador.tirarDados());
		Assert.assertEquals(POS_CARCEL, jugador.getPosicion().getValor());
	}

	
}
