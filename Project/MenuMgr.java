package Project;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class that mainly handles loading and saving the menu
 * 
 * @author Abdul Qaiyum Lee
 *
 */
public class MenuMgr {
	/**
	 * Path of where the menu file is stored
	 */
	private static final String PATH = "menu.txt";
	
	/**
	 * A static version of the menu
	 */
	static List<MenuItem> Menu; 
	
	
	/**
	 * Creates a new menu manager and a blank menu
	 */
	public MenuMgr() {
		Menu= new ArrayList<MenuItem>();
	}
	
	
	/**
	 * Read from file and put into Menu
	 */
	public void loadMenu() {  //read from file and put into array
		FileInputStream fin;
		ObjectInputStream oin;
		try {
			fin = new FileInputStream(PATH);
			oin = new ObjectInputStream(fin);
			
			Object o = oin.readObject();   //every class has Object() as its superclass
			
			if(o instanceof ArrayList)  // the object being read is in arraylist format (because we write arraylist to file)
			{
				ArrayList<?> al = (ArrayList<?>) o;
				
				if(al.size()>0) {
					for(int i=0; i<al.size();i++) {  //iterate the whole file to obtain each menu item
						Object ChildObj = al.get(i);
						if(ChildObj instanceof MenuItem) { //double check if it is menu items
							Menu.add((MenuItem) ChildObj); //add into static menu list
						}
					}
				}
			}
			
			oin.close();
			
			if(Menu.size()!=0) System.out.println("Menu loaded!\n");
			
			
		} catch (Exception E) {
			System.out.println("Menu not loaded!\n");
		}
		
	}
	
	/**
	 * Saves the data to the menu file
	 */
	public void saveMenu() {
		FileOutputStream fos;
		ObjectOutputStream oos;
		try {
			fos = new FileOutputStream(PATH);
			oos= new ObjectOutputStream(fos);
			oos.writeObject(Menu);
			oos.close();
			
		} catch (Exception e) {
			System.out.println("Menu not saved successfully");
		}
		
		System.out.println("Menu saved!");
		
	}
	
	/**
	 * Gets the menu
	 * @return The menu
	 */
	public List<MenuItem> getMenu() {
		return Menu;
	}
	
	/**
	 * Adds a menu item into the menu and sort the menu accordingly
	 * @param type The type of item to be added
	 * @param name The name of the item to be added
	 * @param desc The description of the item to be added
	 * @param price The price of the item to be added
	 */
	public void addItem(MenuItem.ItemType type, String name, String desc, double price) {
		MenuItem newitem = new MenuItem(type, name, desc, price);
		Menu.add(newitem);
		Collections.sort(Menu);
		
	}
	
	/**
	 * Removes a menu item from the menu via its index
	 * @param index Index of the menu item to be removed from the menu
	 */
	public void removeItem(int index) {
		Menu.remove(index);
	}
	
	
	/**
	 * Adds a set package to the menu and sorts the menu accordingly
	 * @param set The set to be added
	 */
	public void addset(SetPackage set) {
		Menu.add(set);
		Collections.sort(Menu);
	} 
	

}
