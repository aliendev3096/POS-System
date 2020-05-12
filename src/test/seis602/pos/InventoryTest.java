package test.seis602.pos;

import main.seis602.pos.report.InventoryReport;
import main.seis602.pos.report.ReportAbstract;

public class InventoryTest {
	
	public static void main(String[] args) {
		
			ReportAbstract report = new InventoryReport();
			report.printReport();
		
	}

}