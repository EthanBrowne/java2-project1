/**
 * 
 */
package edu.ncsu.csc216.product_backlog.model.io;

import java.util.ArrayList;
import java.util.Scanner;

import java.io.FileInputStream;

import edu.ncsu.csc216.product_backlog.model.product.Product;
import edu.ncsu.csc216.product_backlog.model.task.Task;

/**
 * Reads the products from a file
 * Handles file input
 * @author Ethan Browne 
 */
public class ProductsReader {
	
	/**
	 *Creates a ProductsReader object 
	 */
	public ProductsReader(){
		// Empty Constructor
	}
	/**
	 *Reads the file and constructs task and product objects from the information in the file
	 *Checks too see if certain information that is being read is valid. If not throws an exception that is handled accordingly
	 *@param fileName the name of the file
	 *@return ArrayList of products
	 *@throws IllegalArgumentException if fileName is invalid
	 */
	public static ArrayList<Product> readProductsFile(String fileName) {
		Scanner fileReader;
		String fileString = "";
		ArrayList<Product> productList = new ArrayList<Product>();
		try {
			fileReader = new Scanner(new FileInputStream(fileName));
		} catch (Exception e) {
			throw new IllegalArgumentException("Unable to load file.");
		}
		while (fileReader.hasNextLine()) {
			fileString = fileString + fileReader.nextLine() + "\n";
		}
		fileReader.close();
		Scanner lineReader = new Scanner(fileString);
		lineReader.useDelimiter("\\r?\\n?[#] ");
		while (lineReader.hasNext()) {
			try {
				productList.add(proccessProduct(lineReader.next()));
			} catch (Exception e) {
				//Skip
			}
		}
		lineReader.close();
		return productList;
	}
	/**
	 *Processes a string with a task in it and returns the task
	 *@param taskString the string with the task
	 *@return the task that is returned
	 *@throws IllegalArgumentException if information is invalid
	 */
	private static Task proccessTask(String taskString) {
		Scanner taskReader = new Scanner(taskString);
		taskReader.useDelimiter(",");
		int id = 0;
		String state = null;
		String title = null;
		String type = null;
		String creator = null;
		String owner = null;
		String verified = null;
		ArrayList<String> notes = new ArrayList<String>();
		try {
			id = taskReader.nextInt();
			state = taskReader.next();
			title = taskReader.next();
			type = taskReader.next();
			creator = taskReader.next();
			owner = taskReader.next();
			if ("Backlog".equals(state) && !"unowned".equals(owner) || "Rejected".equals(state) && !"unowned".equals(owner)) {
				taskReader.close();
				throw new IllegalArgumentException("Illegal onwer");
			} else if ("unowned".equals(owner) && !"Backlog".equals(state) && !"Rejected".equals(state)) {
				taskReader.close();
				throw new IllegalArgumentException("Illegal unowned owner");
			}
			taskReader.useDelimiter("\\r?\\n?[-] ");
			verified = taskReader.next();
			verified = verified.substring(1, verified.length());
			if ("Done".equals(state) && "false".equals(verified) && !"KA".equals(type)) {
				taskReader.close();
				throw new IllegalArgumentException("Illegal false verification");
			} else if ("Done".equals(state) && "true".equals(verified) && "KA".equals(type)) {
				taskReader.close();
				throw new IllegalArgumentException("Illegal true verification");
			}
			while (taskReader.hasNext()) {
				notes.add(taskReader.next().trim());
			}
		} catch (Exception e) {
			taskReader.close();
			return null;
		}
		
		taskReader.close();
		return new Task(id, state, title, type, creator, owner, verified, notes);
	}
	/**
	 *Processes a string with a product in it and returns the product
	 *@param productString the string with the product
	 *@return the product that is returned
	 *@throws IllegalArgumentException if not valid product
	 */
	private static Product proccessProduct(String productString) {
		Scanner productReader = new Scanner(productString);
		productReader.useDelimiter("\n");
		String productName = productReader.next();
		if (productName.contains("*")) {
			productReader.close();
			throw new IllegalArgumentException("Not a valid product");
		}
		Product p = new Product(productName);
		productReader.useDelimiter("\\r?\\n?[*] ");
		while (productReader.hasNext()) {
			String taskString = productReader.next();
			if (proccessTask(taskString) != null) {
				p.addTask(proccessTask(taskString));
			}
		}
		productReader.close();
		if (p.getTasks().size() == 0) {
			throw new IllegalArgumentException("Not a valid product");
		}
		return p;
	}
}