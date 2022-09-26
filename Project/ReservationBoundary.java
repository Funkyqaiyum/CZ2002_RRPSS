package Project;

import java.text.SimpleDateFormat;
import java.util.*;
/**
 * User Interface for reservation and table related features
 * @author Lee Ming Da
 *
 */
public class ReservationBoundary {
	
	/**
	 * Take in user inputs
	 */
	private static Scanner sc = new Scanner(System.in);
	
	/**
	 * Main Menu of reservation
	 */
	public static void showReservationMenu(){
		int choice;
		
        do {
        	System.out.println();
        	System.out.println("*****   Reservation Manager   *****");
            System.out.println("Select a choice: ");
            System.out.println("1. Show available table");
            System.out.println("2. Make reservation");
            System.out.println("3. Check in reservation");
            System.out.println("4. Remove reservation");
            System.out.println("5. Show all reservations for next 7 days");
        	System.out.println("6. Back");
        	choice = sc.nextInt();
            
            switch (choice) {
                case 1:
                	TableController.showAvailableTables(0);
                	continue;
                case 2:
                	createReservation();
                	continue;
                case 3:
                	checkInCustomer();
                	continue;
                case 4:
                	removeReservation();
                	continue;
                case 5:
                	showAllReservation();
                	continue;
                case 6:
                	ReservationController.saveReservations();
                	TableController.saveTable();
                	// save order to file over here too
                	return;
                /* 
                 * test to free table
                case 7:
                	for(int i=1;i<=20;i++){
                		Table temp = TableController.getTable(i);
                		if(!temp.isAvailable())
                			System.out.println(temp.getTableID()+" orderid="+temp.getOrderID());
        			}		
                	System.out.println("enter tableNo");
                	int free = sc.nextInt();
                	TableController.markUnoccupied(free);
                	continue;
                */
                default:
                	System.out.println("Choose a choice between 1-6");
                	continue;
            }
        } while (choice!=6);
	}
	
	/**
	 * Prompt user to seat customer with reservations. Customer seated to table will have order created and assigned to the table.
	 */
	private static void checkInCustomer() {
		// TODO Auto-generated method stub
		ArrayList<Reservation> todayRsrv = ReservationController.getReservationListByDate(Calendar.getInstance());
		if(todayRsrv.size()==0){
			System.out.println("No reservations for today");
			return;
		}
		int i = 0;
		for(Reservation r: todayRsrv){
			i++;
			System.out.println(i+". "+r);	
    	}
		System.out.println("Select index to check in (0 to go back)");
		i = sc.nextInt();
		if(i==0)
			return;
		try {
			Reservation r = todayRsrv.get(i-1);
			System.out.println("Enter staffID: ");
			int staffId = sc.nextInt();
			if(staffId==0)
				return;
			ReservationController.checkIn(r,staffId);
		}catch(IndexOutOfBoundsException e){
			System.out.println("Invalid index");
			checkInCustomer();
		}
	}

	/**
	 * Prompt user to remove reservation
	 */
	private static void removeReservation() {
		// TODO Auto-generated method stub
		showAllReservation();		
		System.out.println("Select index to remove (0 to go back)");
		int i = sc.nextInt();
		if(i==0)
			return;
		try {
			ReservationController.removeReservationByIndex(i-1);
			System.out.println("Reservation removed");
		}catch(IndexOutOfBoundsException e){
			System.out.println("Invalid index");
			removeReservation();
		}
	}

	/**
	 * Display all reservation for the restaurant
	 */
	private static void showAllReservation() {
		// TODO Auto-generated method stub
		int i = 0;
		for(Reservation r: ReservationController.getAllReservationList()){
			i++;
			System.out.println(i+". "+r);	
    	}
		if(i==0){
			System.out.println("No reservations");
		}
	}

	/**
	 * Prompt user for new reservation
	 */
	private static void createReservation(){
		// TODO Auto-generated method stub
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar booking = Calendar.getInstance();
		int dateChoice = 0;
		System.out.println("Enter pax: ");	
		int pax = sc.nextInt();
		sc.nextLine();
		int maxNoAllowed = TableController.getMaxTablePaxNo();
		if(pax>maxNoAllowed){
			System.out.println("Maximum pax per table is "+maxNoAllowed);
			createReservation();
		}else if(pax<=0){
			System.out.println("Minimum pax per table is 1");
			createReservation();
		}else{
			do{	
				Calendar now = (Calendar) booking.clone();		
			    System.out.println("Select a reservation date choice(Minimum one day and maximum seven days in advance) ");	
			    for(int i=0;i<7;i++){
			    	now.add(Calendar.DATE, 1);
			    	System.out.println((i+1)+". "+dateFormat.format(now.getTime()));
			    }
			    System.out.println("8. Back");
			    dateChoice = sc.nextInt();
			    sc.nextLine();
			    if(dateChoice==8){
			    	return;
			    }else if(dateChoice<1||dateChoice>7){
			    	System.out.println("Please enter a valid choice");	
			    }else{
			    	booking.add(Calendar.DATE, dateChoice);
			    	int hourChoice = 0;
			    	while(hourChoice==0){
			    		System.out.println("Enter reservation hour: (11am-9pm)");
			    		String inputTime = sc.next();
			    		sc.nextLine();
			    		try{
			    			hourChoice = Integer.valueOf(inputTime.replaceAll("\\D+",""));
			    			if(inputTime.contains("pm")&&hourChoice<12){
			    				hourChoice += 12;
			    			}
			    			if(hourChoice<11||hourChoice>=22){
			    				hourChoice = 0;
			    				System.out.println("Please enter a valid choice");
			    			}else{
			    				booking.set(Calendar.HOUR_OF_DAY, hourChoice);
			    				booking.clear(Calendar.MINUTE);
			    				booking.clear(Calendar.SECOND);
			    				booking.clear(Calendar.MILLISECOND);
			    				Reservation add = new Reservation(pax,booking);
			    				if(ReservationController.addReservation(add)){
			    					System.out.println("Enter customer name: ");	
				    				String name = sc.next();
				    				sc.nextLine();
				    				System.out.println("Enter customer contact: ");	
				    				String contact = sc.nextLine();
				    				add.setCustName(name);
				    				add.setContact(contact);
				    				System.out.println("Reservation Added");
			    				}
			    			}
			    		}catch (NumberFormatException ex){
			    			 System.out.println("Invalid Time");
			            }
			    	}
			    }
			}while(dateChoice<1||dateChoice>7);	
		}
		
	}
	

}
