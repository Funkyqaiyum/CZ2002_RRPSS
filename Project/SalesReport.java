package Project;


/**
 * 
 * @author Ravi Soundhariya
 *
 */
public class SalesReport {
	
	
	/**
	 * receive an input of date
     * iterate through all invoices and print out all invoices
	 * @param fromMonth is the start month
	 * @param fromDate is the start date
	 * @param toMonth is the end month
	 * @param toDate is the end date
	 */
	public static void generateReport(int fromMonth, int fromDate , int toMonth , int toDate) {
		int i =0, total=0;
		for(i=0;i<InvoiceManager.invoices.size();i++) {	
			if(InvoiceManager.invoices.get(i).month > fromMonth || InvoiceManager.invoices.get(i).month == fromMonth) {
				if(InvoiceManager.invoices.get(i).month < toMonth || InvoiceManager.invoices.get(i).month == toMonth) {
					System.out.println(InvoiceManager.invoices.get(i));
					
				}
				else total++;
				
			}
			else total++;
		
	}
		
		if(total == InvoiceManager.invoices.size())System.out.println("There are no orders in that time frame!\n");
	}
		
		
	
	
	
	

}
