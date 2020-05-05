package main.seis602.pos.register;

import java.util.ArrayList;
import java.util.List;

import main.seis602.pos.inventory.Inventory;
import main.seis602.pos.inventory.Item;

public class Register 
{
	private static int identifier = 0;
	private int registerId;
	private Cashier cashier;
	private List<Sale> sales;
	private Sale activeSale;
	private double totalSales;
	private Inventory inventory;
	
	public Register(Cashier cashier)
	{
		// set the cashier
		this.cashier = cashier;
		// apply system wide unique id to register
		registerId = identifier;
		//increment identifier for all registers
		identifier++;
		// seed collection of sales
		sales = new ArrayList<Sale>();
		// TODO: load inventory upon spinning up a new register
	}

	public double getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(double totalSales) {
		this.totalSales = totalSales;
	}

	public Sale getActiveSale() {
		return activeSale;
	}

	public void setActiveSale(Sale activeSale) {
		this.activeSale = activeSale;
	}

	public List<Sale> getSales() {
		return sales;
	}

	public Cashier getCashier() {
		return cashier;
	}

	public int getRegisterId() {
		return registerId;
	}
	
	public void createSale(Sale newSale) throws Exception
	{
		// check for active sale
		if(activeSale.Status == Status.Active)
		{
			// throw exception if we have an active sales already
			throw new Exception("Current Sale is still active");
		}
		
		this.activeSale = newSale;
	}
	
	public void completeSale() throws Exception
	{
		// check for non active sale
		if(activeSale.Status != Status.Active)
		{			
			// throw exception if we have do not have an active sale currently
			throw new Exception("Current active sale is not active, there is no sale to complete");
		}
		
		//set activeSale status to complete
		this.activeSale.setStatus(Status.Complete);
		
		//add activeSale to archived sales
		this.sales.add(this.activeSale);
	}
	
	public void returnSale(int saleId) throws Exception
	{
		// Use a stream to find the sale with saleId from the collection of sales
		// Note: It would be nice to have the list of sales as static but we'd have to figure out a way to 
		// update the total sales property on the appropriate register without the use of a database which could
		// potentially get messy. For simplicity sake the list of sales will be respective to its own class
		Sale sale = sales.stream()
				.filter(s -> s.saleId == saleId)
				.findFirst()
				.orElse(null);
		//check for existing sale
		if(sale == null)
		{
			// throw exception if sale does not exist on registerId
			throw new Exception(String.format("Sale of sale id %s does not exist on this register: %s", saleId, registerId));
		}
		
		// Set Sale Status as Returned
		sale.setStatus = Status.Returned;
		
		// return all items in sale to inventory
		for(Item item: sales.getItems())
		{
			sale.returnItem(item.itemId);
		}
		
		// Reduce total sales amount but returned sale amount
		this.totalSales -= sale.getTotal();
	}
	
	public void returnSaleItem(int itemId, int saleId) throws Exception
	{
		// Get respective sale
		Sale sale = sales.stream()
				.filter(s -> s.saleId == saleId)
				.findFirst()
				.orElse(null);
		//check for existing sale
		if(sale == null)
		{
			// throw exception if sale does not exist on registerId
			throw new Exception(String.format("Sale of sale id %s does not exist on this register: %s", saleId, registerId));
		}
		// get Item from Sales Item List
		Item item = sale.getItems().stream()
				.filter(s -> s.itemId == itemId)
				.findFirst()
				.orElse(null);
		
		//check for existing item in sale
		if(sale == null)
		{
			// throw exception if item does not exist on sale
			throw new Exception(String.format("Item of item id %s does not exist on in sale: %s", item.getItemId(), saleId));
		}
		// return item
		sale.returnItem(itemId);
	}
	
	public void printInventoryReport()
	{
		
	}
	
	public void printCashierReport()
	{
		
	}
	
	
}
