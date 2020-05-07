package main.seis602.pos.register;

public class Refund 
{
	private int salesId;
	private String itemName;
	private double refundAmount;
	
	public int getSalesId() {
		return salesId;
	}
	public void setSalesId(int salesId) {
		this.salesId = salesId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public double getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(double refundAmount) {
		this.refundAmount = refundAmount;
	}
}
