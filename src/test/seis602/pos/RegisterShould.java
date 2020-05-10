package test.seis602.pos;

import org.junit.*;

import main.seis602.pos.register.Cashier;
import main.seis602.pos.register.Refund;
import main.seis602.pos.register.Register;
import main.seis602.pos.register.Sale;
import main.seis602.pos.register.Status;

public class RegisterShould {

	@Test
	public void ReturnSaleItem() throws Exception
	{

		Register register = new Register(new Cashier("Test", "User", 1));
		
		register.createSale(new Sale());
		int saleId = register.getActiveSale().getSaleId();
		register.addItem("Orange");
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
		register.addItem("Orange");
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
		register.addItem("Orange");
		register.addItem("Orange");
		
		register.completeSale();

		Assert.assertTrue(register.getSales().get(0).getSaleId() == saleId);
		Assert.assertEquals(7.00, register.getTotalSales(), .01);
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
		Register register = new Register(new Cashier("Test", "User", 1));
		
		register.createSale(new Sale());
		int saleId = register.getActiveSale().getSaleId();
		register.addItem("Orange");
		register.addItem("Orange");
		
		register.cancelSale();

		Assert.assertTrue(register.getSales().size() == 0);
		Assert.assertEquals(0.00, register.getActiveSale().getTotal(), .01);
		Assert.assertTrue(register.getActiveSale().getStatus() == Status.CANCELED);
	}
}
