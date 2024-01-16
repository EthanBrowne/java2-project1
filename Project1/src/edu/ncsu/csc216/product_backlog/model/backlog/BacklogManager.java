/**
 * 
 */
package edu.ncsu.csc216.product_backlog.model.backlog;

import java.util.ArrayList;

import edu.ncsu.csc216.product_backlog.model.command.Command;
import edu.ncsu.csc216.product_backlog.model.io.ProductsReader;
import edu.ncsu.csc216.product_backlog.model.io.ProductsWriter;
import edu.ncsu.csc216.product_backlog.model.product.Product;
import edu.ncsu.csc216.product_backlog.model.task.Task;
import edu.ncsu.csc216.product_backlog.model.task.Task.Type;

/**
 * Manages all of the products and interacts with the GUI
 * This class implements the Singleton design pattern. This means that only one instance of the BacklogManager can ever be created.
 * This is the reason why the constructor is private and why the getInstance method calls the constructor
 * @author Ethan Browne
 */
public class BacklogManager {
	/**Singleton Instance of BacklogManager*/
	private static BacklogManager instance;
	/**List that represents all the products in the backlog*/
	private ArrayList<Product> products;
	/**Represents the current product that is selected by the user*/
	private Product currentProduct;
	
	/**
	 * Private constructor because BacklogManager implements the Singleton design pattern. Constructor is called through getInstance method
	 */
	private BacklogManager() {
		clearProducts();
	}
	/**
	 * Checks to see if the singleton is null and if it is creates a instance of BacklogManager with constructor
	 * @return instance of Backlog Manager
	 */
	public static BacklogManager getInstance() {
		if (instance == null) {
			instance = new BacklogManager();
			return instance;
		} else {
			return instance;
		}
	}
	/**
	 * Sets the singleton to null
	 * This method is protected to restrict where it can be called. It’s intended for testing the BacklogManager class.
	 */
	protected void resetManager(){
		instance = null;
	}
	/**
	 * Creates a new Product with the given name and adds it to the end of the products list
	 * @param productName the name of the product
	 * @throws IllegalArgumentException if productName is invalid
	 */
	public void addProduct(String productName) {
		if (productName == null || productName.length() == 0) {
			throw new IllegalArgumentException("Invalid product name.");
		}
		for (int i = 0; i < this.products.size(); i++) {
			if (productName == this.products.get(i).getProductName()) {
				throw new IllegalArgumentException("Invalid product name.");
			}
		}
		this.products.add(new Product(productName));
		loadProduct(productName);
	}
	/**
	 * Updates the currentProduct’s name to the given value
	 * @param updateName the new name of the products
	 * @throws IllegalArgumentException if no product is selected
	 * @throws IllegalArgumentException if productName is invalid
	 */
	public void editProduct(String updateName) {
		if (currentProduct == null) {
			throw new IllegalArgumentException("No product selected.");
		}
		for (int i = 0; i < this.products.size(); i++) {
			if (updateName == this.products.get(i).getProductName()) {
				throw new IllegalArgumentException("Invalid product name.");
			}
		}
		for (int i = 0; i < this.products.size(); i++) {
			if (getProductName() == this.products.get(i).getProductName()) {
				this.products.get(i).setProductName(updateName);
			}
		}
		loadProduct(updateName);
	}
	
	/**
	 * Deletes the currentProduct
	 * @throws IllegalArgumentException if no product is selected
	 */
	public void deleteProduct() {
		if (currentProduct == null) {
			throw new IllegalArgumentException("No product selected.");
		}
		products.remove(currentProduct);
		if (products.size() > 0) {
			this.currentProduct = products.get(0);
		} else {
			this.currentProduct = null;
		}
	}
	/**
	 * Find the Product with the given name in the list, make it the active or currentProduct
	 * @param productName the name of the product
	 * @throws IllegalArgumentException if product can not be found
	 */
	public void loadProduct(String productName) {
		boolean b = true;
		for (int i = 0; i < this.products.size(); i++) {
			if (productName == this.products.get(i).getProductName()) {
				currentProduct = this.products.get(i);
				b = false;
			}
		}
		if (b) {
			throw new IllegalArgumentException("Product not available.");
		}
	}
	/**
	 * Returns the name of the current product. if no product is selected then it returns null
	 * @return the name of the current product
	 */
	public String getProductName() {
		if (currentProduct == null) {
			return null;
		}
		return currentProduct.getProductName();
	}
	/**
	 * Returns a String array of product names in the order they are listed in the products list. This is used by the GUI to populate the product drop down.
	 * @return array of product names
	 */
	public String[] getProductList() {
		String[] names = new String[products.size()];
		for (int i = 0; i < products.size(); i++) {
			names[i] = products.get(i).getProductName();
		}
		return names;
	}
	/**
	 * Resets products to an empty list. The currentProduct is set to null.
	 */
	public void clearProducts() {
		currentProduct = null;
		products = new ArrayList<Product>();
	}
	/**
	 * Reads from a file and adds all of the new Products in the file to the end of product list. The first product in the list is made the currentProduct
	 * @param fileName the name of the file
	 */
	public void loadFromFile(String fileName){
		ArrayList<Product> loadedProducts = ProductsReader.readProductsFile(fileName);
		currentProduct = loadedProducts.get(0);
		for (int i = 0; i < loadedProducts.size(); i++) {
			products.add(loadedProducts.get(i));
		}
		
	}
	/**
	 * Writes the products in the list to the file named fileName
	 * @param fileName the name of the file
	 * @throws IllegalArgumentException if unable to save file because current product is null or has no tasks
	 */
	public void saveToFile(String fileName) {
		if (currentProduct == null || currentProduct.getTasks().size() == 0) {
			throw new IllegalArgumentException("Unable to save file.");
		}
		ProductsWriter.writeProductsToFile(fileName, products);
	}
	/**
	 * Returns a 2d string array that represents the task in the current product
	 * @return a 2d string array with task id, state name, type as long string, and title
	 */
	public String[][] getTasksAsArray() {
		if (currentProduct == null) {
			return null;
		}
		String[][] stringList = new String[currentProduct.getTasks().size()][4];
		for (int i = 0; i < currentProduct.getTasks().size(); i++) {
			stringList[i][0] = String.valueOf(currentProduct.getTasks().get(i).getTaskId());
			stringList[i][1] = currentProduct.getTasks().get(i).getStateName();
			stringList[i][2] = currentProduct.getTasks().get(i).getTypeLongName();
			stringList[i][3] = currentProduct.getTasks().get(i).getTitle();
			
		}
		return stringList;
	}
	/**
	 * Returns a task that matches the id given
	 * @param id the id of the task
	 * @return task the task that has the id
	 */
	public Task getTaskById(int id) {
		if (currentProduct == null) {
			return null;
		}
		return currentProduct.getTaskById(id);
	}
	/**
	 * Executes a command on a specific task in the taskList
	 * @param id the ID of the specific task to execute command on
	 * @param c the command to execute
	 */
	public void executeCommand(int id, Command c) {
		getTaskById(id).update(c);
	}
	/**
	 * Deletes a task in the product given an id
	 * @param id the task id to delete
	 */
	public void deleteTaskById(int id) {
		if (currentProduct != null) {
			currentProduct.deleteTaskById(id);
		}
	}
	/**
	 * Adds a task to the product given the following parameters
	 * @param title the title of the task
	 * @param type the type of the task
	 * @param creator the creator of the task
	 * @param note the details of the task
	 */
	public void addTaskToProduct(String title, Type type, String creator, String note) {
		currentProduct.addTask(title, type, creator, note);
		
	}
}
