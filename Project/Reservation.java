package Project;

import java.io.Serializable;
import java.util.*;

/**
 * representing a table in the restaurant
 * @author Lee Ming Da
 *
 */
@SuppressWarnings("serial")
public class Reservation implements Serializable{
	/**
	 * reservation belonging to the table ID
	 */
	private int tableID;
	/**
	 * no of customer for reservation
	 */
	private int pax;
	/**
	 * date and time of reservation
	 */
	private Calendar reserveDateTime;
	/**
	 * contact info of customer for reservation
	 */
	private String contact;
	/**
	 * name of customer for reservation
	 */
	private String custName;
	
	/**
	 * Create a new reservation having a reservation size and date and time.
	 * @param pax 		No of customer for this reservation
	 * @param datetime 	Date and timing for this reservation
	 */
	public Reservation(int pax, Calendar datetime) {
		this.pax = pax;
		this.reserveDateTime = datetime;
	}

	/**
	 * get the tableID of the table assigned to this reservation
	 * @return this reservation tableID
	 */
	public int getTableNo() {
		return tableID;
	}

	/**
	 * set the tableID of the table assigned to this reservation
	 * @param tableNo - Table ID for this reservation
	 */
	public void setTableNo(int tableNo) {
		this.tableID = tableNo;
	}

	/**
	 * get the contact of the customer for this reservation
	 * @return this reservation customer contact
	 */
	public String getContact() {
		return contact;
	}

	/**
	 * set the contact of the customer for this reservation
	 * @param contact - Contact of the customer
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}

	/**
	 * get the name of the customer for this reservation
	 * @return this reservation customer name
	 */
	public String getCustName() {
		return custName;
	}

	/**
	 * set the name of the customer for this reservation
	 * @param custName - Name of the customer
	 */
	public void setCustName(String custName) {
		this.custName = custName;
	}

	/**
	 * get the no of customer for this reservation
	 * @return no of customer for this reservation
	 */
	public int getPax() {
		return pax;
	}

	/**
	 * set the no of customer for this reservation
	 * @param pax - No of customer
	 */
	public void setPax(int pax) {
		this.pax = pax;
	}

	/**
	 * get the date time of this reservation
	 * @return this reservation date time
	 */
	public Calendar getReserveDateTime() {
		return reserveDateTime;
	}

	/**
	 * set the date and time of this reservation
	 * @param datetime - When is the reservation
	 */
	public void setReserveDateTime(Date datetime) {
		this.reserveDateTime.setTime(datetime);
	}
	
	/**
	 * format the reservation object for output
	 * @return this reservation output text
	 */
	public String toString(){
		return "Table No: " + this.getTableNo() + " reserve by: " + this.getCustName() + 
				" contact: " + this.getContact() + " Reserve time: " + this.getReserveDateTime().getTime();
	}
}
