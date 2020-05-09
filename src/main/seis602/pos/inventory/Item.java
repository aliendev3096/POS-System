package main.seis602.pos.inventory;

public class Item 
{
	private String name;
	private int threshold;
	private int onHandQuantity;
	private String supplier;
	private double price;
	private int maxOnHandQuantity;
	private boolean reOrder;
	
	public Item() {
		
	}
	
	public Item(String name, double price, String supplier)
	{
		this.name = name;
		this.price = price;
		onHandQuantity = 5;
		threshold = 4;
		this.supplier = supplier;
	}
	
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
	
	public int getMaxOnHandQuantity() {
		return this.maxOnHandQuantity;
	}
	
	public void setReOrder(boolean reOrderFlag) {
		this.reOrder = reOrderFlag;
	}
	
	public boolean getReOrder() {
		return this.reOrder;
	}
	
	public void addQuantity(int quantity) {
		this.onHandQuantity += quantity;
	}
	
	public void subtractQuantity(int quantity) {
		this.onHandQuantity -= quantity;
	}

	@Override
	public String toString() {
		return "Item [name=" + name + ", threshold=" + threshold + ", onHandQuantity=" + onHandQuantity + ", supplier="
				+ supplier + ", price=" + price + ", maxOnHandQuantity=" + maxOnHandQuantity + ", reOrder=" + reOrder
				+ "]";
	}
	
	
}
