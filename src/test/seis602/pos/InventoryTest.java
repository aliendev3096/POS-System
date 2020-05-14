package test.seis602.pos;

import org.junit.*;

import main.seis602.pos.inventory.Inventory;
import main.seis602.pos.inventory.Item;


public class InventoryTest {
	
	@Test
	public void InventoryLifeCycle() throws Exception
	{

		Inventory inventory = Inventory.getSingleton();
		
		Assert.assertEquals(8, inventory.getInventoryList().size());
		Assert.assertNotNull(inventory.getItem("Orange"));
		
		inventory.add(new Item("Apple", 2.50, "SuperValue", 4, 10));
		Assert.assertEquals(8, inventory.getInventoryList().size());
		Assert.assertEquals(10, inventory.getItem("Apple").getOnHandQuantity());
		
		inventory.remove("Apple");

		Assert.assertEquals(7, inventory.getInventoryList().size());
		Assert.assertNull(inventory.getItem("Apple"));
		
		inventory.subtractItemQuantity("Orange", 5);
		Assert.assertEquals(5, inventory.getItem("Orange").getOnHandQuantity());
		
		inventory.addItemQuantity("Orange", 5);
		Assert.assertEquals(10, inventory.getItem("Orange").getOnHandQuantity());
	}

}