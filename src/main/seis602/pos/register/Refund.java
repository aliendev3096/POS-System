package main.seis602.pos.register;

import java.util.ArrayList;
import java.util.List;

public class Refund 
{
	private static int refundId = 0;
	private int saleId;
	private List<String> items;
	private double refundAmount;
	
	public Refund()
	{
		setRefundId(getRefundId() + 1);
		items = new ArrayList<String>();
	}
	public int getSaleId() {
		return saleId;
	}
	public void setSaleId(int salesId) {
		this.saleId = salesId;
	}
	public List<String> getItems() {
		return items;
	}
	public void addItem(String itemName) {
		this.items.add(itemName);
	}
	public double getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(double refundAmount) {
		this.refundAmount = refundAmount;
	}
	public static int getRefundId() {
		return refundId;
	}
	public static void setRefundId(int refundId) {
		Refund.refundId = refundId;
	}
}
