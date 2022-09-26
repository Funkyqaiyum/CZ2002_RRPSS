package Project;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a Set Package inside the menu<br>
 * A set Package is also a {@link MenuItem}
 * 
 * @author Abdul Qaiyum Lee
 *
 */
public class SetPackage extends MenuItem{
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = -6202587731825029071L;
	/**
	 * Discount applied to all Set Packages
	 */
	private static final double DISCOUNT=0.85;
	/**
	 * A list of all the menu items in this set package
	 */
	private List<MenuItem> list;
	
	/**
	 * Creates a new set package with the given information
	 * 
	 * @param type SetPackage enum
	 * @param name This set package's name
	 * @param desc This set package's description
	 * @param price This set package's price
	 */
	public SetPackage(MenuItem.ItemType type, String name, String desc, double price) {
		super(type, name, desc, price);
		
		this.list= new ArrayList<MenuItem>();
	}
	
	
	/**
	 * Creates a new set package with the given information<br>
	 * 
	 * Used for loading an existing set package if the list of
	 * menu items is provided
	 * 
	 * @param type SetPackage Enumerated Data Type
	 * @param name This set package's name
	 * @param desc This set package's description
	 * @param price This set package's price
	 * @param list This set package's list of menu items
	 */
	
	public SetPackage(MenuItem.ItemType type, String name, String desc, double price, List<MenuItem> list) {  //Constructor (if info already provided)
		super(type, name, desc, price);
		
		if(list!=null) this.list = list;
		else list= new ArrayList<MenuItem>();
	}
	
	/**
	 * Gets the list of menu items in this set package
	 * @return The list of menu items in this set package
	 */
	public List<MenuItem> getList() {
		return list;
	}
	
	/**
	 * Adds a menu item into the set package<br>
	 * Sorts the list of menu items according to the item type
	 * 
	 * @param item The menu item to be added into the set package
	 */
	public void addItem(MenuItem item) {
		list.add(item);
		setPrice(getPrice()+(item.getPrice()*DISCOUNT));  //update price
		Collections.sort(list);  //sort according to type
	}
	
	/**
	 * Removes a menu item from the set package<br>
	 * Prints out the items in the set after removal
	 */
	public void removeItem() {
		if(list.size()==0) return;
		
		else {
		displayItemDetails();
		System.out.println();
		System.out.println("Enter the index of the item you want to remove:");
		Scanner sc = new Scanner(System.in);
		int x = sc.nextInt();
		
		setPrice(getPrice()-(list.get(x-1).getPrice()*DISCOUNT));
		try { list.remove(x-1);
		}catch(RuntimeException outOfBounds) {
			System.out.println("Please enter a correct index. Try again.");
		}
		displayItemDetails();
		}
	}
	
	/**
	 * Recalculates the price of the set package in the event where there are
	 * changes to the set or price of the items within the set
	 */
	public void recalculatePrice() {   //in case price of MenuItems is updated
		if(list.size()==0) return;
		else {
			double newPrice=0;
			for(int i=0;i<list.size();i++) {
				newPrice+=list.get(i).getPrice();
			}
			setPrice(newPrice*DISCOUNT);
		}
	}
	
    /**
     * Prints out the set name, price and the items inside the set
     */
	@Override
	public void displayItemDetails() {
		System.out.printf(getName());
		System.out.printf("%43s%n",new DecimalFormat("$###,##0.00").format(getPrice()));
		for(int i=0;i<list.size();i++) {
			System.out.println("    "+(i+1)+") "+list.get(i).getName());
		}
		System.out.println();
	}
	
}
