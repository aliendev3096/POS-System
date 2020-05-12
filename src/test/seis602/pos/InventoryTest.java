package test.seis602.pos;

import java.util.List;

import main.seis602.pos.inventory.Inventory;
import main.seis602.pos.inventory.Item;
import main.seis602.pos.report.InventoryReport;
import main.seis602.pos.report.ReportAbstract;

public class InventoryTest {
	
	public static void main(String[] args) {
		
			ReportAbstract report = new InventoryReport();
			report.printReport();
		
	}

}
