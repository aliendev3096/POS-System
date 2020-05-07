package main.seis602.pos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import main.seis602.pos.register.Cashier;
import main.seis602.pos.register.Register;

public class Console 
{
	private static Register activeRegister = null;
	private static ArrayList<Register> registers = new ArrayList<Register>();
	private static Cashier cashier;
	private static String username = null, password = null;
	
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		boolean validCredentials = false;
		boolean reAuthenticate = true;
		String commandOption = "0";
		
		// Generate Prompt in watch mode style
		StringBuilder consoleCommandPrompt = new StringBuilder("Command: \n");
		consoleCommandPrompt.append("1) Log in to new register as new employee \n");
		consoleCommandPrompt.append("2) Switch Register \n");
		consoleCommandPrompt.append("3) Add Sale \n");
		consoleCommandPrompt.append("4) Return Sale \n");
		consoleCommandPrompt.append("5) Return Item in Current Sale \n");
		consoleCommandPrompt.append("6) Complete Current Sale \n");
		consoleCommandPrompt.append("7) Stop POS System \n");
		
		while(!commandOption.equals("7"))
		{	
			showCurrentRegister();
			System.out.print(consoleCommandPrompt);
			
			commandOption =in.nextLine();

			switch(commandOption)
			{
				case "1": 			
					// Authentication Flow
					while(reAuthenticate)
					{
						// prompt for user credentials
						validCredentials = authenticate(in);
						if(!validCredentials)
						{
							System.out.println("Do you wish to re-authenticate? (Yes/No)");
							String reauthenticateResponse = in.nextLine();
							if(reauthenticateResponse.equalsIgnoreCase("no"))
							{
								reAuthenticate = false;
							}
						}
						else
						{
							reAuthenticate = false;
						}
					}
					if(validCredentials)
					{
						// Retrieve Cashier Data
						cashier = retrieveCashier(username);
						
						// Setup Register
						Register register = new Register(cashier);
						// Add register to collection of registers
						registers.add(register);
						// set application's active register
						activeRegister = register;
					}
					reAuthenticate = true;
					break;
				
				case "2": 
					if(registers.size() == 0)
					{
						System.out.println("No Possible Open Registers at this time.");
						break;
					}
					
					// Display all available registers
					System.out.println("All Available Registers \n");
					for(Register reg: registers)
					{
						System.out.println(String.format("Register Number: %s", reg.getRegisterId()));
					}
					System.out.println("Enter a register number: \n");
					// Take input from user
					String regNumber = in.nextLine();
					try
					{
						// Search for user inputed register id
						Register newActiveRegister = registers.stream()
								.filter(r -> r.getRegisterId() == Integer.parseInt(regNumber))
								.findFirst()
								.orElse(null);
						// Set active register if available and prompt user of change
						if(newActiveRegister != null)
						{
							// Authentication Flow
							while(reAuthenticate)
							{
								// prompt for user credentials
								validCredentials = authenticate(in);
								if(!validCredentials)
								{
									System.out.println("Do you wish to re-authenticate? (Yes/No)");
									String reauthenticateResponse = in.nextLine();
									if(reauthenticateResponse.equalsIgnoreCase("no"))
									{
										reAuthenticate = false;
									}
								}
								else
								{
									reAuthenticate = false;
								}
							}
							if(validCredentials)
							{
								activeRegister = newActiveRegister;
								cashier = retrieveCashier(username);
								System.out.println(String.format("Register Set to %s", regNumber));
							}
							reAuthenticate = true;
						}
						// Prompt user of no change if register is not valid
						else 
						{
							System.out.println("Register Not Found \n");
						}
					}
					catch(Exception e)
					{
						System.out.println("Invalid Register Id");
					}
					
					break;
				case "3":
					if(activeRegister == null)
					{
						System.out.println("Please log into a register before adding a sale.");
						break;
					}
					try {
						// Try to Create a new Sale
						//TODO: Fill in Sale information
						//activeRegister.createSale();
					} 
					catch(Exception e)
					{
						// if there were any problems creating the sale
						// log the output
						System.out.print(e.getMessage());
					}
					break;
				case "4":
					if(activeRegister == null)
					{
						System.out.println("Please log into a register before returning a sale.");
						break;
					}
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
				case "5": 
					if(activeRegister == null)
					{
						System.out.println("Please log into a register before returning an item.");
						break;
					}
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
				case "6":
					if(activeRegister == null)
					{
						System.out.println("Please log into a register before completing a sale.");
						break;
					}
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
				case "7":
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
	
	// Mocks Authentication Flow
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

		if(!validCredentials.get(username).equals(password.toString()))
		{
			return false;
		}
		// assume credentials are valid and return true
		return true;
	}
	
	private static void showCurrentRegister()
	{
		if(activeRegister != null)
		{
			System.out.println(String.format("Current Register: %s", activeRegister.getRegisterId()));
			System.out.println(String.format("Logged in as: %s %s", cashier.getFirstName(), cashier.getLastName()));
		}
		else
		{
			System.out.println("You are not logged into any register.");
		}
	}
	
	private static boolean authenticate(Scanner in)
	{
		boolean validCredentials = false;
		// prompt for user credentials
		System.out.println("Username: \n");
		username = in.nextLine();

		System.out.println("Password: ");
		password = in.nextLine();
		validCredentials = validateCredentials(username, password);

		// if invalid, provide error and loop around prompt
		if(!validCredentials)
		{
			System.out.println("Invalid Credentials Provided. \n");
			
		}
		return validCredentials;
	}
}
