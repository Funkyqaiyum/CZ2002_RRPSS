package Project;
/**
 * The boundary for orders for the staff to interact with
 * Can create a new order
 * Can view an existing order
 * Can add items to order
 * Can remove items from order
 * @author Jerry Kuan
 * @version 1.0
 * @since 2021-11-13
 */


import java.util.*;
/**
 * 
 * @author Jerry Kuan
 *
 */
public class OrderBoundary{

/**
 * The size of the ArrayList containing orders
 */
protected static int i = OrderControl.Orderlist.size();

/**
 * Option to create a new order
 * Option to view order
 * Option to add items to order
 * Option to remove items from order
 * Option to exit
 */
public static void loadorder(){
	boolean b =true;
	while (b){
		    System.out.println();
		    System.out.println("*****   Order Manager   *****");
			System.out.println("1: Create Order");
			System.out.println("2: View Order");
			System.out.println("3: Add items to order");
			System.out.println("4: Remove items from order");
			System.out.println("5: Exit");
			
			// get the arraylist
		    //ArrayList<Order> Orderlist2 = OrderControl.getlist();
		    // get the scanner 
			Scanner sc = new Scanner(System.in);
			int num = sc.nextInt();
		
			switch (num) { 
			case 1:	
				System.out.println("Enter pax: ");
				int pax = sc.nextInt();
				
				try {
					ArrayList<Table>check = TableController.showAvailableTables(pax);
					if(check.size()==0) break;
					System.out.println("Enter table number: ");
					int num2 = sc.nextInt();				
					boolean foundTable = false;
					
					for(Table temp:check){
						if(temp.getTableID()==num2){
							foundTable = true;
							break;
						}
					}
					
					if(!foundTable){
						System.out.println("Please try again");
						break;
					}
				
					System.out.println("Enter staffID: ");
					int num3 = sc.nextInt();
					Order neworder = new Order(i, num3, num2);
					OrderControl.addorder(neworder);
					System.out.println("OrderID is "+ i);
					TableController.markOccupied(num2,i);
					i++;
					
				}catch(Exception E)
				{
					System.out.println("Please try again");
				}	
				break;
			
			case 2:
				if(OrderControl.Orderlist.size() == 0) {
					System.out.println("There are no orders currently.");
					break;
				}
				System.out.println("OrderIDs:");
				for(Order o:OrderControl.Orderlist){
					System.out.println(o.getorderID());
				}
				
				System.out.println();
				System.out.println("Enter orderid: ");
				
				int id = sc.nextInt();
				try {
					OrderControl.Orderlist.get(id).view();
					
				}catch(Exception E)
				{
					System.out.println("Please enter valid orderid");
				}	
				break;
			case 3:
				/**/
				System.out.println("OrderIDs:");
				for(Order o:OrderControl.Orderlist){
					System.out.println(o.getorderID());
				}
				System.out.println();
				
				System.out.println("Enter orderid: ");
				int num4 = sc.nextInt();
				try {
					OrderControl.Orderlist.get(num4);
					
				}catch(Exception E)
				{
					System.out.println("Please enter valid orderid");
					break;
				}
				sc.nextLine();
				MenuBoundary.displayMenu();
				int y = 1;
				while (y!=0){
				System.out.println("Enter the name of the menu item: ");
				String name = sc.nextLine();
				System.out.println("Enter quantity: ");
				int num6 = sc.nextInt();
				
				try {
					int x=0;
					for (int i=0; i<MenuMgr.Menu.size();i++) {
						if( name.equals(MenuMgr.Menu.get(i).getName())){
							 x=i;
						}	
					}
					OrderItem example2 = new OrderItem(MenuMgr.Menu.get(x).getName(), MenuMgr.Menu.get(x).getPrice(),num6);
					OrderControl.Orderlist.get(num4).add(example2);	
					
				}catch(Exception E)
				{
					System.out.println("Please try again");
				}	
					System.out.println("Enter 0 to exit, 1 to continue");
					int num11 = sc.nextInt();
					sc.nextLine();
					if (num11==0) {
						y = 0;
					}	
				}
				break;			
			case 4:
				System.out.println("Enter orderid:");
				int num7 = sc.nextInt();
				sc.nextLine();
				try {
					OrderControl.Orderlist.get(num7).view();
					
				}catch(Exception E)
				{
					System.out.println("Please enter valid orderid");
					break;
				}
				System.out.println("Enter item number to remove: ");
				int num8= sc.nextInt();
				System.out.println("Enter quantity to remove: ");
				int num9 = sc.nextInt();
				try {
					OrderControl.Orderlist.get(num7).remove(num8-1, num9);
					
				}catch(Exception E)
				{
					System.out.println("Please try again");
				}
				break;
			case 5:
				OrderControl.saveorder();
				TableController.saveTable();
				b=false;
				break;
			}
		}
}
}
