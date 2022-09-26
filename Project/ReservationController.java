package Project;


import java.io.*;
import java.util.*;

/**
 * Reservation controller class that manages the creation, retrieval, deleting and updating of reservation.
 * @author Lee Ming Da
 */
public class ReservationController {
	
	/**
	 * list of reservations for this restaurant
	 */
	private static ArrayList<Reservation> reservations = ReservationController.loadReservations();
	/**
	 * file path for the text file
	 */
	private final static String FILEPATH = "Reservations.txt";
	
	/**
	 * Load reservation from text file.
	 * @return list of non expired reservations
	 */
	public static ArrayList<Reservation> loadReservations(){
		reservations = new ArrayList<Reservation>();
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
						if(temp instanceof Reservation){
							reservations.add((Reservation) temp);
						}
					}
				}
			}
			oin.close();
		}catch(Exception e){
			/*
			 * no message cause empty reservation list will come here
			 * 
			 * Initialize to test reservation
			 * Calendar tempDate = Calendar.getInstance();
			 * Reservation r1 = new Reservation(1,8,tempDate,"23414123","testing");
			 * Reservation r2 = new Reservation(15,1,tempDate,"12345678","mingda");
			 * reservations.add(r1);
			 * TableController.getTable(r1.getTableNo()).addTableReservation(r1);
			 * reservations.add(r2);
			 * TableController.getTable(r2.getTableNo()).addTableReservation(r2);
			*/
		}
		return getAllReservationList();
	}
	
	/**
	 * Upload the reservation list to a text file for future use.
	 */
	public static void saveReservations(){
		FileOutputStream fos;
		ObjectOutputStream oos;
		try{
			fos = new FileOutputStream(FILEPATH);
			oos = new ObjectOutputStream(fos);
			
			oos.writeObject(getAllReservationList());
			System.out.print("Reservation list saved. ");
			oos.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Get all reservations for this restaurant
	 * Clear all expired reservation first
	 * @return non expired list of reservations
	 */
	public static ArrayList<Reservation> getAllReservationList() {
    	removeExpiredResrv();
    	reservations.sort(Comparator.comparing(Reservation::getReserveDateTime));
		return reservations;
	}
	
	/**
	 * Find the list of reservation for the restaurant by a given date
	 * @param date - Date to filter the reservation list
	 * @return list of reservation for that given date
	 */
	public static ArrayList<Reservation> getReservationListByDate(Calendar date) {
		removeExpiredResrv();
		ArrayList<Reservation> rsvByDate = new  ArrayList<Reservation>();
		int year = date.get(Calendar.YEAR);
		int month = date.get(Calendar.MONTH);
		int day = date.get(Calendar.DAY_OF_MONTH);
    	for(Reservation rsrv: reservations){
    		Calendar checkDate = rsrv.getReserveDateTime();
    		if(checkDate.get(Calendar.YEAR)==year&&checkDate.get(Calendar.MONTH)==month&&checkDate.get(Calendar.DAY_OF_MONTH)==day){
    			rsvByDate.add(rsrv);
    		}
    	}
		return rsvByDate;
	}
	
	/**
	 * Clear all no show reservations. Reservation that exceed 30 minutes of its booking time will be removed.
	 */
	public static void removeExpiredResrv(){
		Calendar expiredDateTime = Calendar.getInstance();
		expiredDateTime.add(Calendar.MINUTE, -30);
		for (int i = reservations.size()-1; i >= 0; i--){
		    if(reservations.get(i).getReserveDateTime().before(expiredDateTime))
		    	removeReservationByIndex(i);
		 }
	}

	/**
	 * Insert a new reservation
	 * @param rev - New reservation for the restaurant
	 * @return if is successfully added to the list of reservation
	 */
	public static boolean addReservation(Reservation rev) {
		if(rev.getReserveDateTime().get(Calendar.HOUR_OF_DAY)<11||rev.getReserveDateTime().get(Calendar.HOUR_OF_DAY)>21){
			// reservation hours 11am - 9pm/**/
			System.out.println("Reservation time not within operating hours");
			return false;
		}
		ArrayList<Table> hasTable = TableController.getAvailableTableForResrv(rev.getReserveDateTime(),rev.getPax());
		if(hasTable.size()==0){
			System.out.println("No available table for "+rev.getPax()+"pax on "+rev.getReserveDateTime().getTime());
			return false;
		}else{
			hasTable.sort(Comparator.comparingInt(Table::getSeats));
			rev.setTableNo(hasTable.get(0).getTableID());
			reservations.add(rev);
			TableController.getTable(hasTable.get(0).getTableID()).addTableReservation(rev);
			return true;
		}
	}
	
	/**
	 * Check in a customer that has reservation
	 * Remove the reservation and create an order for the table
	 * If assigned table is occupied, find another available table according to no of customer
	 * @param rev - reservation to check in
	 * @param staffId - ID of staff that handles the check in
	 */
	public static void checkIn(Reservation rev, int staffId) {
		int index = reservations.indexOf(rev);
		Reservation temp = reservations.get(index);
		int tableNo = temp.getTableNo();
		boolean seated = true;
		if(!TableController.getTable(tableNo).isAvailable()){
			ArrayList<Table> nextAvailTable = TableController.getAvailableTableForResrv(Calendar.getInstance(),temp.getPax());
			if(nextAvailTable.size()==0){
				System.out.println("Table "+tableNo+" is still occupied. No other available tables.");
				seated = false;	
			}else {
				// assign another available table
				nextAvailTable.sort(Comparator.comparingInt(Table::getSeats));
				tableNo = nextAvailTable.get(0).getTableID();
			}
		}
		if(seated) {
			removeReservationByIndex(index);
			//  creating order
			OrderControl.loadorders();
			Order neworder = new Order(OrderControl.getlist().size(), staffId, tableNo);
			OrderControl.addorder(neworder);
			TableController.markOccupied(tableNo,neworder.getorderID());
			System.out.println("OrderID is "+ neworder.getorderID());
			OrderControl.saveorder();
		}
	}
	
	/**
	 * Remove a reservation from list by their index
	 * @param index - position of reservation in the list
	 */
	public static void removeReservationByIndex(int index) {
		Reservation temp = reservations.get(index);
		reservations.remove(temp);
		TableController.getTable(temp.getTableNo()).removeTableReservation(temp);
	}
}
