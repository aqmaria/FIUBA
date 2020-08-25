package model.casillero.propiedad;

import org.junit.Assert;
import org.junit.Test;

public class EdificacionesTest {
	@Test
	public void test_edificacionesVaciaCantidad0() {
		Edificaciones edificaciones = new Edificaciones(100);
		
		Assert.assertEquals(0, edificaciones.getCantidad());
	}
	
	
}
