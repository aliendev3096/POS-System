package main.seis602.pos.register;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import main.seis602.pos.inventory.Inventory;
import main.seis602.pos.inventory.Item;

public class Register 
{
	private static int identifier = 45467;
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
		if(activeSale != null) 
		{
			// check for active sale
			if(activeSale.getStatus() == Status.ACTIVE)
			{
				// throw exception if we have an active sales already
				throw new Exception("Current Sale is still active");
			}
		}
		
		this.activeSale = newSale;
	}
	
	public void completeSale() throws Exception
	{
		if(activeSale != null) 
		{
			// throw exception if we have do not have an active sale currently
			throw new Exception("No Active Sale to Complete");
		}
		// check for non active sale
		if(activeSale.getStatus() != Status.ACTIVE)
		{			
			// throw exception if we have do not have an active sale currently
			throw new Exception("Current active sale is not active, there is no sale to complete");
		}
		
		//set activeSale status to complete
		this.activeSale.setStatus(Status.COMPLETED);
		
		//add activeSale to archived sales
		this.sales.add(this.activeSale);
	}
	
	public void returnSale(Sale sale) throws Exception
	{
		// Use a stream to find the sale with saleId from the collection of sales
		// Note: It would be nice to have the list of sales as static but we'd have to figure out a way to 
		// update the total sales property on the appropriate register without the use of a database which could
		// potentially get messy. For simplicity sake the list of sales will be respective to its own class
		Sale saleToReturn = sales.stream()
				.filter(s -> s.getSaleId() == sale.getSaleId())
				.findFirst()
				.orElse(null);
		//check for existing sale
		if(saleToReturn == null)
		{
			// throw exception if sale does not exist on registerId
			throw new Exception(String.format("Sale of sale id %s does not exist on this register: %s", sale.getSaleId(), registerId));
		}
		
		// Set Sale Status as Returned
		saleToReturn.setStatus(Status.RETURNED);
		
		// return all items in sale to inventory and mark it respectively in its sale
		for(Map<ItemStatus, Item> item : saleToReturn.getItemList())
		{
			saleToReturn.returnItem(item.get(ItemStatus.ACTIVE));
			//inventory.add(item);
		}
		
		// Reduce total sales amount but returned sale amount
		this.totalSales -= saleToReturn.getTotal();
	}
	
	public Refund returnSaleItem(String itemName, int saleId) throws Exception
	{
		Refund refund = new Refund();
		// Get respective sale
		Sale sale = sales.stream()
				.filter(s -> s.getSaleId() == saleId)
				.findFirst()
				.orElse(null);
		//check for existing sale
		if(sale == null)
		{
			// throw exception if sale does not exist on registerId
			throw new Exception(String.format("Sale of sale id %s does not exist on this register: %s", saleId, registerId));
		}
		// get Item from Sales Item List
		Item item = sale.getItem(itemName);
		
		//check for existing item in sale
		if(item == null)
		{
			// throw exception if item does not exist on sale
			throw new Exception(String.format("Item of item id %s does not exist on in sale: %s", item.getName(), saleId));
		}
		// return item
		boolean returnedSuccess = sale.returnItem(item);
		
		if(returnedSuccess)
		{
			refund.setSalesId(sale.getSaleId());
			refund.setItemName(item.getName());
			refund.setRefundAmount(item.getPrice());
		}
		return refund;
	}
	
	public int getAmountOfSale()
	{
		return this.sales.size();
	}
	
	public void printInventoryReport()
	{
		
	}
	
	public void printCashierReport()
	{
		
	}
	
	
}
