package main.seis602.pos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import main.seis602.pos.inventory.Item;
import main.seis602.pos.register.Cashier;
import main.seis602.pos.register.ItemStatus;
import main.seis602.pos.register.Register;
import main.seis602.pos.register.Sale;
import main.seis602.pos.register.Status;

public class Console 
{
	private static Register activeRegister = null;
	private static List<Register> registers = new ArrayList<>();
	private static Cashier cashier;
	private static String username = null, password = null;
	
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		boolean validCredentials = false;
		boolean reAuthenticate = true;
		String commandOption = "0";
		
		
		while(!commandOption.equals("15"))
		{	
			System.out.print("-------------------------------------- \n");
			showCurrentRegister();
			showCurrentSale();
			showCommandPrompt(validCredentials);
			System.out.print("Enter: ");
			
			commandOption = in.nextLine();

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
							System.out.print("Do you wish to re-authenticate? (Yes/No): ");
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
						System.out.println("No Possible Open Registers at this time. \\n");
						break;
					}
					
					// Display all available registers
					System.out.println("All Available Registers \n");
					for(Register reg: registers)
					{
						System.out.println(String.format("Register Number: %s", reg.getRegisterId()));
					}
					System.out.print("Enter a register number: ");
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
									System.out.print("Do you wish to re-authenticate? (Yes/No): ");
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
								activeRegister.setCashier(cashier);
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
						System.out.println("Invalid Register Id \n");
					}
					
					break;
				case "3":
					if(activeRegister == null)
					{
						System.out.println("Please log into a register before adding a sale. \n");
						break;
					}
					if(activeRegister.getActiveSale() != null && activeRegister.getActiveSale().getStatus() == Status.ACTIVE)
					{
						System.out.println("Active Sale already in session. \n");
						break;
					}
					try {
						// Create new sale
						activeRegister.createSale(new Sale());
					} 
					catch(Exception e)
					{
						System.out.print(e);
					}
					break;
				case "4":
					if(activeRegister == null)
					{
						System.out.println("Please log into a register before canceling a sale. \n");
						break;
					}
					if(activeRegister.getActiveSale() == null)
					{
						System.out.println("No active sale to cancel. \n");
						break;
					}
					try {
						// Cancel current active sale
						activeRegister.cancelSale();
						
					} 
					catch(Exception e)
					{
						System.out.print(e);
					}
					break;
				case "5":
					if(activeRegister == null)
					{
						System.out.println("Please log into a register before adding an item to a sale. \n");
						break;
					}
					if(activeRegister.getActiveSale() == null || activeRegister.getActiveSale().getStatus() != Status.ACTIVE)
					{
						System.out.println("No sale to add item too. \n");
						break;
					}
					try {
						// Take input from user
						System.out.print("Enter an item to add to sale: ");
						String itemName = in.nextLine();
						System.out.print("Enter quantity: ");
						int qty = Integer.parseInt(in.nextLine());
						activeRegister.addItem(itemName, qty);
					} 
					catch(Exception e)
					{
						System.out.println("Item does not exist in inventory");
					}
					break;
				case "6":
					if(activeRegister == null)
					{
						System.out.println("Please log into a register before removing an item from a sale. \n");
						break;
					}
					if(activeRegister.getActiveSale() == null || activeRegister.getActiveSale().getStatus() != Status.ACTIVE)
					{
						System.out.println("No sale to remove item. \n");
						break;
					}
					try {
						// Display all items on current active register sale
						System.out.println("All Available Items on current active sale. \n");
						for(Item item: activeRegister.getActiveSale().getItemList())
						{
							System.out.println(String.format("Item Name: %s", item.getName()));
						}
						// Take input from user
						System.out.print("Enter an item to remove from sale: ");
						String itemName = in.nextLine();
						
						//Remove item from sale
						activeRegister.removeItem(itemName);
					} 
					catch(Exception e)
					{
						System.out.print(e);
					}
					break;
				case "7":
					if(activeRegister == null)
					{
						System.out.println("Please log into a register before returning a sale. \n");
						break;
					}
					if(activeRegister.getSales().size() == 0)
					{
						System.out.println("No Possible Sales to return at this time. \n");
						break;
					}
					try {
						// Display all Sales on current active register
						// Do we want all sales to be available from all registers?
						System.out.println("All Available Sales on current active register. \n");
						List<Map<Cashier, Sale>> completedSalesList = activeRegister.getSales();
						completedSalesList.forEach(item -> {
							item.forEach((cashier, sale) -> {
								if (sale.getStatus() == Status.COMPLETED)
									System.out.println(String.format("Sale Id: %s \n", sale.getSaleId()));
							});
						});
						
						System.out.print("Enter a sale id to return: ");
						// Take input from user
						String saleId = in.nextLine();
						int integerSaleId = Integer.parseInt(saleId);
						try
						{
							//return sale via register
							activeRegister.returnSale(integerSaleId);
						}
						catch(Exception e)
						{
							System.out.println(e);
						}
						if (activeRegister != null) {
							activeRegister.flushInventory();
						}
					} 
					catch(Exception e)
					{
						System.out.print(e);
					}
					break;
				case "8": 
					if(activeRegister == null)
					{
						System.out.println("Please log into a register before returning an item. \n");
						break;
					}
					if(activeRegister.getSales().size() == 0)
					{
						System.out.println("No Possible Sales to return an item at this time. \n");
						break;
					}
					try {
						//Prompt item return
						System.out.println("All Available Sales on current active register. \n");
						List<Map<Cashier, Sale>> completedSalesList = activeRegister.getSales();
						completedSalesList.forEach(item -> {
							item.forEach((cashier, sale) -> {
								if (sale.getStatus() == Status.COMPLETED)
									System.out.println(String.format("Sale Id: %s \n", sale.getSaleId()));
							});
						});
						System.out.print("Enter a sale id to return: ");
						// Take input from user
						String saleId = in.nextLine();
						try
						{
							int integerSaleId = Integer.parseInt(saleId);
							// Search for user inputed sale id
							Map<Cashier, Sale> saleMap = activeRegister.getSaleMapById(integerSaleId);
							Sale  sale = null;
							for (Map.Entry<Cashier, Sale> entry : saleMap.entrySet()) {
								sale = entry.getValue();
						
							}
							
							if(sale == null)
							{
								// throw exception if sale does not exist on registerId
								throw new Exception(String.format("Sale of sale id %s does not exist on this register: %s", saleId, activeRegister.getRegisterId()));
							}
							
							// Display All items in sale to choose from
							for(Item item : sale.getItemList())
							{
								System.out.println(
										String.format("Item Name : %s", item.getName()));
							}
							
							System.out.print("Enter an item to return: ");
							// Take input from user
							String itemName = in.nextLine();
							//return item via register 
							activeRegister.returnSaleItem(itemName, integerSaleId);
							
						}
						catch(Exception e)
						{
							System.out.println(e);
						}
						if (activeRegister != null) {
							activeRegister.flushInventory();
						}
					} 
					catch(Exception e)
					{
						System.out.print(e);
					}
					break;
				case "9":
					if(activeRegister == null)
					{
						System.out.println("Please log into a register before completing a sale. \n");
						break;
					}
					try {
						// complete active sale via register 
						activeRegister.completeSale();
					} 
					catch(Exception e)
					{
						System.out.print(e);
					}
					if (activeRegister != null) {
						activeRegister.flushInventory();
					}
					break;
				case "10":
					if(activeRegister == null)
					{
						System.out.println("Please log into a register before printing a report. \n");
						break;
					}
					try {
						// Re-order Items
						activeRegister.reOrderItem();
					} 
					catch(Exception e)
					{
						System.out.print(e);
					}
					
					break;
				case "11":
					if(activeRegister == null)
					{
						System.out.println("Please log into a register before printing a report. \n");
						break;
					}
					try {
						// Move Item to Inventory
						activeRegister.moveItemToInventory() ;
					} 
					catch(Exception e)
					{
						System.out.print(e);
					}
					break;
				case "12":
					if(activeRegister == null)
					{
						System.out.println("Please log into a register before printing a report. \n");
						break;
					}
					try {
						// print active register inventory report 
						activeRegister.printInventoryReport();
					} 
					catch(Exception e)
					{
						System.out.print(e);
					}
					break;
				case "13":
					if(activeRegister == null)
					{
						System.out.println("Please log into a register before printing a report. \n");
						break;
					}
					try {
						// print active register cashier report 
						activeRegister.printCashierReport(true);
					} 
					catch(Exception e)
					{
						System.out.print(e);
					}
					break;
				case "14":
					if(activeRegister == null)
					{
						System.out.println("Please log into a register before printing a report. \n");
						break;
					}
					try {
						// print active register cashier report 
						activeRegister.printCashierReport(false);
					} 
					catch(Exception e)
					{
						System.out.print(e);
					}
					break;
				case "15":
					System.out.println("POS System is closing \n");
					if (activeRegister != null) {
						activeRegister.flushInventory();
					}
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
				"manager101", new Cashier("Mike", "Manager", 536267),
				"associate987", new Cashier("Ashley", "Associate", 454653),
				"associate122", new Cashier("Andy", "Associate", 534534));
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
		System.out.println(String.format("Number of Register Open: %s", registers.size()));
		if(activeRegister != null)
		{
			if (activeRegister.getReOrderFlag()) {
				System.out.println("*** One or more items is low in stock, please re-order ***");
			}
			
			if (activeRegister.getPendingOrderFlag()) {
				System.out.println("*** Order Pending ***");
			}
			System.out.println(String.format("Current Register: %s", activeRegister.getRegisterId()));
			System.out.println(String.format("Number of Sales in Register: %s", activeRegister.getNumberOfSales()));
			System.out.println(String.format("Logged in as: %s %s Id: %s", cashier.getFirstName(), cashier.getLastName(), cashier.getCashierId()));
		}
		else
		{
			System.out.println("You are not logged into any register.");
		}
	}
	
	private static void showCurrentSale()
	{
		if(activeRegister != null)
		{
			if(activeRegister.getActiveSale() != null && activeRegister.getActiveSale().getStatus() == Status.ACTIVE)
			{
				System.out.println(String.format("Current Sale Id: %s", activeRegister.getActiveSale().getSaleId()));
				System.out.println(String.format("Current Sale Items: \n"));
				List<Map<ItemStatus, Item>> itemList = activeRegister.getActiveSale().getMapItemList();
				for (Map<ItemStatus, Item> item : itemList) {
					item.forEach((k, v) -> {
						if (!k.equals(ItemStatus.ACTIVE)) {
							System.out.println(String.format("%s %s @ %.2f", k, v.getName(), v.getPrice()));
						} else {
							System.out.println(String.format("%s @ %.2f", v.getName(), v.getPrice()));
						}
						
					});
				}
				
				System.out.print(String.format("Total: $%.2f \n \n", activeRegister.getActiveSale().getTotal()));
			}
		}
	}
	
	private static void showCommandPrompt(boolean authenticated)
	{
		// Generate Prompt in watch mode style
		StringBuilder consoleCommandPrompt = new StringBuilder("Command: \n");
		consoleCommandPrompt.append("1) Log in to open a register \n");
		consoleCommandPrompt.append("2) Switch Register \n");
		consoleCommandPrompt.append("3) Add Sale \n");
		consoleCommandPrompt.append("4) Cancel Current Sale \n");
		consoleCommandPrompt.append("5) Add Item to Sale \n");
		consoleCommandPrompt.append("6) Void Item in Sale \n");
		consoleCommandPrompt.append("7) Return Sale \n");
		consoleCommandPrompt.append("8) Return Item \n");
		consoleCommandPrompt.append("9) Complete Current Sale \n");
		consoleCommandPrompt.append("10) Re-Order Item(s)\n");
		consoleCommandPrompt.append("11) Move Item(s) to Inventory \n");
		consoleCommandPrompt.append("12) Print Inventory Report \n");
		consoleCommandPrompt.append("13) Print Cashier Report - Current Cashier \n");
		consoleCommandPrompt.append("14) Print Cashier Report \n");
		consoleCommandPrompt.append("15) Stop POS System \n");
		
		System.out.print(consoleCommandPrompt);
	}
	
	private static boolean authenticate(Scanner in)
	{
		boolean validCredentials = false;
		// prompt for user credentials
		System.out.print("Username: ");
		username = in.nextLine();

		System.out.print("Password: ");
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
