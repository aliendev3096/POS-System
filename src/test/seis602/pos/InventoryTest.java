package test.seis602.pos;

import java.util.List;

import main.seis602.pos.inventory.Inventory;
import main.seis602.pos.inventory.Item;

public class InventoryTest {
	
	public static void main(String[] args) {
		Inventory inventory = Inventory.getSingleton();
		
		
		List<Item> itemList = inventory.getInventoryList();
		
		for (Item i : itemList) {
			System.out.println(i.toString());
		}
		
		System.out.println("");
		Item porkItem = inventory.subtractItemQuantity("Pork", 7);
		System.out.println(porkItem);
		System.out.println("");
		
		for (Item i : itemList) {
			System.out.println(i.toString());
		}
		
		inventory.reOrder();
		System.out.println("");
		
		for (Item i : itemList) {
			System.out.println(i.toString());
		}
	}

}
