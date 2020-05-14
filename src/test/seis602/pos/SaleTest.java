package test.seis602.pos;

import org.junit.*;

import main.seis602.pos.inventory.Item;
import main.seis602.pos.register.ItemStatus;
import main.seis602.pos.register.Sale;
import main.seis602.pos.register.Status;

public class SaleTest {

	@Test
	public void ReturnItemOnCompleteSale()
	{

		Sale newSale = new Sale();
		Item orangeItem = new Item("Orange", 2.99, null,3 ,3);
		
		newSale.addItem(new Item("Apple", 5.99, null,3,3));
		newSale.addItem(orangeItem);
		newSale.addItem(new Item("Chocolate", 3.99, null, 3,3));
		
		Assert.assertEquals(38.91, newSale.getTotal(), .01);
		newSale.setStatus(Status.COMPLETED);

		newSale.returnItem(orangeItem);
		
		Assert.assertEquals(35.92, newSale.getTotal(), .01);
		Assert.assertNotNull(newSale.getItem("Orange", ItemStatus.ACTIVE));
		Assert.assertNotNull(newSale.getItem("Orange", ItemStatus.RETURNED));
	}
	
	@Test
	public void VoidItemOnActiveSale()
	{
		// Arrange
		Sale newSale = new Sale();
		Item orangeItem = new Item("Orange", 2.99, null, 3, 3);
		
		newSale.addItem(new Item("Apple", 5.99, null, 3, 3));
		newSale.addItem(orangeItem);
		newSale.addItem(new Item("Chocolate", 3.99, null, 3, 3));
		
		Assert.assertEquals(38.91, newSale.getTotal(), .01);
		//Act 
		newSale.voidItem(orangeItem.getName());
		
		//Assert
		Assert.assertEquals(35.92, newSale.getTotal(), .01);
		Assert.assertNotNull(newSale.getItem("Orange", ItemStatus.ACTIVE));
		Assert.assertNotNull(newSale.getItem("Orange", ItemStatus.VOID));
	}
	
	@Test
	public void VoidSale()
	{
		// Arrange
		Sale newSale = new Sale();
		Item orangeItem = new Item("Orange", 2.99, null, 3, 3);
		
		newSale.addItem(new Item("Apple", 5.99, null, 3, 3));
		newSale.addItem(orangeItem);
		newSale.addItem(new Item("Chocolate", 3.99, null, 3, 3));
		
		Assert.assertEquals(38.91, newSale.getTotal(), .01);
		//Act 
		newSale.voidSale();
		
		//Assert
		Assert.assertEquals(0, newSale.getTotal(), .01);
		Assert.assertEquals(Status.CANCELED, newSale.getStatus());
		Assert.assertNull(newSale.getItem("Orange", ItemStatus.ACTIVE));
		Assert.assertNull(newSale.getItem("Apple", ItemStatus.ACTIVE));
		Assert.assertNull(newSale.getItem("Chocolate", ItemStatus.ACTIVE));
		Assert.assertNotNull(newSale.getItem("Orange", ItemStatus.VOID));
		Assert.assertNotNull(newSale.getItem("Apple", ItemStatus.VOID));
		Assert.assertNotNull(newSale.getItem("Chocolate", ItemStatus.VOID));
	}
}
