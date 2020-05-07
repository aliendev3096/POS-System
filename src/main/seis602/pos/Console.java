package main.seis602.pos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import main.seis602.pos.register.Cashier;
import main.seis602.pos.register.Register;

public class Console 
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		ArrayList<Register> registers = new ArrayList<Register>();
		boolean validCredentials = false;
		Register activeRegister = null;
		int commandOption = 0;
		String username = null, password;
		
		while(commandOption != 7)
		{
			// Generate Prompt in watch mode style
			StringBuilder consoleCommandPrompt = new StringBuilder("Command: \n");
			consoleCommandPrompt.append("1) Log in to new register as new employee \n");
			consoleCommandPrompt.append("2) Switch Register \n");
			consoleCommandPrompt.append("3) Add Sale \n");
			consoleCommandPrompt.append("4) Return Sale \n");
			consoleCommandPrompt.append("5) Return Item in Current Sale \n");
			consoleCommandPrompt.append("6) Complete Current Sale \n");
			consoleCommandPrompt.append("7) Stop POS System");
			
			System.out.print(consoleCommandPrompt);
			
			commandOption = in.nextInt();
			
			switch(commandOption)
			{
				case 1: 			
					// Auth Flow
					while(!validCredentials)
					{
						// prompt for user credentials
						System.out.println("Username: ");
						username = in.nextLine();
						System.out.println("\n Password: ");
						password = in.nextLine();
						validCredentials = validateCredentials(username, password);
						
						// if invalid, provide error and loop around prompt
						if(!validCredentials)
						{
							System.out.println("Invalid Credentials Provided.");
						}
					}
					
					// Retrieve Cashier Data
					Cashier cashier = retrieveCashier(username);
					
					// Setup Register
					Register register = new Register(cashier);
					// Add register to collection of registers
					registers.add(register);
					// set application's active register
					activeRegister = register;
					break;
				
				case 2: 
					// Display all available registers
					System.out.println("All Available Registers \n");
					for(Register reg: registers)
					{
						System.out.println(String.format("Register Number: %s", reg.getRegisterId()));
					}
					System.out.println("Enter a register number: \n");
					// Take input from user
					int regNumber = in.nextInt();
					// Search for user inputed register id
					Register newActiveRegister = registers.stream()
							.filter(r -> r.getRegisterId() == regNumber)
							.findFirst()
							.orElse(null);
					// Set active register if available and prompt user of change
					if(newActiveRegister != null)
					{
						activeRegister = newActiveRegister;
						System.out.println(String.format("Register Set to %s", regNumber));
					}
					// Prompt user of no change if register is not valid
					else 
					{
						System.out.println("Register Not Found \n");
						System.out.println(String.format("Register Set to %s", activeRegister.getRegisterId()));
					}
					break;
				case 3: 
					try {
						// Try to Create a new Sale
						//TODO: Fill in Sale information
						activeRegister.createSale();
					} 
					catch(Exception e)
					{
						// if there were any problems creating the sale
						// log the output
						System.out.print(e.getMessage());
					}
					break;
				case 4:
					try {
						//TODO: Prompt sale return (allow even if we have a current active sale?)
						//TODO: return sale via register 
					} 
					catch(Exception e)
					{
						// if there were any problems
						// log the output
						System.out.print(e.getMessage());
					}
					break;
				case 5: 
					try {
						//TODO: Prompt item return 
						//TODO: return item via register 
					} 
					catch(Exception e)
					{
						// if there were any problems
						// log the output
						System.out.print(e.getMessage());
					}
					break;
				case 6:
					try {
						//TODO: complete active sale via register 
					} 
					catch(Exception e)
					{
						// if there were any problems
						// log the output
						System.out.print(e.getMessage());
					}
					break;
				case 7:
					System.out.println("POS System is closing \n");
					break;
				default: break;
			}
		}
		// close input stream
		in.close();
		
	}
	// Mock Retrieval of employee data 
	private static Cashier retrieveCashier(String username)
	{
		// In memory user database 
		Map<String, Cashier> employees = Map.of(
				"manager101", new Cashier("Mike", "Manager"),
				"associate987", new Cashier("Ashley", "Associate"),
				"associate122", new Cashier("Andy", "Associate"));
		return employees.get(username);
	}
	
	// Mocks Auth Flow
	private static boolean validateCredentials(String username, String password)
	{
		// Mimic Some form of authentication 
		Map<String, String> validCredentials = Map.of("manager101", "101", 
				"associate987", "987",
				"associate122", "122");
		// if user name doesn't exist, short circuit
		if(!validCredentials.containsKey(username))
		{
			return false;
		}
		// if user name exists, check the password and short circuit if need be.
		if(validCredentials.get(username) != password)
		{
			return false;
		}
		// assume credentials are valid and return true
		return true;
	}
}
