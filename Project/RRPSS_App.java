package Project;

import java.util.*;
/**
 * 
 * @author Ravi Soundhariya
 *
 */
public class RRPSS_App {
	
	/**
	 * Main function for the user to interact with
	 * @param args Main function input
	 */
	public static void main(String[] args) {
		ArrayList<Table> tables = TableController.loadTable();
		ArrayList<Reservation> reservations = ReservationController.loadReservations();
		OrderControl.loadorders();
		MenuMgr manager = new MenuMgr();
		manager.loadMenu();
		MenuBoundary main = new MenuBoundary(manager);
		
		    int value=1;
		    Scanner sc = new Scanner(System.in);
		
			StringBuffer sb = new StringBuffer();
			sb.append("\n*****   Restaurant App   *****\n");
			sb.append("1. Menu\n");
			sb.append("2. Order\n");
			sb.append("3. Reservation\n");;
			sb.append("4. Invoice/Sales Report\n");
			sb.append("5. Quit Application\n");

		     String str = sb.toString();
		     boolean x = true;
		     do {
		    	 
		    	 System.out.println(str);
		    	 System.out.print("Enter your choice = ");
		    	 value = sc.nextInt();
		    	 
		    	 switch(value) {
		    	 
		    	 case 1:
		    
		    		 main.pickChoice();
		    		 break;
		    	 
		    	 
		    	 case 2:
		    		 OrderBoundary.loadorder();
		    		
		    		 break;
		    	 
		    	 case 3:
		    		 ReservationBoundary.showReservationMenu();
		    		 break;
		    		 
		    	 case 4:
		    		 InvoiceBoundary.invoiceMenu();
		    		 break;
		    		 
		    	 case 5:{
		    		 System.out.println("Closing Application.....");
		    		 manager.saveMenu();
		    		 OrderControl.saveorder();
		    		 TableController.saveTable();
		    		 ReservationController.saveReservations();
		    		 x=false;
		    		 break;
		    	 }
		    		 
		    	
		    	default :
		    		break;
		    		 
		    		 
		    	 }
		    	 
		     }while(x);
		      
		      
		      
		    	  
		    	 
		    	 
		    	
		    		 
	

}
}
