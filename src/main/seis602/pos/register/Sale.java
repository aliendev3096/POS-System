package main.seis602.pos.register;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import main.seis602.pos.inventory.Item;

public class Sale 
{
	private static int identifier = 5563;
	private int saleId;
	private Status status;
	private Date date;
	private List<Map<ItemStatus, Item>> itemList;
	private double total;
	
	public Sale()
	{
		this.status = Status.ACTIVE;	
		saleId = identifier;
		identifier++;
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
	
	public List<Map<ItemStatus, Item>> getItemList() {
		if(itemList == null) {
			itemList = new ArrayList<Map<ItemStatus, Item>>();
		}
		return itemList;
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
			setTotal(getTotal() + item.getPrice()); // adjust the sales total price
			item.setOnHandQuantity(item.getOnHandQuantity() - 1); // adjust the item onHandQuantity
		}
	}
	
	public boolean voidItem(Item item) {
		boolean isVoided = false;
		for(Map<ItemStatus, Item> itemMap : this.itemList) {
			if(itemMap.containsKey(ItemStatus.ACTIVE) && itemMap.containsValue(item)) { // Item found in the sales list
				itemMap.remove(ItemStatus.ACTIVE, item); // remove the item from the sales list
				itemMap.put(ItemStatus.VOID, item); // add item to the sales list with returned status
				setTotal(getTotal() - item.getPrice()); // adjust the sales total price
				item.setOnHandQuantity(item.getOnHandQuantity() + 1); // adjust the item onHandQuantity
				
				isVoided = true;
				break;
			}
		}
		
		return isVoided;
	}
	// TODO: maybe we don't need this method and just let the register handle all returns/voids
	public void voidSales() {
		List<Map<ItemStatus, Item>> itemSaleList = this.itemList;
		// Return all active items to inventory
		for(Map<ItemStatus, Item> itemMap : itemSaleList) {
			// We only care if the item is ACTIVE, otherwise its probably 
			// already been returned to inventory
			Item item = itemMap.get(ItemStatus.ACTIVE);
			if(item != null)
			{
				//Update Sale List
				returnItem(item);
				// Update inventory
			}
		}
		this.itemList = null;
		this.total = 0;
		this.status = Status.CANCELED;
	}
	
	public boolean returnItem(Item item) {
		boolean isReturned = false;
		for(Map<ItemStatus, Item> itemMap : this.itemList) {
			if(itemMap.containsKey(ItemStatus.ACTIVE) && itemMap.containsValue(item)) { // Item found in the sales list
				itemMap.remove(ItemStatus.ACTIVE, item); // remove the item from the sales list
				itemMap.put(ItemStatus.RETURNED, item); // add item to the sales list with returned status
				setTotal(getTotal() - item.getPrice()); // adjust the sales total price
				item.setOnHandQuantity(item.getOnHandQuantity() + 1); // adjust the item onHandQuantity
				
				isReturned = true;
				break;
			}
		}
		return isReturned;
	}
	
	public Item getItem(String itemName)
	{
		// Get all active items
		ArrayList<Item> activeItems = new ArrayList<Item>();
		for(Map<ItemStatus, Item> itemMap : this.itemList) {
			if(itemMap.containsKey(ItemStatus.ACTIVE)) 
			{
				Item item = itemMap.get(ItemStatus.ACTIVE);
				// Found the item, return it.
				if(item.getName() == itemName)
				{
					return item;
				}
			}
		}
		// We did not find the item
		return null;
		
	}
}
