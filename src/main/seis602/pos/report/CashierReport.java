package main.seis602.pos.report;

import java.util.List;
import java.util.Map;
import main.seis602.pos.register.Cashier;
import main.seis602.pos.register.CompletedSales;
import main.seis602.pos.register.Sale;

public class CashierReport extends ReportAbstract {
	private CompletedSales completedSales;
	private int cashierId;
	private boolean isActiveCashier;
	
	public CashierReport(int cashierId, boolean isActiveCashier) {
		super(Report.CASHIER);
		completedSales = CompletedSales.getSingleton();
		this.cashierId = cashierId;
		this.isActiveCashier = isActiveCashier;
	}
	
	@Override
	public void printReport() {
		List<Map<Cashier, Sale>> completedSalesList = completedSales.getCompletedSalesList();
		StringBuilder sb = new StringBuilder();
		int asterickLength = 43;
		for (int i = 0; i < asterickLength; i++) {
			sb.append("*");
		}
		sb.append("Cashier Report");
		for (int i = 0; i < asterickLength; i++) {
			sb.append("*");
		}
		
		int cashierIdLength  = 12;
		int nameLength = 20;
		int saleIdLength = 8;
		int saleDateLength = 30;
		int totalLength = 12;
		int statusLength = 11;
		sb.append("\n|Cashier Id");
		for (int i=0; i < cashierIdLength - "Cashier Id".length(); i++ ) {
			sb.append(" ");
		}
		sb.append("|Name");
		for (int i=0; i < nameLength - "Name".length(); i++ ) {
			sb.append(" ");
		}
		sb.append("|Sale Id");
		for (int i=0; i < saleIdLength  - "Sale Id".length(); i++ ) {
			sb.append(" ");
		}
		sb.append("|Sale Date");
		for (int i=0; i < saleDateLength - "Sale Date".length(); i++ ) {
			sb.append(" ");
		}
		sb.append("|Sale Total");
		for (int i=0; i < totalLength - "Sale Total".length(); i++ ) {
			sb.append(" ");
		}
		sb.append("|Status");
		for (int i=0; i < statusLength - "Status".length(); i++ ) {
			sb.append(" ");
		}
		sb.append("|");

		sb.append("\n");
		for (int i = 0; i < 100; i++) {
			sb.append("-");
		}

		double runningTotal = 0;
		String cashierId;
		String name;
		String saleId;
		String saleDate;
		String totalSale;
		String saleStatus;
		
		for (Map<Cashier, Sale> item: completedSalesList) {
			for(Map.Entry<Cashier, Sale> entry: item.entrySet()) {
				if (entry.getKey().getCashierId() == this.cashierId && this.isActiveCashier) {
					cashierId = String.format("%s", entry.getKey().getCashierId());
					name = String.format("%s %s", entry.getKey().getFirstName(), entry.getKey().getLastName());
					saleId = String.format("%s", entry.getValue().getSaleId());
					saleDate = String.format("%s", entry.getValue().getDate());
					runningTotal += entry.getValue().getTotal();
					totalSale = String.format("$%.2f", entry.getValue().getTotal());
					saleStatus = String.format("%s", entry.getValue().getStatus());
					
					sb.append(String.format("\n|%s", cashierId));
					for (int i=0; i < cashierIdLength - cashierId.length(); i++ ) {
						sb.append(" ");
					}
					sb.append(String.format("|%s", name));
					for (int i=0; i < nameLength - name.length(); i++ ) {
						sb.append(" ");
					}
					sb.append(String.format("|%s", saleId));
					for (int i=0; i < saleIdLength - saleId.length(); i++ ) {
						sb.append(" ");
					}
					sb.append(String.format("|%s", saleDate));
					for (int i=0; i < saleDateLength - saleDate.length(); i++ ) {
						sb.append(" ");
					}
					sb.append(String.format("|%s", totalSale));
					for (int i=0; i < totalLength - totalSale.length(); i++ ) {
						sb.append(" ");
					}
					sb.append(String.format("|%s", saleStatus));
					for (int i=0; i < statusLength - saleStatus.length(); i++ ) {
						sb.append(" ");
					}
					sb.append("|");
				} 
				
				if (!this.isActiveCashier) {
					cashierId = String.format("%s", entry.getKey().getCashierId());
					name = String.format("%s %s", entry.getKey().getFirstName(), entry.getKey().getLastName());
					saleId = String.format("%s", entry.getValue().getSaleId());
					saleDate = String.format("%s", entry.getValue().getDate());
					runningTotal += entry.getValue().getTotal();
					totalSale = String.format("$%.2f", entry.getValue().getTotal());
					saleStatus = String.format("%s", entry.getValue().getStatus());
					
					sb.append(String.format("\n|%s", cashierId));
					for (int i=0; i < cashierIdLength - cashierId.length(); i++ ) {
						sb.append(" ");
					}
					sb.append(String.format("|%s", name));
					for (int i=0; i < nameLength - name.length(); i++ ) {
						sb.append(" ");
					}
					sb.append(String.format("|%s", saleId));
					for (int i=0; i < saleIdLength - saleId.length(); i++ ) {
						sb.append(" ");
					}
					sb.append(String.format("|%s", saleDate));
					for (int i=0; i < saleDateLength - saleDate.length(); i++ ) {
						sb.append(" ");
					}
					sb.append(String.format("|%s", totalSale));
					for (int i=0; i < totalLength - totalSale.length(); i++ ) {
						sb.append(" ");
					}
					sb.append(String.format("|%s", saleStatus));
					for (int i=0; i < statusLength - saleStatus.length(); i++ ) {
						sb.append(" ");
					}
					sb.append("|");
				}
				
			}
			
		}
		sb.append("\n");
		for (int i = 0; i < 100; i++) {
			sb.append("-");
		}
		String total = String.format("Total Amount: $%.2f", runningTotal);
		sb.append("\n");
		for (int i = 0; i < 100 - total.length(); i++) {
			sb.append(" ");
		}
		sb.append(total);
		sb.append("\n");
		for (int i = 0; i < 100 - total.length(); i++) {
			sb.append(" ");
		}
		for (int i = 0; i < total.length(); i++) {
			sb.append("-");
		}
		System.out.println(sb.toString());
		
		
	}

	
}