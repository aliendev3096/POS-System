package main.seis602.pos.register;

public class Cashier 
{
	private static int uniqueId = 0;
	private int cashierId;
	private String firstName;
	private String lastName;
	
	public Cashier()
	{
		cashierId = uniqueId;
		uniqueId++;
	}
	
	public int getCashierId() {
		return cashierId;
	}


	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
}
