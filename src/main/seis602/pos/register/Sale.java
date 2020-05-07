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
	private int saleId;
	private Status status;
	private Date date;
	private List<Map<ItemStatus, Item>> itemList;
	private double total;
	
	public Sale()
	{
		this.status = Status.ACTIVE;
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
	
	public void voidItem(Item item) {
		for(Map<ItemStatus, Item> itemMap : this.itemList) {
			if(itemMap.containsKey(item.getName())) { // Item found in the sales list
				this.itemList.remove(itemMap); // remove the item from the sales list
				setTotal(getTotal() - item.getPrice()); // adjust the sales total price
				item.setOnHandQuantity(item.getOnHandQuantity() + 1); // adjust the item onHandQuantity
				break;
			}
		}
	}
	
	public void voidSales() {
		List<Map<ItemStatus, Item>> salesList = this.itemList;
		for(Map<ItemStatus, Item> itemMap : salesList) {
			Set<Entry<String, Item>> entrySet = itemMap.entrySet();
			for(Entry<String, Item> entry: entrySet) {
				Item item = entry.getValue();
				item.setOnHandQuantity(item.getOnHandQuantity() + 1);
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
}
