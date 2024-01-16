/**
 * 
 */
package edu.ncsu.csc216.product_backlog.model.io;

import java.io.PrintStream;
import java.util.ArrayList;
import java.io.File;

import edu.ncsu.csc216.product_backlog.model.product.Product;

/**
 * Writes the products to a file
 * Handles file output
 * @author Ethan Browne 
 */
public class ProductsWriter {
	
	/**
	 *Creates a ProductsWriter object 
	 */
	public ProductsWriter(){
		// Empty Constructor
	}
	/**
	 *Writes an array list of products to a file
	 *@param fileName the name of the file
	 *@param products the list of products to write
	 *@throws IllegalArgumentException if file is unable to be saved
	 */
	public static void writeProductsToFile(String fileName, ArrayList<Product> products) {
		PrintStream fileWriter;
		try {
			fileWriter = new PrintStream(new File(fileName));
		} catch (Exception e) {
			throw new IllegalArgumentException("Unable to save file.");
		}
		
		for (int i = 0; i < products.size(); i++) {
			fileWriter.println("# " + products.get(i).getProductName());
			for (int j = 0; j < products.get(i).getTasks().size(); j++) {
				fileWriter.println(products.get(i).getTasks().get(j).toString());
			}
		}
		

		

		fileWriter.close();
	}
}