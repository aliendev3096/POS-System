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
	private List<Map<String, Item>> itemList;
	private double total;
	
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
	
	public List<Map<String, Item>> getItemList() {
		if(itemList == null) {
			itemList = new ArrayList<Map<String, Item>>();
		}
		return itemList;
	}
	
	public void setItemList(List<Map<String, Item>> itemList) {
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
			this.status = Status.ACTIVE;
			Map<String, Item> itemToAdd = new HashMap<String, Item>();
			itemToAdd.put(item.getName(), item);
			this.itemList.add(itemToAdd); // add the item to the sales list
			setTotal(getTotal() + item.getPrice()); // adjust the sales total price
			item.setOnHandQuantity(item.getOnHandQuantity() - 1); // adjust the item onHandQuantity
		}
	}
	
	public void voidItem(Item item) {
		for(Map<String, Item> itemMap : this.itemList) {
			if(itemMap.containsKey(item.getName())) { // Item found in the sales list
				this.itemList.remove(itemMap); // remove the item from the sales list
				setTotal(getTotal() - item.getPrice()); // adjust the sales total price
				item.setOnHandQuantity(item.getOnHandQuantity() + 1); // adjust the item onHandQuantity
				break;
			}
		}
	}
	
	public void voidSales() {
		List<Map<String, Item>> salesList = this.itemList;
		for(Map<String, Item> itemMap : salesList) {
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
	
	public Refund returnItem(Item item) {
		Refund refund = new Refund();
		for(Map<String, Item> itemMap : this.itemList) {
			if(itemMap.containsKey(item.getName())) { // Item found in the sales list
				this.itemList.remove(itemMap); // remove the item from the sales list
				setTotal(getTotal() - item.getPrice()); // adjust the sales total price
				item.setOnHandQuantity(item.getOnHandQuantity() + 1); // adjust the item onHandQuantity
				
				refund.setSalesId(this.saleId);
				refund.setItemName(item.getName());
				refund.setRefundAmount(item.getPrice());
				break;
			}
		}
		this.status = Status.RETURNED;
		return refund;
	}
}
