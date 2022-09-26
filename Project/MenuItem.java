package Project;
import java.io.Serializable;

/**
 * Represents an individual item in the menu<br>
 * Implements the Serializable interface to allow menu items to be saved<br>
 * Implements the Comparable interface to allow menu items to be sorted by item type
 * 
 * @author Abdul Qaiyum Lee
 * 
 */
public class MenuItem implements Serializable, Comparable<MenuItem> {
	/**
	 * Serial Version ID generated
	 */
	private static final long serialVersionUID = 2410018395509734077L;
	
	
	/**
	 * 	
	 * Enumeration type used to determine the type of menu item
	 * 
	 * @author Abdul Qaiyum Lee
	 */
	public static enum ItemType
	{	
		/**
		 * Main Course type
		 */
		Main("Main Course"),
		/**
		 * Drink type
		 */
		Drink("Drink"),
		/**
		 * Dessert type
		 */
		Dessert("Dessert"),
		/**
		 * Set Package type
		 */
		Set("Set Package");
		
		/**
		 * Full name of the item type
		 */
		private final String value;		//String inside the bracket
		
		/**
		 * Constructor for itemType
		 * @param value full name of item type
		 */
		private ItemType(String value) {  //constructor
			this.value=value;
		}
		
		/**
		 * Returns full name of item type in string format
		 * @return String of full name of item type
		 */
		public String toStrValue() {  //function to get string in bracket
			return value;
		}
		
	}
	
	
	/**
	 * Type of food item in the menu, uses ItemType enum
	 */
	private ItemType type;
	/**
	 * The name of this menu item
	 */
	private String name;
	/**
	 * The description of this menu item
	 */
	private String desc;
	/**
	 * The price of this menu item
	 */
	private double price;
	
	/**
	 * Creates a new Menu item
	 * @param type This menu item's type
	 * @param name The name of this menu item
	 * @param desc The description of this menu item
	 * @param price The price of this menu item
	 */
	public MenuItem(MenuItem.ItemType type, String name, String desc, double price) {
		super();
		this.type = type;
		this.name = name;
		this.desc = desc;
		this.price = price;
	}

	/**
	 * Gets the menu item's type
	 * 
	 * @return This menu item's type
	 */
	public ItemType getType() {
		return type;
	}
	
	/**
	 * Changes the item type of this menu item
	 * 
	 * @param type The new item type of this menu item
	 */
	public void setType(ItemType type) {
		this.type = type;
	}
	
	
	/**
	 * Gets the name of this menu item
	 * 
	 * @return The name of this menu item
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Changes the name of this menu item
	 * 
	 * @param name The new name of this menu item
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
	/**
	 * Gets the descirption of this menu item
	 * 
	 * @return The description of this menu item
	 */
	public String getDesc() {
		return desc;
	}
	
	
	/**
	 * Changes the description of this menu item
	 * 
	 * @param desc The new description of this menu item
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	/**
	 * Gets the price of this menu item
	 * 
	 * @return The price of this menu item
	 */
	public double getPrice() {
		return price;
	}
	
	
	/**
	 * Changes the price of this menu item
	 * 
	 * @param price The new price of this menu item
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	
	
	/**
	 * Displays the details of this menu item.<br>
	 * Name, Description and Price
	 */
	public void displayItemDetails() {
		System.out.printf("%-18s",getName());
		System.out.printf("%-25s",getDesc());
		System.out.printf("$%.2f \n",getPrice());
	}
	

	/**
	 * Function used to compare menu items based on their item type<br>
	 * Used when calling <b>Collections.sort</b>
	 * 
	 * @param x Menu item to be compared with
	 */
	@Override
	public int compareTo(MenuItem x) {
		return this.getType().compareTo(x.getType());
	}


}
