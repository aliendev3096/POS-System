package main.seis602.pos.register;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import main.seis602.pos.inventory.Inventory;
import main.seis602.pos.inventory.Item;
import main.seis602.pos.report.CashierReport;
import main.seis602.pos.report.InventoryReport;
import main.seis602.pos.report.ReportAbstract;

public class Register 
{
	private static int identifier = 45467;
	private int registerId;
	private Cashier cashier;
	private Sale activeSale;
	private double totalSales;
	private Inventory inventory;
	private CompletedSales completedSales;
	
	public Register(Cashier cashier)
	{
		// set the cashier
		this.cashier = cashier;
		// apply system wide unique id to register
		registerId = identifier;
		//increment identifier for all registers
		identifier++;
		// load inventory upon spinning up a new register
		inventory = Inventory.getSingleton();
		completedSales = CompletedSales.getSingleton();
	}
	
	public Register() {
		
	}
	
	public Inventory getInventory()
	{
		return this.inventory;
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

	public List<Map<Cashier, Sale>> getSales() {
		return completedSales.getCompletedSalesList();
	}

	public Cashier getCashier() {
		return cashier;
	}

	public int getRegisterId() {
		return registerId;
	}
	
	public void addItem(String itemName, int qty) throws Exception
	{
		Item item = inventory.subtractItemQuantity(itemName,  qty);
		this.activeSale.addItem(item);
	}
	
	public void removeItem(String itemName) throws Exception
	{
		this.activeSale.voidItem(itemName);
	}
	
	public void cancelSale() throws Exception
	{
		
		this.activeSale.voidSale();
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
		if(activeSale == null) 
		{
			// throw exception if we have do not have an active sale currently
			throw new Exception("No Active Sale to Complete \n");
		}
		// check for non active sale
		if(activeSale.getStatus() != Status.ACTIVE)
		{			
			// throw exception if we have do not have an active sale currently
			throw new Exception("Current sale is no longer active, there is no sale to complete \n");
		}
		
		//set activeSale status to complete
		this.activeSale.setStatus(Status.COMPLETED);
		
		//add activeSale to archived sales
		Map<Cashier, Sale> completedSale = new HashMap<>();
		completedSale.put(this.cashier, this.activeSale);
		this.completedSales.addCompletedSale(completedSale);
		
		// Add Total Sale Amount
		this.totalSales += this.activeSale.getTotal();
		
	}
	
	public Refund returnSale(int saleId) throws Exception
	{
		Refund refund = new Refund();

		Map<Cashier, Sale> saleMap = completedSales.getSaleById(saleId);
		
		Sale  saleToReturn = null;
		for (Map.Entry<Cashier, Sale> entry : saleMap.entrySet()) {
			saleToReturn = entry.getValue();
	
		}
		
		//check for existing sale
		if(saleToReturn == null)
		{
			// throw exception if sale does not exist on registerId
			throw new Exception(String.format("Sale of sale id %s does not exist on this register: %s", saleId, registerId));
		}
		
		double saleTotal = saleToReturn.getTotal();
		// Set Sale Status as Returned
		saleToReturn .setStatus(Status.RETURNED);
		
		// return all items in sale marking it respectively
		for(Item item : saleToReturn .getItemList())
		{
			// Return Item in Sale
			saleToReturn.returnItem(item);
			// Set Refund for all items
			refund.addItem(item.getName());
			refund.setRefundAmount(refund.getRefundAmount() + item.getPrice());
			inventory.addItemQuantity(item.getName(), 1);
		}
		
		// Reduce total sales amount but returned sale amount
		this.totalSales -= saleTotal;
		refund.setSaleId(saleId);
		
		return refund;
	}
	
	public Refund returnSaleItem(String itemName, int saleId) throws Exception
	{
		Refund refund = new Refund();
		
		Map<Cashier, Sale> saleMap = completedSales.getSaleById(saleId);
		Sale  sale = null;
		for (Map.Entry<Cashier, Sale> entry : saleMap.entrySet()) {
			sale = entry.getValue();
	
		}

		// get Item from Sales Item List
		Item item = sale.getItem(itemName, ItemStatus.ACTIVE);
		
		//check for existing item in sale
		if(item == null)
		{
			// throw exception if item does not exist on sale
			throw new Exception(String.format("Item of item id %s does not exist on in sale: %s", itemName, saleId));
		}
		// return item relative to the sale
		boolean returnedSuccess = sale.returnItem(item);
		
		if(returnedSuccess)
		{
			refund.setSaleId(sale.getSaleId());
			refund.addItem(item.getName());
			refund.setRefundAmount(item.getPrice());
			inventory.addItemQuantity(item.getName(), 1);
		}
		this.totalSales -= item.getPrice();
		
		return refund;
	}
	
	public Map<Cashier, Sale> getSaleMapById(int saleId) {
		return completedSales.getSaleById(saleId);
	}
	
	public boolean getReOrderFlag() {
		return inventory.getReOrderFlag();
	}
	
	public boolean getPendingOrderFlag() {
		return inventory.getPendingOrderFlah();
	}
	
	public void reOrderItem()
	{
		inventory.reOrder();
	}
	
	public void moveItemToInventory() 
	{
		inventory.moveItemToInventory();
	}
	
	public void printInventoryReport()
	{
		ReportAbstract report = new InventoryReport();
		report.printReport();
		
	}
	
	public void printCashierReport()
	{
		ReportAbstract report = new CashierReport();
		report.printReport();
	}
	
	
}
