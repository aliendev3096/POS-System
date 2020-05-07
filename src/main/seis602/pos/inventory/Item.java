package main.seis602.pos.inventory;

public class Item 
{
	private String name;
	private int threshold;
	private int onHandQuantity;
	private String supplier;
	private double price;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getThreshold() {
		return threshold;
	}
	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}
	public int getOnHandQuantity() {
		return onHandQuantity;
	}
	public void setOnHandQuantity(int onHandQuantity) {
		this.onHandQuantity = onHandQuantity;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
}
