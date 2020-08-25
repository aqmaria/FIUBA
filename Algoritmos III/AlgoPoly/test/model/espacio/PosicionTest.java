package model.espacio;

import org.junit.Assert;
import org.junit.Test;

public class PosicionTest {
	@Test
	public void testCrearPosicionSinEspecificar() {
		Posicion posicion = new Posicion();
		Assert.assertNotNull(posicion);
	}
	
	@Test
	public void testCrearPosicionEspecifica() {
		Posicion posicion = new Posicion(8);
		Assert.assertNotNull(posicion);
	}
	
	@Test
	public void testAvanzarPosicion() {
		Posicion posicion = new Posicion();
		posicion.avanzar(5);
		Assert.assertEquals(5, posicion.getValor());
	}
	
	@Test
	public void testRetrocederPosicion() {
		Posicion posicion = new Posicion(10);
		posicion.retroceder(5);
		Assert.assertEquals(5, posicion.getValor());
	}
	
	@Test
	public void testAvanzarPosicionMasQue19() {
		Posicion posicion = new Posicion();
		posicion.avanzar(21);
		Assert.assertEquals(1, posicion.getValor());
	}
	
	@Test
	public void testAvanzarPosicionMasQue0() {
		Posicion posicion = new Posicion();
		posicion.retroceder(11);
		Assert.assertEquals(9, posicion.getValor());
	}
	
}
