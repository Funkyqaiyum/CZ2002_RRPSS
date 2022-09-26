
package Project;

import java.util.Scanner;

/**
 * Class that interacts with user to perform operations on the menu
 * 
 * @author Abdul Qaiyum Lee
 *
 */
public class MenuBoundary {
	/**
	 * For reading user input
	 */
	private static Scanner sc= new Scanner(System.in);
	/**
	 * Instance of Menu manager
	 */
	private static MenuMgr menuMgr;
	
	/**
	 * Creates the menu boundary class
	 * @param MenuMgr Menu Manager
	 */
	public MenuBoundary(MenuMgr MenuMgr) {
		MenuBoundary.menuMgr=MenuMgr;
	}
	
	/**
	 * Displays all the options in the main menu
	 */
	public void displaymenuMgr(){
		System.out.println();
		System.out.println("*****   Menu Manager   *****");
		System.out.println("1) Display Menu");
		System.out.println("2) Add menu item");
		System.out.println("3) Remove menu item");
		System.out.println("4) Update menu item");
		System.out.println("5) Add set package");
		System.out.println("6) Remove set package");
		System.out.println("7) Update set package");
		System.out.println("0) Terminate");
		System.out.println("\n\n");

	}
	
	/**
	 * Allows the user to pick one of the options in the main menu
	 */
	public void pickChoice() {
		boolean loop= true;
		int choice=0;
		while(loop) {
			displaymenuMgr();
			System.out.println("Enter your choice");
			choice=sc.nextInt();
			sc.nextLine();
			
			switch(choice) {
			case 0:{
				menuMgr.saveMenu();
				System.out.println("Terminate");
				loop=false;
				break;
			}
			
			case 1: displayMenu(); break;
			case 2: addMenuItem(); break;
			case 3: removeMenuItem(); break;
			case 4: updateMenuItem(); break;
			case 5: addSet(); break;
			case 6: removeSet(); break;
			case 7: updateSet(); break;
			default: System.out.println("Please enter a correct input");
			
			}
		}
	}
	
	/**
	 * Prints out the Menu<br>
	 * Menu items are categorised according to their item type<br>
	 * Each category has its own indexing
	 */
	public static void displayMenu() {
		if(menuMgr.getMenu().size()==0) {
			System.out.println("There is no Menu to display");
			return;	
		}
		System.out.println();
		System.out.print(new String(new char[24]).replace("\0", "*"));
		System.out.print(" MENU ");
		System.out.println(new String(new char[24]).replace("\0", "*"));
		
		MenuItem.ItemType curType=null;
		int num=1;
		for(int i=0;i<menuMgr.getMenu().size();i++) {
			if(menuMgr.getMenu().get(i).getType()!=curType) {
				num=1;
				curType=menuMgr.getMenu().get(i).getType();
				System.out.println();
				System.out.println("/ "+curType.toStrValue()+" /");
			}

			System.out.printf("%d:   ",num++); menuMgr.getMenu().get(i).displayItemDetails();
		}
	}
	
	/**
	 * Finding the index of the first set package inside the Menu list
	 * @return Index of the first Set Package
	 */
	private int findFirstSet() {
		int index=0;
		for(int i=0;i<menuMgr.getMenu().size();i++) {
			if(menuMgr.getMenu().get(i).getType()== MenuItem.ItemType.Set) {
				index=i;
				return index;
			}
		}
		return menuMgr.getMenu().size();  //if there are no sets
	
	}
	
	/**
	 * Fuction to create and add an alacarte item into the menu
	 */
	private void addMenuItem() {
		
		//search for the first index of set
		int firstset= findFirstSet();
		
		System.out.println("Select the type of item you want to add:");
		System.out.println("1: Main Course    2: Drink   3: Dessert");
		int choice = sc.nextInt();
		sc.nextLine();
		if(choice<1 || choice >3) {
			System.out.println("Enter one of the options. Please try again");
			return;
		}
		
		System.out.println("Please enter the name of the item");
		String name=sc.nextLine();
		for(int i=0;i<firstset;i++) {  //loop from start till before the first set item
			if(menuMgr.getMenu().get(i).getName()==name) {
				System.out.println("Item with the same name already exists. Please try again");
				return;
			}
		}
		//if no duplicate, proceed
		System.out.println("Please enter the price of the item");
		double price = sc.nextDouble();	
		sc.nextLine();
		if(price<0) {
			System.out.println("Price entered is invalid. Please try again");
			return;
		}
		
		System.out.println("Please enter the description of the item");
		String desc = sc.nextLine();
		
		try {
			menuMgr.addItem(MenuItem.ItemType.values()[choice-1],name,desc,price);
			
		}catch(Exception E)
		{
			System.out.println("Unable to add item. Please try again");
		}
	
		
		System.out.println("Item Added Successfully");
		
	}
	
	/**
	 * Function to remove an alacarte item from the Menu
	 */
	private void removeMenuItem() {
		if(menuMgr.getMenu().size()==0) {
			System.out.println("Menu is empty");
			return;
		}
		
		displayMenu();
		System.out.println("Enter the type of the item to be removed");
		System.out.println("1: Main Course    2: Drink   3: Dessert");
		int choice = sc.nextInt();
		if(choice<1 || choice>3) {
			System.out.println("Enter one of the options. Please try again");
			return;
		}
		
		
		int firstOfType=0;
		MenuItem.ItemType type = MenuItem.ItemType.values()[choice-1];
		for(int i=0;i<menuMgr.getMenu().size();i++) {
			if(menuMgr.getMenu().get(i).getType()==type) {
				firstOfType=i;
				break;
			}
		}
		
		System.out.println("Enter the index of the item in the menu");
		int index= sc.nextInt();  //index wrt to type
		sc.nextLine();
		if(index<1 || index> menuMgr.getMenu().size()) {
			System.out.println("Invalid index. Please try again");
			return;
		}
		
		int itemindex= firstOfType+index-1;  //index wrt to entire menu
		
		//double confirm
		if(menuMgr.getMenu().get(itemindex).getType() != type) {
			System.out.println("Wrong index provided. Please try again");
			return;
		}
		
		String remName= menuMgr.getMenu().get(itemindex).getName();
		menuMgr.removeItem(itemindex);
		System.out.println(remName + " is removed from menu");
		
	}
	
	/**
	 * Function to make changes to the details of an alacarte item in the Menu
	 * Operations: Change name, change description, change price
	 */
	private void updateMenuItem() {
		if(menuMgr.getMenu().size()==0) {  //if empty then return
			System.out.println("Menu is empty");
			return;
		}
		
		//obtain the item to be updated
		displayMenu();
		System.out.println();
		System.out.println("Enter the type of the item you want to update");
		System.out.println("1: Main Course    2: Drink   3: Dessert");
		int choice = sc.nextInt();
		if(choice<1 || choice >3) {
			System.out.println("Enter one of the options. Please try again");
			return;
		}
		
		
		int firstOfType=0;
		MenuItem.ItemType type = MenuItem.ItemType.values()[choice-1];
		for(int i=0;i< menuMgr.getMenu().size();i++) {
			if(menuMgr.getMenu().get(i).getType()==type) {
				firstOfType=i;
				break;
			}
		}
		
		System.out.println("Enter the index of the item in the menu");
		int index= sc.nextInt();  //index wrt to type
		sc.nextLine();
		if(index<0 || index> menuMgr.getMenu().size()) {
			System.out.println("Invalid index. Please try again");
			return;
		}
		
		int itemindex= firstOfType+index-1;  //index wrt to entire menu
		
		//double confirm
		if(menuMgr.getMenu().get(itemindex).getType() != type) {
			System.out.println("Wrong index provided. Please try again");
			return;
			}
		
		System.out.println("Current item details");  //show current details
		menuMgr.getMenu().get(itemindex).displayItemDetails();
		
		boolean loop=true;
		while(loop) {
			
			System.out.println("What would you like to change");
			System.out.println("1: Name   2: Description   3: Price  0:  Done");
			int select=sc.nextInt();
			sc.nextLine();
			
			switch(select) {
			
			case 0:{
				System.out.println("Done with changes");
				loop=false;
				break;
			}
			case 1:{  //change name
				System.out.println("Enter the new name");
				try{
					String newName= sc.nextLine();
					menuMgr.getMenu().get(itemindex).setName(newName);
				}
				catch(Exception E) {
					System.out.println("Please enter a proper name");
					break;
				}
				
				System.out.println("Updated item:");
				menuMgr.getMenu().get(itemindex).displayItemDetails();
				break;
				}
			
			case 2:{
				System.out.println("Enter the new description");
				try{
					String newDesc= sc.nextLine();
					menuMgr.getMenu().get(itemindex).setDesc(newDesc);
				}
				catch(Exception E) {
					System.out.println("Please enter a proper description");
					break;
				}
				
				System.out.println("Updated item:");
				menuMgr.getMenu().get(itemindex).displayItemDetails();
				break;
			}
			
			case 3:{
				System.out.println("Enter the new price");
				try{
					double newPrice= sc.nextDouble();
					sc.nextLine();
					if(newPrice<0) {
						System.out.println("Please enter a proper price");
						break;
					}
					menuMgr.getMenu().get(itemindex).setPrice(newPrice);
				}catch(Exception E) {
					System.out.println("Please enter a proper price");
					break;
				}
				
				System.out.println("Updated item:");
				menuMgr.getMenu().get(itemindex).displayItemDetails();
				
				//because price change, some set items price will change
				for(int i=findFirstSet();i<menuMgr.getMenu().size();i++) { //just recalculate all sets
					MenuItem set = menuMgr.getMenu().get(i);
					((SetPackage)set).recalculatePrice();  //explicit down casting to be able to call the method
				}

				break;
			}
			
			default: System.out.println("Please enter a proper choice"); break;
			}
			
		}
			
	} //end of update menu
	
	
	/**
	 * Function to add a create and add a set package to the Menu
	 */
	private void addSet() {
		displayMenu();
		int firstSet= findFirstSet();
		
		System.out.println("Enter the name of the set package");
		String name = sc.nextLine();
		for(int i=firstSet;i<menuMgr.getMenu().size();i++) {
			if(menuMgr.getMenu().get(i).getName()==name) {
				System.out.println("Name already exists. Please try again");
				return;
			}
		}
		
		SetPackage newSet= new SetPackage(MenuItem.ItemType.Set,name,"-",0);   //set description none, price is 0
		
		boolean loop=true;
		while(loop) {
			
			System.out.println("What items do you want to add in the set?");
			System.out.println("1: Main Course    2: Drink   3: Dessert   0: Done");
			int choice=sc.nextInt();
			sc.nextLine();
			
			if(choice==0) {
				System.out.println("Done adding");
				loop=false;
				break;
			}
			
			if(choice<1 || choice>3) {
				System.out.println("Invalid input");
				continue;
			}
			
			MenuItem.ItemType type= MenuItem.ItemType.values()[choice-1];
			int firstOfType=0;
			for(int i=0;i<menuMgr.getMenu().size();i++) {
				if(menuMgr.getMenu().get(i).getType()==type) {
					firstOfType=i;
					break;
				}
			}
			
			System.out.println("Enter the index of the item within its type");
			int typeIndex= sc.nextInt();
			sc.nextLine();
			
			int itemIndex= firstOfType+typeIndex-1;  //index in the whole menu
			
			//check if valid
			if(menuMgr.getMenu().get(itemIndex).getType()!=type) {
				System.out.println("Index error. Please try again");
				continue;
			}
			
			newSet.addItem(menuMgr.getMenu().get(itemIndex)); //add into the set, will be sorted
			
		}
		
		menuMgr.addset(newSet);
		
	} //end of addSet()
	
	/**
	 * Function to remove a set package from the menu
	 */
	private void removeSet() {
		if(menuMgr.getMenu().size()==0) {
			System.out.println("Menu is empty");
			return;
		}
		int firstSet=findFirstSet();
		if(firstSet==menuMgr.getMenu().size()) {
			System.out.println("There are no set packages in the menu");
			return;
		}
		
		displayMenu();
		System.out.println("Enter the index of the set you want to remove");
		int index= sc.nextInt();
		sc.nextLine();
		
		int setIndex=firstSet+index-1;
		if(setIndex<firstSet || setIndex>=menuMgr.getMenu().size()) {
			System.out.println("Invalid index. Please try again");
			return;
		}
		
		menuMgr.getMenu().remove(setIndex);
		System.out.println("Set Package removed");
		
	}
	
	/**
	 * Functions to make changes to an existing set in the set package<br>
	 * Operations: Change name of set, add menu item to set, remove menu item from set
	 */
	private void updateSet() {  
		if(menuMgr.getMenu().size()==0) {
			System.out.println("Menu is empty");
			return;
		}
		int firstSet=findFirstSet();
		if(firstSet==menuMgr.getMenu().size()) {
			System.out.println("There are no set packages in the menu");
			return;
		}
		displayMenu();
		System.out.println();
		System.out.println("Enter the index of the set you want to update");
		int index= sc.nextInt();
		sc.nextLine();
		
		int setIndex=firstSet+index-1;
		if(setIndex<firstSet || setIndex>=menuMgr.getMenu().size()) {
			System.out.println("Invalid index. Please try again");
			return;
		}  //up till here is all checking
		
		
		SetPackage set= (SetPackage)menuMgr.getMenu().get(setIndex);
		
		boolean loop= true;
		while(loop) {
			System.out.println("What do you want to update?");
			System.out.println("1: Name   2: Add item   3: Remove item   0: Done");
			int choice = sc.nextInt();
			sc.nextLine();
			
			switch(choice) {
			
			case 0:{
				System.out.println("Done with changes");
				loop=false;
				break;
			}
			case 1:{
				System.out.println("Enter the new name");
				try{
					String newName= sc.nextLine();
					menuMgr.getMenu().get(setIndex).setName(newName);
				}
				catch(Exception E) {
					System.out.println("Please enter a proper name");
					break;
				}
				break;
			}
			
			case 2:{
				System.out.println("What type of item do you want to add in the set?");
				System.out.println("1: Main Course    2: Drink   3: Dessert");
				int select=sc.nextInt();
				sc.nextLine();
				
				if(select<1 || select>3) {
					System.out.println("Invalid input. Please try again");
					break;
				}
				
				MenuItem.ItemType type= MenuItem.ItemType.values()[select-1];
				int firstOfType=0;
				for(int i=0;i<menuMgr.getMenu().size();i++) {
					if(menuMgr.getMenu().get(i).getType()==type) {
						firstOfType=i;
						break;
					}
				}
				
				System.out.println("Enter the index of the item within its type");
				int typeIndex= sc.nextInt();
				sc.nextLine();
				
				int itemIndex= firstOfType+typeIndex-1;  //index in the whole menu
				
				//check if valid
				if(menuMgr.getMenu().get(itemIndex).getType()!=type) {
					System.out.println("Index error. Please try again");
					continue;
				}
				
				
				set.addItem(menuMgr.getMenu().get(itemIndex)); //add into the set, will be sorted
				break;
			}
			
			case 3:{
				try{
					set.removeItem();
				}catch(Exception e) {
					System.out.println("Something went wrong, please try again.");
				}
				break;
			}
			
			default: {
				System.out.println("Please enter a correct option");
				break;
			}
					
			}
		}
		
	} //end of update set
	
	
}
