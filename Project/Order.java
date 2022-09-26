package Project;

/**
 * Represents an order created by the staff
 * @author Jerry Kuan
 * @version 1.0
 * @since  2021-11-13
 */



import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
/**
 * 
 * @author Jerry Kuan
 *
 */
public class Order implements Serializable {
	
	/**
	 * The ID number of the order
	 */
	private int orderID;
	
	/**
	 * The ID number of the staff
	 */
	private int staffID;
	
	/**
	 * The ID number of the table
	 */
	private int tableID;
	
	/**
	 * The total price of the order
	 */
	private double price=0;
	
	/**
	 * Creates an ArrayList storing items ordered
	 */
	protected ArrayList<OrderItem> items = new ArrayList<OrderItem>(); 
	
	/**
	 * Creates a new order with the id of the order, id of staff, id of table
	 * @param orderid The ID of the order
	 * @param staffid The ID of the staff
	 * @param tableid THe ID of the table
	 */
	public Order(int orderid, int staffid, int tableid) {
		orderID = orderid;
		staffID = staffid;
		tableID = tableid;
	}
	
	
	/**
	 * Adds an item ordered to the ArrayList storing items ordered
	 * @param a is the item to be added
	 */
	public void add(OrderItem a) {
		items.add(a);
		this.price += a.getquantity()*a.getprice();
	}
	
	/**
	 * Removes an item ordered from the ArrayList
	 * Updates the total price 
	 * @param id The id of the item ordered
	 * @param qty The quantity to remove
	 */
	public void remove(int id, int qty) {
		if (items.get(id).getquantity()<=qty) {
			this.price -=items.get(id).getprice()*items.get(id).getquantity();
			items.remove(items.get(id));
			
		}
		else {
			items.get(id).setquantity(items.get(id).getquantity()-qty);
			this.price -= items.get(id).getprice()*qty;
		}

	}
	
	/**
	 * Prints out all the items ordered
	 * Prints out the names of all items ordered
	 * Prints out the quantity of each item ordered
	 * Prints out the total price
	 */
	public void view() {
	    
		System.out.println("No.\t   "+"Item\t\t\t"+"Quantity");
		for(int i = 0; i < items.size(); i++)
		{
		    System.out.print(String.format("|%-10d",i+1));
			System.out.print(String.format("|%-20s|",items.get(i).getname()));
		    System.out.println(items.get(i).getquantity());
		}
			System.out.print("Total Price:\t\t");
			System.out.printf(new DecimalFormat("$###,##0.00").format(this.price));
		     System.out.println();
		
	}
	
	/**
	 * Gets the ID of this order
	 * @return Returns the OrderID
	 */
	
	public int getorderID() {
		return orderID;
	}
	
	/**
	 * Sets the id of the order
	 * @param orderid The id of the order
	 */

	public void setorderID(int orderid) {
		this.orderID = orderid;
	}
	
	/**
	 * Gets the id of the staff
	 * @return Returns the StaffID
	 */

	public int getstaffID() {
		return staffID;
	}
	
	/**
	 * sets the ID of the staff
	 * @param staffid The id of the staff
	 */

	public void setstaffID(int staffid) {
		this.staffID = staffid;
	}
	
	/**
	 * Gets the ID of the table
	 * @return tableID The ID of the table
	 */
	public int gettableID() {
		return tableID;
	}
	
	/**
	 * sets the id of the table
	 * @param tableid The id of the table
	 */
	public void settableID(int tableid) {
		this.tableID = tableid;
	}

	/** 
	 * Gets the total price of all the items ordered
	 * @return Returns the total price of all the items ordered
	 */
	public double getPrice() {
		return price;
	}
	
	/** 
	 * Gets the ArrayList of all the items ordered for this object
	 * @return Returns the ArrayList of all the items ordered for this object
	 */
	public ArrayList<OrderItem> getorderitem(){
		return this.items;
	}	
		}	



