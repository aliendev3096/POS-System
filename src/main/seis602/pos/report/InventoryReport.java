package main.seis602.pos.report;

import java.util.List;

import main.seis602.pos.inventory.Inventory;
import main.seis602.pos.inventory.Item;

public class InventoryReport extends ReportAbstract{
	private Inventory inventory;
	
	public InventoryReport() {
		super(Report.INVENTORY);
		this.inventory = Inventory.getSingleton();
		
	}
	
	@Override
	public void printReport() {
		List<Item> itemList = inventory.getInventoryList();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 35; i++) {
			sb.append("*");
		}
		sb.append("Inventory Report");
		for (int i = 0; i < 35; i++) {
			sb.append("*");
		}
		int nameLength = 16;
		int priceLength = 7;
		int quantityLength = 10;
		int thresholdLength = 11;
		int supplierLength = 20;
		int pendingOrderLength = 15;
		sb.append("\n|Name");
		for (int i=0; i < nameLength - "Name".length(); i++ ) {
			sb.append(" ");
		}
		sb.append("|Price");
		for (int i=0; i < priceLength - "Price".length(); i++ ) {
			sb.append(" ");
		}
		sb.append("|Quantity");
		for (int i=0; i < quantityLength - "Quantity".length(); i++ ) {
			sb.append(" ");
		}
		sb.append("|Threshold");
		for (int i=0; i < thresholdLength - "Threshold".length(); i++ ) {
			sb.append(" ");
		}
		sb.append("|Supplier");
		for (int i=0; i < supplierLength - "Supplier".length(); i++ ) {
			sb.append(" ");
		}
		sb.append("|Pending Order");
		for (int i=0; i < pendingOrderLength - "Pending Order".length(); i++ ) {
			sb.append(" ");
		}
		sb.append("|");

		sb.append("\n");
		for (int i = 0; i < 86; i++) {
			sb.append("-");
		}
		itemList.forEach(item -> {
			String name = item.getName();
			String price = String.format("$%.2f", item.getPrice());
			String quantity = String.format("%s", item.getOnHandQuantity());
			String threshold = String.format("%s", item.getThreshold());
			String supplier = item.getSupplier();
			String pendingOrder = String.format("%s", item.getPendingOrder());
			
			sb.append(String.format("\n|%s", name));
			for (int i=0; i < nameLength - name.length(); i++ ) {
				sb.append(" ");
			}
			sb.append(String.format("|%s", price));
			for (int i=0; i < priceLength - price.length(); i++ ) {
				sb.append(" ");
			}
			sb.append(String.format("|%s", quantity));
			for (int i=0; i < quantityLength - quantity.length(); i++ ) {
				sb.append(" ");
			}
			sb.append(String.format("|%s", threshold));
			for (int i=0; i < thresholdLength - threshold.length(); i++ ) {
				sb.append(" ");
			}
			sb.append(String.format("|%s", supplier));
			for (int i=0; i < supplierLength - supplier.length(); i++ ) {
				sb.append(" ");
			}
			sb.append(String.format("|%s", pendingOrder));
			for (int i=0; i < pendingOrderLength - pendingOrder.length(); i++ ) {
				sb.append(" ");
			}
			sb.append("|");
		
		});
		sb.append("\n");
		for (int i = 0; i < 86; i++) {
			sb.append("-");
		}
		System.out.println(sb.toString());
		
		
	}

}