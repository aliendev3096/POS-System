package main.seis602.pos.register;

import org.junit.*;

public class RegisterShould {

	@Test
	public void ReturnSaleItem() throws Exception
	{

		Register register = new Register(new Cashier("Test", "User"));
		
		register.createSale(new Sale());
		int saleId = register.getActiveSale().getSaleId();
		Assert.assertEquals(1,  register.getAmountOfSales());
		register.addItem("Orange");
		register.completeSale();

		Refund refund = register.returnSaleItem("Orange", saleId);
		
		Assert.assertEquals(2.99, refund.getRefundAmount(), .01);
		Assert.assertEquals(0.00, register.getTotalSales(), .01);
		Assert.assertTrue(refund.getItems().contains("orange"));
		Assert.assertEquals(saleId, refund.getSaleId());
	}
	
	@Test
	public void ReturnSale() throws Exception
	{
		Register register = new Register(new Cashier("Test", "User"));
		
		register.createSale(new Sale());
		int saleId = register.getActiveSale().getSaleId();
		Assert.assertEquals(1,  register.getAmountOfSales());
		register.addItem("Orange");
		register.completeSale();

		Refund refund = register.returnSale(saleId);
		
		Assert.assertEquals(2.99, refund.getRefundAmount(), .01);
		Assert.assertEquals(0.00, register.getTotalSales(), .01);
		Assert.assertTrue(refund.getItems().contains("orange"));
		Assert.assertEquals(saleId, refund.getSaleId());
	}
	
	@Test
	public void CompleteSale() throws Exception
	{
		Register register = new Register(new Cashier("Test", "User"));
		
		register.createSale(new Sale());
		int saleId = register.getActiveSale().getSaleId();
		Assert.assertEquals(1,  register.getAmountOfSales());
		register.addItem("Orange");
		register.addItem("Orange");
		
		register.completeSale();

		Assert.assertTrue(register.getSales().get(0).getSaleId() == saleId);
		Assert.assertEquals(4.98, register.getTotalSales(), .01);
		Assert.assertTrue(register.getActiveSale().getStatus() == Status.COMPLETED);
	}
	
	@Test
	public void AddItem() throws Exception
	{

	}
	
	@Test
	public void RemoveItem() throws Exception
	{

	}
	
	@Test
	public void CancelSale() throws Exception
	{
		Register register = new Register(new Cashier("Test", "User"));
		
		register.createSale(new Sale());
		int saleId = register.getActiveSale().getSaleId();
		Assert.assertEquals(1,  register.getAmountOfSales());
		register.addItem("Orange");
		register.addItem("Orange");
		
		register.cancelSale();

		Assert.assertFalse(register.getSales().get(0).getSaleId() == saleId);
		Assert.assertEquals(0.00, register.getActiveSale().getTotal(), .01);
		Assert.assertTrue(register.getActiveSale().getStatus() == Status.CANCELED);
	}
}
