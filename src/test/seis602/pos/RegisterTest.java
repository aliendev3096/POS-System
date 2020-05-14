package test.seis602.pos;

import org.junit.*;

import main.seis602.pos.register.Cashier;
import main.seis602.pos.register.Refund;
import main.seis602.pos.register.Register;
import main.seis602.pos.register.Sale;
import main.seis602.pos.register.Status;

public class RegisterTest {

	@Test
	public void ReturnSaleItem() throws Exception
	{

		Register register = new Register(new Cashier("Test", "User", 1));
		
		register.createSale(new Sale());
		int saleId = register.getActiveSale().getSaleId();
		register.addItem("Orange", 1);
		register.completeSale();

		Refund refund = register.returnSaleItem("Orange", saleId);
		
		Assert.assertEquals(3.50, refund.getRefundAmount(), .01);
		Assert.assertEquals(0.00, register.getTotalSales(), .01);
		Assert.assertFalse(refund.getItems().contains("orange"));
		Assert.assertEquals(saleId, refund.getSaleId());
	}
	
	@Test
	public void ReturnSale() throws Exception
	{
		Register register = new Register(new Cashier("Test", "User", 1));
		
		register.createSale(new Sale());
		int saleId = register.getActiveSale().getSaleId();
		register.addItem("Orange", 1);
		register.completeSale();

		Refund refund = register.returnSale(saleId);
		
		Assert.assertEquals(3.50, refund.getRefundAmount(), .01);
		Assert.assertEquals(0.00, register.getTotalSales(), .01);
		Assert.assertTrue(refund.getItems().contains("Orange"));
		Assert.assertEquals(saleId, refund.getSaleId());
	}
	
	@Test
	public void CompleteSale() throws Exception
	{
		Register register = new Register(new Cashier("Test", "User", 1));
		
		register.createSale(new Sale());
		int saleId = register.getActiveSale().getSaleId();
		register.addItem("Orange", 1);
		register.addItem("Orange", 1);
		
		register.completeSale();

		//Assert.assertTrue(register.getSales().get(0).getSaleId() == saleId);
		Assert.assertEquals(7.00, register.getTotalSales(), .01);
		Assert.assertTrue(register.getActiveSale().getStatus() == Status.COMPLETED);
	}
	
	@Test
	public void AddItem() throws Exception
	{
		Register register = new Register(new Cashier("Test", "User", 1));
		register.createSale(new Sale());
		register.addItem("Apple", 1);
		register.addItem("Apple", 1);
		
		Assert.assertEquals(8, register.getInventory().getItem("Apple").getOnHandQuantity());
		Assert.assertFalse(register.getInventory().getItem("Apple").getReOrder());
		
		register.addItem("Apple", 1);
		register.addItem("Apple", 1);
		register.addItem("Apple", 1);
		
		Assert.assertEquals(5, register.getInventory().getItem("Apple").getOnHandQuantity());
		register.addItem("Apple", 1);
		
		Assert.assertEquals(4, register.getInventory().getItem("Apple").getOnHandQuantity());
		Assert.assertTrue(register.getInventory().getItem("Apple").getReOrder());
	}
	
	@Test
	public void RemoveItem() throws Exception
	{
		Register register = new Register(new Cashier("Test", "User", 1));
		register.createSale(new Sale());
		register.addItem("Kiwi", 1);
		register.addItem("Kiwi", 1);
		
		Assert.assertEquals(8, register.getInventory().getItem("Kiwi").getOnHandQuantity());
		Assert.assertFalse(register.getInventory().getItem("Kiwi").getReOrder());
		
		register.removeItem("Kiwi");
		register.removeItem("Kiwi");
		
		Assert.assertEquals(10, register.getInventory().getItem("Kiwi").getOnHandQuantity());
		Assert.assertFalse(register.getInventory().getItem("Kiwi").getReOrder());
	}
	
	@Test
	public void CancelSale() throws Exception
	{
		Register register = new Register(new Cashier("Test", "User", 1));
		
		register.createSale(new Sale());
		register.addItem("Steak", 1);
		register.addItem("Steak", 1);
		
		register.cancelSale();

		Assert.assertTrue(register.getSales().size() == 1);
		Assert.assertEquals(0.00, register.getActiveSale().getTotal(), .01);
		Assert.assertTrue(register.getActiveSale().getStatus() == Status.CANCELED);
	}
}
