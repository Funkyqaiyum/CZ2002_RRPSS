

package Project;
import java.util.ArrayList;

/**
 * 
 * @author Ravi Soundhariya
 *
 */
public class InvoiceManager {
	
	/**
	 * holds all the invoices created 
	 */
	protected static ArrayList<Invoice> invoices = new ArrayList<Invoice>();
	
	
	/**
	 * append the invoice to the ArrayList of invoices for future reference
	 * @param invoice an instance of invoice
	 */
	public void add(Invoice invoice) {
		invoices.add(invoice);
	}
	
	/**
	 * 
	 * @param orderID  is the serial number of the order being paid for
	 * @param membership states whether the customer has a membership
	 */
	public static void createInvoice(int orderID, boolean membership) {
		
		// get the order obj using the orderID
		// and then give it to the Invoice class ----  Invoice invoice = new Invoice(order,member)
		// then add this invoice to the arraylist ---- invoices.add(invoice);
        // then print it out ----- printInvoice(invoice);  --- got the method alr
		
		Order order = OrderControl.getorder(orderID);
		Invoice invoice = new Invoice(order,membership);
		invoices.add(invoice);
		InvoiceManager.printInvoice(invoice);
		int tableid= OrderControl.Orderlist.get(orderID).gettableID();
		TableController.markUnoccupied(tableid);
		TableController.saveTable();
	}
	
	/**
	 * prints invoice by passing in created invoice instance
	 * @param invoice is the instance of invoice
	 */
	 public static void printInvoice(Invoice invoice) {
	        System.out.println(invoice);
	    }

}

