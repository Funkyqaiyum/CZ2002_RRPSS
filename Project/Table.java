package Project;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * representing a table in the restaurant
 * @author Xun Yi
 *
 */
@SuppressWarnings("serial")
public class Table implements Serializable{

	/**
	 * size of table
	 */
	private int seats;
	/**
	 * unique identifier of table
	 */
	private int tableID;
	/**
	 * status of table
	 */
	private boolean availability;
	/**
	 * order ID belonging to table
	 */
	private int orderID;
	/**
	 * reservations for the table
	 */
	private ArrayList<Reservation> tableResrv;
	
	/**
	 * Create a new table having an tableID, seats and an empty tableResrv ArrayList. Default table availability is true.
	 * @param tableID 	Table id of the table
	 * @param seats 	Maximum seat size of the table
	 */
	public Table(int tableID, int seats) {
		this.tableID = tableID+1;
		this.setSeats(seats);
		this.setAvailability(true);
		this.tableResrv = new ArrayList<Reservation>();
	}

	/**
	 * get the seat size of the table
	 * @return this table max seats no
	 */
	public int getSeats() {
		return this.seats;
	}

	/**
	 * set the seat size of the table
	 * @param seats - seat size of table
	 */
	public void setSeats(int seats) {
		this.seats = seats;
	}

	/**
	 * get the tableID of the table
	 * @return this table tableID
	 */
	public int getTableID() {
		return this.tableID;
	}

	/**
	 * set the tableID of the table
	 * @param tableID - this table tableID
	 */
	public void setTableID(int tableID) {
		this.tableID = tableID;
	}

	/**
	 * get the availability of the table
	 * @return this table availability
	 */
	public boolean isAvailable() {
		return this.availability;
	}

	/**
	 * set the availability of the table
	 * if unoccupied, remove order ID for this table
	 * @param availability - occupied or unoccupied
	 */
	public void setAvailability(boolean availability) {
		this.availability = availability;
		if(this.availability){
			this.orderID = -1;
		}
	}

	/**
	 * get the order ID of the table
	 * @return this table current order ID
	 */
	public int getOrderID() {
		return this.orderID;
	}

	/**
	 * set the order ID for the table
	 * @param orderID - order ID for this table
	 */
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	/**
	 * get all reservation for this table
	 * @return this table reservation list
	 */
	public ArrayList<Reservation> getTableReservation() {
		return this.tableResrv;
	}

	/**
	 * insert a new reservation for this table
	 * @param newResrv - New reservation for this table
	 */
	public void addTableReservation(Reservation newResrv) {
		this.tableResrv.add(newResrv);
	}
	
	/**
	 * remove a reservation from this tableResrv list
	 * @param tableResrv - Reservation for this table
	 */
	public void removeTableReservation(Reservation tableResrv) {
		this.tableResrv.remove(tableResrv);
	}
}