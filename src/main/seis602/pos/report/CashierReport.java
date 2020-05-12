package main.seis602.pos.report;

import java.util.List;
import java.util.Map;
import main.seis602.pos.register.Cashier;
import main.seis602.pos.register.CompletedSales;
import main.seis602.pos.register.Sale;

public class CashierReport extends ReportAbstract {
	private CompletedSales completedSales;
	
	public CashierReport() {
		super(Report.CASHIER);
		completedSales = CompletedSales.getSingleton();
	}
	
	public void printReport() {
		
		List<Map<Cashier, Sale>> completedSalesList = completedSales.getCompletedSalesList();
		

		
		completedSalesList.forEach(item -> {
			item.forEach((cashier, sale) -> {
				System.out.println(String.format("cashierId=%s : name=%s : saleId=%s : SaleDate=%s : totalSale=%.2f : status=%s", 
						cashier.getCashierId(), cashier.getFirstName(), sale.getSaleId(), sale.getDate(), sale.getTotal(), sale.getStatus()));
			});
		});
		
		
	}

	
}