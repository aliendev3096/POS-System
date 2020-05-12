package main.seis602.pos.report;

public abstract class ReportAbstract implements ReportInterface {
	private Report report;
	
	public ReportAbstract(Report report) {
		this.report = report;
		
	}
	
	public void printReport() {
		
	};

}
