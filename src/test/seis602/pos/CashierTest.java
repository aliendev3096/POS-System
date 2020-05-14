package test.seis602.pos;

import org.junit.*;
import main.seis602.pos.register.Cashier;

public class CashierTest {
	
	@Test
	public void Initialize_Cashier() throws Exception
	{

		Cashier cashier = new Cashier("UserFirstName", "UserLastName", 555);
		
		Assert.assertEquals(555, cashier.getCashierId());
		Assert.assertEquals("UserFirstName", cashier.getFirstName());
		Assert.assertEquals("UserLastName", cashier.getLastName());
	}
	
}
