package model.juego;

import org.junit.Assert;
import org.junit.Test;

public class TiradaTest {

	@Test
	public void testCrearTirada() {
		TiradaDados tirada = new TiradaDados();
		Assert.assertNotNull(tirada);
	}

	@Test
	public void testDadosNoSacanSiempreNumerosIguales() {
		TiradaDados tirada = new TiradaDados();
		Dado dado = new Dado(1, 6);
		
		tirada.agregarDado(dado);
		tirada.agregarDado(dado);
		
		boolean sacoNumerosDiferentes = false;
		
		for (int i = 0; i < 1000; i++) {
			if(tirada.tirar() % 2 == 1)
				sacoNumerosDiferentes = true;
		}
		
		Assert.assertTrue(sacoNumerosDiferentes);
	}
	
	@Test
	public void testTiradaRespetaRango() {
		TiradaDados tiradaDados = new TiradaDados();
		Dado dado = new Dado(1, 6);
		
		tiradaDados.agregarDado(dado);
		tiradaDados.agregarDado(dado);
		
		int tirada = 0;
		boolean respeta = true;
		
		for(int i = 0; i < 1000; i++) {
			tirada = tiradaDados.tirar();
			if(tirada < 2 || tirada > 12)
				respeta = false;
		}
		Assert.assertTrue(respeta);
	}
	
	@Test
	public void testDadosTienenMismaPosibilidadDeSacarNumerosDiferentesOIguales() {
		TiradaDados tirada = new TiradaDados();
		Dado dado = new Dado(1, 6);
		
		tirada.agregarDado(dado);
		tirada.agregarDado(dado);
		
		int vecesNumerosDiferentes = 0;
		int cantTiradas = 10000;
		
		for (int i = 0; i < cantTiradas; i++) {
			if(tirada.tirar() % 2 == 1)
				vecesNumerosDiferentes += 1;
		}
		
		int porcentajeNumerosDiferentes = (int)(((double)vecesNumerosDiferentes / (double)cantTiradas) * 100);
		
		Assert.assertTrue(porcentajeNumerosDiferentes > 0);
		Assert.assertTrue(porcentajeNumerosDiferentes > 5);
		Assert.assertTrue(porcentajeNumerosDiferentes > 10);
		Assert.assertTrue(porcentajeNumerosDiferentes > 15);
		Assert.assertTrue(porcentajeNumerosDiferentes > 20);
		Assert.assertTrue(porcentajeNumerosDiferentes > 25);
		Assert.assertTrue(porcentajeNumerosDiferentes > 30);
		Assert.assertTrue(porcentajeNumerosDiferentes > 35);
		Assert.assertTrue(porcentajeNumerosDiferentes > 40);
		
		Assert.assertTrue(porcentajeNumerosDiferentes < 100);
		Assert.assertTrue(porcentajeNumerosDiferentes < 95);
		Assert.assertTrue(porcentajeNumerosDiferentes < 90);
		Assert.assertTrue(porcentajeNumerosDiferentes < 85);
		Assert.assertTrue(porcentajeNumerosDiferentes < 80);
		Assert.assertTrue(porcentajeNumerosDiferentes < 75);
		Assert.assertTrue(porcentajeNumerosDiferentes < 70);
		Assert.assertTrue(porcentajeNumerosDiferentes < 65);
		Assert.assertTrue(porcentajeNumerosDiferentes < 60);
	}
}
