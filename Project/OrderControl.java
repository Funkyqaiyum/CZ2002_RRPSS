package Project;
/**
 * Maintains an ArrayList of all the order
 * Can save and load the orders from a file
 * Can add and remove orders
 * @author Jerry Kuan
 * @version 1.0
 * @since 2021-11-13
 */


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
/**
 * 
 * @author Jerry Kuan
 *
 */
public class OrderControl {
	
	/**
	 * Creates an ArrayList storing orders
	 */
	
	static ArrayList <Order>  Orderlist= new ArrayList <Order>(); 
	
	/**
	 * Stores the ArrayList to the FilePath "Orders.txt"
	 */
	private final static String FILEPATH = "Orders.txt";
	
	/**
	 * Loads the ArrayList of Orders from the FilePath "Orders.txt"
	 */
	public static void loadorders(){
		FileInputStream fin;
		ObjectInputStream oin;
		try{
			fin = new FileInputStream(FILEPATH);
			oin = new ObjectInputStream(fin);
			
			Object o = oin.readObject();
			
			if(o instanceof ArrayList){
				ArrayList<?> list = (ArrayList<?>) o;
				if(list.size()>0){
					for(Object temp: list){
						if(temp instanceof Order){
							Orderlist.add((Order) temp);
						}
					}
				}
			}
			oin.close();

		}catch(Exception e){
			System.out.println("Order not loaded");
			
		}
	}
	
	/**
	 * Saves the ArrayList of Orders to the FilePath "Orders.txt"
	 */
	public static void saveorder(){
		FileOutputStream fos;
		ObjectOutputStream oos;
		try{
			fos = new FileOutputStream(FILEPATH);
			oos = new ObjectOutputStream(fos);
			
			oos.writeObject(Orderlist);
			oos.close();
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		System.out.print("OrderList saved. ");
	}	
	
	/**
	 * Adds an order to the ArrayList containing orders
	 * @param order This order
	 */
	 public static void addorder(Order order){      
		 Orderlist.add(order); 
	 } 
	 
	 /**
	  * Removes an order from the ArrayList containing orders
	  * @param order This order to remove
	  */
	 public static void removeorder(Order order) {
		 Orderlist.remove(order);
	 }	 

	/**
	 * returns this ArrayList containing orders
	 * @return this ArayList containing orders
	 */
	public static ArrayList<Order> getlist() {
		return Orderlist;
	}
	
	/**
	 * Returns the order when given the id of the order
	 * @param theorderid serial number of the order 
	 * @return Returns the order
	 */
	public static Order getorder(int theorderid){
	    for(Order a : Orderlist) {
	        if(a.getorderID()==theorderid) {
	            return a;
	        }
	    }
		return null;
		}
	
}