package main.seis602.pos.register;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import main.seis602.pos.inventory.Inventory;
import main.seis602.pos.inventory.Item;

public class Sale 
{
	private static int identifier = 5563;
	private int saleId;
	private Status status;
	private Date date;
	private List<Map<ItemStatus, Item>> itemList;
	private double total;
	private Inventory inventory;
	
	public Sale()
	{
		this.status = Status.ACTIVE;
		date = new Date();
		saleId = identifier;
		identifier++;
		itemList = new ArrayList<Map<ItemStatus, Item>>();
		inventory = Inventory.getSingleton();
	}
	
	public int getSaleId() {
		return saleId;
	}
	
	public void setSaleId(int salesId) {
		this.saleId = salesId;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public List<Item> getItemList() {
		ArrayList<Item> items = new ArrayList<Item>();
		if(itemList == null) {
			return items;
		}
		
		for(Map<ItemStatus, Item> itemMap : itemList)
		{
			Item item = itemMap.get(ItemStatus.ACTIVE);
			if(item != null)
			{
				items.add(item);
			}
		}
		return items;
	}
	
	public void setItemList(List<Map<ItemStatus, Item>> itemList) {
		this.itemList = itemList;
	}
	
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	
	public void addItem(Item item) {
		// check if the item is in stock
		int currentOnHandQty = item.getOnHandQuantity();
		
		// Add the item to the sales list, only if the item is in stock
		if(currentOnHandQty > 0) {
			Map<ItemStatus, Item> itemToAdd = new HashMap<ItemStatus, Item>();
			itemToAdd.put(ItemStatus.ACTIVE, item);
			this.itemList.add(itemToAdd); // add the item to the sales list
			this.total += item.getPrice(); // adjust the sales total price
			//item.setOnHandQuantity(item.getOnHandQuantity() - 1); // adjust the item onHandQuantity
			inventory.subtractItemQuantity(item.getName(), 1);
		}
	}
	
	public boolean voidItem(Item item) {
		boolean isVoided = false;
		for(Item itemInList : this.getItemList()) {
			// Item found in the sales list
			if(itemInList.getName() == item.getName()) { 
				// remove the item from the sales list
				this.itemList.remove(Map.of(ItemStatus.ACTIVE, item)); 
				// if the sale was already completed the we mark the item as returned
				// if the sale is still active, don't add the item back into the list
				// upon a cancellation of an item
				this.itemList.add(Map.of(ItemStatus.VOID, item));
				// adjust the sales total price
				this.total -= item.getPrice();
				// adjust the item onHandQuantity
				//item.setOnHandQuantity(item.getOnHandQuantity() + 1); 
				inventory.addItemQuantity(item.getName(), 1);
				isVoided = true;
				break;
			}
		}
		return isVoided;
	}

	public void voidSale() {
		// Return all active items to inventory
		for(Item item : this.getItemList()) {
			//Update Sale List
			voidItem(item);
		}
		
		this.total = 0;
		this.status = Status.CANCELED;
	}
	
	public boolean returnItem(Item item) {
		boolean isReturned = false;
		for(Item itemInList : this.getItemList()) {
			// Item found in the sales list
			if(itemInList.getName() == item.getName()) { 
				// remove the item from the sales list
				this.itemList.remove(Map.of(ItemStatus.ACTIVE, item)); 
				// if the sale was already completed the we mark the item as returned
				// if the sale is still active, don't add the item back into the list
				// upon a cancellation of an item
				this.itemList.add(Map.of(ItemStatus.RETURNED, item));
				// adjust the sales total price
				this.total -= item.getPrice();
				// adjust the item onHandQuantity
				//item.setOnHandQuantity(item.getOnHandQuantity() + 1); 
				inventory.addItemQuantity(item.getName(), 1);
				isReturned = true;
				break;
			}
		}
		return isReturned;
	}
	
	public Item getItem(String itemName, ItemStatus status)
	{
		for(Map<ItemStatus, Item> itemMap : this.itemList) {
			if(itemMap.containsKey(status)) 
			{
				Item item = itemMap.get(status);
				// Found the item, return it.
				if(item.getName().equalsIgnoreCase(itemName))
				{
					return item;
				}
			}
		}
		// We did not find the item
		return null;
	}
}
