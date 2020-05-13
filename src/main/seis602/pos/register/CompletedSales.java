package main.seis602.pos.register;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CompletedSales {
	private static CompletedSales SINGLETON = null;
	private List<Map<Cashier, Sale>> completedSalesList = new ArrayList<>();
	
	public static CompletedSales getSingleton() {
		if (SINGLETON  == null) {
			synchronized (CompletedSales.class) {
				if (SINGLETON == null) {
					SINGLETON   = new CompletedSales();
					
				}
			}
		}
		return SINGLETON;
	}
	
	public void addCompletedSale(Map<Cashier, Sale> completedSale) {
		this.completedSalesList.add(completedSale);
	}
	
	public List<Map<Cashier, Sale>> getCompletedSalesList() {
		return this.completedSalesList;
	}
	
	public Map<Cashier, Sale> getSaleById(int saleId) {
		Map<Cashier, Sale> result = null;
		for (Map<Cashier, Sale> item : this.completedSalesList) {
			for (Map.Entry<Cashier, Sale> entry: item.entrySet()) {
				if (entry.getValue().getSaleId() == saleId) {
					result = item;
				}
			}
		}
		return result;
	}
}
