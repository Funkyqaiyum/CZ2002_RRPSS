package Project;
/**
 * Represents an item ordered 
 * Many items can be ordered
 * @author Jerry Kuan
 * @version 1.0
 * @since 2021-11-13
 */


import java.io.Serializable;
/**
 * 
 * @author Jerry Kuan
 *
 */
public class OrderItem implements Serializable {
	
	/**
	 * The name of the item ordered
	 */
	private String name;
	
	/**
	 * The price of the item ordered
	 */
	private double price;
	
	/**
	 * The quantity of this item ordered 
	 */
	private int quantity;
	
	/**
	 * Creates a new item/s ordered with the given name of item, price of item, and quantity
	 * @param name The item's name
	 * @param price The student's price
	 * @param quantity The quantity ordered
	 */
	
	public OrderItem(String name, double price, int quantity) {
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}
 
	/**
	 * Gets the name of this item ordered
	 * @return this item's name
	 */
	
	public String getname(){
		return name;
	}
	
	/**
	 * Gets the total price of this item ordered
	 * @return This item's total price
	 */
	public double getprice() {
		return price;
	
	}
	
	/**
	 * Gets the quantity of this item ordered
	 * @return This item's quantity
	 */
	public int getquantity() {
		return quantity;
	}
	
	/**
	 * Sets the quantity of this item ordered
	 * @param qty This item's quantity
	 */
	public void setquantity(int qty) {
		this.quantity = qty;
}
}
