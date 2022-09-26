package Project;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents the Invoice system which prints orders
 * @author Ravi Soundhariya
 *
 */
public class Invoice {
	
	/**
	 * serial number of the invoice
	 */
	protected int invoiceID; 
	
	/**
	 * date the order was created
	 */
	protected int date;
	
	/**
	 * month the order was created
	 */
	protected int month;
	
	/**
	 * time the order was created
	 */
	protected String time;
	
	/**
	 * Membership of the customer 
	 */
	private boolean member;
	
	/**
	 * serial number of the order 
	 */
	private int orderID;
	
	/**
	 * the order that is being paid for, which includes all the items ordered, with their quantities and prices.
	 */
	protected Order order;
	
	/**
	 *  the ID of the staff who took the order
	 */
	private int staffID;
	
	/**
	 * ID of the table where the order was created
	 */
	private int tableID;
	
	/**
	 * the gross price of the order 
	 */
	private double price;
	
	/**
	 * the total amount payable by by customer
	 */
	private double finalPrice;
	
	/**
	 * Creates a new Invoice with the given order and customer membership
	 * @param order is the entire order of the customer, ie, all the items ordered and their quantity and price
	 * @param membership is whether the customer is a member of the restaurant
	 */
	public Invoice(Order order, boolean membership) {
		
		this.order = order;
		
		Random r = new Random();
		this.invoiceID = r.ints(1, 100000, 999999).findFirst().getAsInt();
		
		this.member = membership;
		this.orderID = order.getorderID();
		this.staffID = order.getstaffID();
		this.tableID = order.gettableID();
		this.price = order.getPrice();
		
		
		
		if(this.member) {
			double afterGST = 1.17 * this.price;
			this.finalPrice = 0.85 * afterGST;
		}
		else {
			double afterGST = 1.17 * this.price;
			this.finalPrice = afterGST;
		}
		
		
		LocalDateTime myDateObj = LocalDateTime.now();  
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm:ss");   
	    this.time = myDateObj.format(myFormatObj);  
	    
	    
	    int size = InvoiceManager.invoices.size();
	    Calendar TIMESTAMP = Calendar.getInstance();
        TIMESTAMP.setTime(new Date());
        //System.out.println(TIMESTAMP.getTime());
        TIMESTAMP.add(Calendar.DATE, size);
        
        this.month = TIMESTAMP.get(Calendar.MONTH);
        this.date = TIMESTAMP.get(Calendar.DATE);
        //System.out.println(TIMESTAMP.getTime());
		
	}
	
	
	/**
	 * Prints the invoice
	 */
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("\n");
		stringBuffer.append("Order Details\n");
		stringBuffer.append("ID: " + this.orderID + "\n");
		stringBuffer.append(this.time + "\n");
		stringBuffer.append("Staff: " + this.staffID + "\n");
		stringBuffer.append("-------------------------------------------\n");
		stringBuffer.append(" S/N          Items         Quantity Price\n");
		for (int i = 0; i < this.order.items.size() ; i++) {
		    double cost = this.order.items.get(i).getprice() * this.order.items.get(i).getquantity();
		    stringBuffer.append(String.format("%3d  %-23s %4d   $%5.2f\n", i+1, this.order.items.get(i).getname(), this.order.items.get(i).getquantity(), cost));
		}
		stringBuffer.append("-------------------------------------------\n");
		stringBuffer.append(String.format("                       Service Fee : $%5.2f\n", this.price * 0.1));
		stringBuffer.append(String.format("                               GST : $%5.2f\n", this.price * 0.07));
		stringBuffer.append(String.format("                             Total : $%5.2f\n", this.finalPrice));
		
		return stringBuffer.toString();
	}

}
