package Project;

import java.io.*;
import java.util.*;

/**
 * Table controller class that manages the retrieval and updating of table.
 * @author Xun Yi
 */
public class TableController{

	/**
	 * list of tables for the restaurant
	 */
	private static ArrayList<Table> tables = TableController.loadTable();
	/**
	 * file path for the text file
	 */
	private final static String FILEPATH = "Table.txt";
	
	/**
	 * Load Tables from text file. Will initialize here if file cannot be found
	 * @return List of table of the restaurant
	 */
	public static ArrayList<Table> loadTable(){
		tables = new ArrayList<Table>();
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
						if(temp instanceof Table){
							tables.add((Table) temp);
						}
					}
				}
			}
			oin.close();
		}catch(Exception e){
			System.out.println("No Tables. Initializing");
			for(int i=0; i<5; i++)					
				tables.add(new Table(i, 8));
			for(int i=5; i<15; i++)					
				tables.add(new Table(i, 4));
			for(int i=15; i<20; i++)				
				tables.add(new Table(i, 2));			
			saveTable();
		}
		return tables;
	}
	
	/**
	 * Save tables to text file for future uses.
	 */
	public static void saveTable(){
		FileOutputStream fos;
		ObjectOutputStream oos;
		try{
			fos = new FileOutputStream(FILEPATH);
			oos = new ObjectOutputStream(fos);
			
			oos.writeObject(tables);
			System.out.println("Tables saved.");
			oos.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Add new table to restaurant
	 * @param seats - seat of the new table
	 */
	public static void addTable(int seats) {
		tables.add(new Table(tables.size(),seats));
	}

	/**
	 * Get a table in the restaurant
	 * @param tableID - table ID to find a table in the restaurant
	 * @return table - Table of the table ID
	 */
	public static Table getTable(int tableID) { 
		return tables.get(tableID-1);
	}

	/**
	 * remove a table to restaurant
	 * @param tableID - table ID to remove a table in the restaurant
	 */
	public static void removeTable(int tableID) { 
		tables.remove(tableID-1);
	}

	/**
	 * Find and show the list of available tables currently
	 * @param pax - filter the list to exclude tables whose seats are lesser
	 * @return list of available tables currently
	 */
	public static ArrayList<Table> showAvailableTables(int pax) {
		if(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)<11||Calendar.getInstance().get(Calendar.HOUR_OF_DAY)>21){
		// reservation hours 11am - 9pm
		// comment this part to test after hours
			System.out.println("Restaurant is closed");
			return null;
		}/**/
		ArrayList<Table> showCurrentAvail = getAvailableTableForResrv(Calendar.getInstance(), pax);
		if(showCurrentAvail.size()>0){
			System.out.println("Printing available tables:");
			for(int i=0;i<showCurrentAvail.size();i++) {
				Table temp=showCurrentAvail.get(i);
				System.out.println("Table "+temp.getTableID()+" is available");
			}
		}else{
			System.out.println("No table is available");
		}
		return showCurrentAvail;
	}
	
	/**
	 * Find list of tables that are available for a reservation date and time.
	 * Exclude those that have reservation close to the timing given
	 * @param resDateTime - date and time of the reservation
	 * @param pax - no of customer for the reservation
	 * @return list of tables that meets the reservation timing and seats
	 */
	public static ArrayList<Table> getAvailableTableForResrv(Calendar resDateTime, int pax) {
		ReservationController.removeExpiredResrv();
		ArrayList<Table> checkAvail = new ArrayList<Table>();
		int maxNoAllowed = getMaxTablePaxNo();
		Calendar today = Calendar.getInstance();
		
		if(pax<0||pax>maxNoAllowed){
			System.out.println("Maximum pax per table is "+maxNoAllowed);
		}else{
			for(Table temp: tables){
				boolean taken = false;
				if(temp.getSeats()<pax) continue;
				else if(today.get(Calendar.YEAR)==resDateTime.get(Calendar.YEAR)&&
						today.get(Calendar.MONTH)==resDateTime.get(Calendar.MONTH)&&
						today.get(Calendar.DAY_OF_MONTH)==resDateTime.get(Calendar.DAY_OF_MONTH)&&
						!temp.isAvailable()){
					continue;
				}
				for(Reservation rsrvTemp: temp.getTableReservation()){
					Calendar checkBeforeValidTime = (Calendar) rsrvTemp.getReserveDateTime().clone();
					Calendar checkAfterValidTime = (Calendar) rsrvTemp.getReserveDateTime().clone();
					checkBeforeValidTime.add(Calendar.MINUTE, -30);
					checkAfterValidTime.add(Calendar.MINUTE, 30);
					if(resDateTime.getTime().after(checkBeforeValidTime.getTime()) 
							&&resDateTime.getTime().before(checkAfterValidTime.getTime())){
						taken = true;
						break;
					}
				}
				if(taken)continue;
				checkAvail.add(temp);
			}		
		}
		return checkAvail;
	}

	/**
	 * Change the table availability to false
	 * @param TableID - ID of table that will be used
	 * @param orderId - ID of the order for the table
	 */
	public static void markOccupied(int TableID, int orderId) {
		Table temp = tables.get(TableID-1);
		temp.setAvailability(false);
		temp.setOrderID(orderId);
		System.out.println("Table "+temp.getTableID()+" is now occupied.");
	}

	/**
	 * Free the table 
	 * @param TableID - ID of table being occupied
	 */
	public static void markUnoccupied(int TableID) {
		Table temp =tables.get(TableID-1);
		temp.setAvailability(true);
		System.out.println("Table "+temp.getTableID()+" is now available.");
	}
	/**
	 * Find maximum seat size of a table
	 * @return maximum table seat size of the restaurant
	 */
	@SuppressWarnings("unchecked")
	public static int getMaxTablePaxNo() {
		ArrayList<Table> tempTable = (ArrayList<Table>) tables.clone();
		tempTable.sort(Comparator.comparingInt(Table::getSeats));
		return tempTable.get(tempTable.size()-1).getSeats();
	}
}