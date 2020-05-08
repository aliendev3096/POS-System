package main.seis602.pos.register;

public class Cashier 
{
	private int cashierId;
	private String firstName;
	private String lastName;
	
	public Cashier(String firstName, String lastName, int id)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		cashierId = id;
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
