package test.seis602.pos;

import java.util.HashMap;
import java.util.Map;

import org.junit.*;

import main.seis602.pos.register.Cashier;
import main.seis602.pos.register.CompletedSales;
import main.seis602.pos.register.Sale;

public class CompletedSalesTest {
	@Test
	public void Add_CompletedSales() throws Exception
	{
		Map<Cashier, Sale> completedSale = new HashMap<Cashier, Sale>();
		completedSale.put(new Cashier("cFirst", "cLast", 1), new Sale());
		CompletedSales cSales = CompletedSales.getSingleton();
		cSales.addCompletedSale(completedSale);
		Assert.assertEquals(1, cSales.getCompletedSalesList().size());
	}
	
	@Test
	public void Get_CompletedSales_By_Sale_Id() throws Exception
	{
		Map<Cashier, Sale> completedSale = new HashMap<Cashier, Sale>();
		Sale sale = new Sale();
		Cashier cashier = new Cashier("cFirst", "cLast", 1);
		completedSale.put(cashier, sale);
		CompletedSales cSales = CompletedSales.getSingleton();
		cSales.addCompletedSale(completedSale);
		Assert.assertEquals(sale.getSaleId(), cSales.getSaleById(sale.getSaleId()).get(cashier).getSaleId());
	}
	

}
