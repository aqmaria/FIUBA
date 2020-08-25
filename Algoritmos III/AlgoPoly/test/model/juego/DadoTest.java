package model.juego;

import org.junit.Assert;
import org.junit.Test;

public class DadoTest {

	@Test
	public void testCrearDado() {
		Dado dado = new Dado(1, 6);
		Assert.assertNotNull(dado);
	}

	@Test
	public void testDadoRespetaRango() {
		Dado dado = new Dado(1, 6);
		
		int tirada = 0;
		boolean respeta = true;
		
		for(int i = 0; i < 1000; i++) {
			tirada = dado.tirar();
			if(tirada < 1 || tirada > 6)
				respeta = false;
		}
		Assert.assertTrue(respeta);
	}
	
	
}
