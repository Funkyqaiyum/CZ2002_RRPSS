package Project;

import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * Boundary class for Invoice
 * @author Ravi Soundhariya
 *
 */
public class InvoiceBoundary {
	
	/**
	 * print out the options and
     *calls the functions according to input
	 */
	protected static void invoiceMenu() {
        Scanner sc = new Scanner(System.in);
        while (true) {
        	System.out.println();
        	System.out.println("*****   Invoice Manager   *****");
    		System.out.println("1) Create Invoice");
    		System.out.println("2) Print Report");
    		System.out.println("3) Back");
            
    		System.out.println("Enter your choice: ");
            int choice = sc.nextInt();
            switch (choice){
                case 1:
                    addInvoice();
                    break;
                case 2:
                    printReport();
                    break;


                default:
                    return;
            }
        }
    }
	
	/**
	 * receive input of orderID
	 * if orderID is invalid,  print out error message
	 * else proceed to create and print invoice
	 */
	protected static void addInvoice() {
		
		Scanner sc = new Scanner(System.in);
        int orderID = -1;
        boolean success = false, success_input = false;
        int testOrderID =0;
        
        do {
        	try {
        		System.out.println("Please enter the order id (enter -1 to exit):");
                testOrderID = sc.nextInt();
        		
        	}
        	catch (InputMismatchException ex) {
        		System.out.println("Invalid input! Please try again.\n");
        		sc.nextLine();
        		continue;
        	}
            
            if(OrderControl.getorder(testOrderID)== null) {
            	System.out.println("Invalid OrderID! Please try again.\n");

            }
            else {
            	orderID = testOrderID;
            	testOrderID = -1;
            }
            
        }while(testOrderID != -1);
        
        
       while(orderID != -1 && !success_input) {
    	   try {
    		   System.out.println("Please state if membership is available (true/false):");
           	   success = sc.nextBoolean();
           	 InvoiceManager.createInvoice(orderID, success);
           	 success_input = true;
    	   }
        	catch (InputMismatchException ex) {
        		System.out.println("Invalid input! Please try again.\n");
        		sc.nextLine();
        	}
    	  
        }
        
        
		
	}
	
	/**
	 * receive an input of date
    * iterate through all invoices and print out all invoices
	 */
	protected static void printReport() {
		
		Scanner sc = new Scanner(System.in);
		int fromDay,fromMonth,toDay,toMonth;
		boolean inputs_received = false;
		while(!inputs_received) {
			
		try {
			
		// Get the starting date
         System.out.print("Please enter the starting date(DD/MM):");
         String dateStr = sc.next();
         //dateStr = "11/11";
         String[] date = dateStr.split("/");
         fromDay = Integer.parseInt(date[0]);
         fromMonth = Integer.parseInt(date[1]);
         if(fromDay>32 || fromDay<1 || fromMonth >12 || fromMonth <1)throw new Exception();
		 
         
         //Get the ending date
         System.out.print("Please enter the ending date(DD/MM):");
         dateStr = sc.next();
         String[] date2 = dateStr.split("/");
         toDay = Integer.parseInt(date2[0]);
         toMonth = Integer.parseInt(date2[1]);
         if(toDay>32 || toDay<1 || toMonth >12 || toMonth <1)throw new Exception();
         
         
         inputs_received = true;
         SalesReport.generateReport(fromMonth, fromDay, toMonth, toDay);
        
		}
		catch (InputMismatchException ex) {
    		System.out.println("Invalid input! Please try again.\n");
    		sc.nextLine();
    	}
		
		catch(NumberFormatException ex){
			System.out.println("Invalid input! Please try again.\n");
    		sc.nextLine();
		}
		catch(Exception ex) {
			System.out.println("Ensure valid dates and months are input.");
			System.out.println("Please try again.\n");
    		sc.nextLine();
		}
		
		}
	}

}
