package test.seis602.pos;

import org.junit.*;

import main.seis602.pos.inventory.Item;

public class ItemTest {
	
	@Test
	public void Initialize_Item() throws Exception
	{

		Item item = new Item("Orange", 3.99, "SuperValue", 3, 5);
		
		Assert.assertEquals(3.99, item.getPrice(), .01);
		Assert.assertEquals(5, item.getMaxOnHandQuantity());
		Assert.assertEquals(5, item.getOnHandQuantity());
		Assert.assertEquals(3, item.getThreshold());
		Assert.assertEquals(false, item.getReOrder());
		Assert.assertEquals("SuperValue", item.getSupplier());
		Assert.assertEquals("Orange", item.getName());
	}
	
}
