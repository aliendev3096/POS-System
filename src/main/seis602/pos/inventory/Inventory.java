package main.seis602.pos.inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.seis602.pos.inventory.Item;
import java.io.File;
import java.io.FileNotFoundException;


public class Inventory {
	private static Inventory SINGLETON = null;
	private static List<Item> inventoryList = new ArrayList<>();
	private boolean reOrderFlag = false;

	public static Inventory getSingleton() {
		if (SINGLETON  == null) {
			synchronized (Inventory.class) {
				if (SINGLETON == null) {
					SINGLETON   = new Inventory();
					if (inventoryList.size() == 0) {
						loadItem();
					}
				}
			}
		}
		return SINGLETON;
	}
	
	public boolean getReOrderFlag() {
		return this.reOrderFlag;
	}
	
	public List<Item> getInventoryList() {
		return Inventory.inventoryList;
	}
	
	/**
	 * Get the name of the item in the list
	 * @return list of items' name
	 */
	public List<String> getItemsName() {
		List<String> nameList = new ArrayList<>();
		for (Item item : inventoryList) {
			nameList.add(item.getName());
		}
		return nameList;
	}
	
	
	/**
	 * Get the item in the list
	 * @return list of items' name
	 */
	public Item getItem(String name) {
		for (Item item : inventoryList) {
			if(item.getName().equalsIgnoreCase("name"))
			{
				return item;
			}
		}
		return null;
	}
	
	/**
	 * This method add a new item to the inventory
	 * @param item
	 */
	public void add(Item item) {
		boolean addItem = true;
		for (Item i : inventoryList) {
			if (i.getName().equals(item.getName())) {
				System.out.println(String.format("Item with name=%s is already in the inventory", item.getName()));
				addItem = false;
			} 
		}
		
		if (addItem) {
			inventoryList.add(item);
		}
	}
	
	/**
	 * This method remove item from the inventory by name
	 * @param name - Name of the item
	 */
	public void remove(String name) {
		for (Item item : inventoryList) {
			if (item.getName().equals(name)) {
				inventoryList.remove(item);
				break;
			}
		}
	}
	
	/**
	 * This method subtract quantity from an item in the list
	 * If the quantity of the item is 0, return a null item
	 * If quantity is greater than or equal to the onHandQuantity:
	 * 	-return the item with the onHandQuantity equal to quantity requested
	 * 	-subtract the quantity from the onHandQuantity of the item in the list
	 * If onHandQauntity is less than requested quantity:
	 * 	-return item with the available onHandQuantity
	 * Set reOrder flag to true of onHandQuantity is less than threshold
	 * @param name
	 * @param quantity
	 * @return
	 */
	public Item subtractItemQuantity(String name, int quantity) {
		Item item = null;
		for (Item i : inventoryList) {
			if (i.getName().equals(name)) {
				if (i.getOnHandQuantity() == 0) {
					break;
				} else {
					item = new Item();
					item.setName(i.getName());
					item.setPrice(i.getPrice());
					if (i.getOnHandQuantity() >= quantity) {
						item.setOnHandQuantity(quantity);
						i.subtractQuantity(quantity);
					} else {
						item.setOnHandQuantity(i.getOnHandQuantity());
						i.subtractQuantity(i.getOnHandQuantity());
					}
				}
			}
			
			if (i.getOnHandQuantity() <= i.getThreshold()) {
				i.setReOrder(true);
				this.reOrderFlag = true;
			}
		}
		return item;
	}
	
	/**
	 * This method add quantity to the item
	 * @param name
	 * @param quantity
	 */
	public void addItemQuantity(String name, int quantity) {
		for (Item i : inventoryList) {
			if (i.getName().equals(name)) {
				i.addQuantity(quantity);
				break;
			}
		}
	}
	
	public int getItemCount() {
		return inventoryList.size();
	}
	
	public void reOrder() {
		if (this.reOrderFlag) {
			for (Item i : inventoryList) {
				if (i.getReOrder()) {
					int numOfItem = i.getMaxOnHandQuantity() - i.getOnHandQuantity();
					addItemQuantity(i.getName(), numOfItem); 
					i.setReOrder(false);
				}
			}
		}
	}
	
	/**
	 * This method load item into the inventory list;
	 */
	private static void loadItem() {
		try {
			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(getStringFromFile());
			JSONArray jsonArray = (JSONArray) json.get("inventory");
			ObjectMapper mapper = new ObjectMapper();
			
			for (int i = 0; i < jsonArray.size(); i++) {
				Item item = mapper.readValue(jsonArray.get(i).toString(), Item.class);
				inventoryList.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Helper method to read from JSON file
	 * @return - String
	 */
	private static String getStringFromFile() {
		StringBuilder sb = new StringBuilder();
		try {
			File dataStore = new File("dataStore.json");
			Scanner scanner = new Scanner(dataStore);
			while (scanner.hasNext()) {
				String  data = scanner.next();
				sb.append(data);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}  
		return sb.toString();
	}
}
