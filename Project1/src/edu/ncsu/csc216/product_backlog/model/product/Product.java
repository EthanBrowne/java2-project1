/**
 * 
 */
package edu.ncsu.csc216.product_backlog.model.product;

import java.util.ArrayList;

import edu.ncsu.csc216.product_backlog.model.command.Command;
import edu.ncsu.csc216.product_backlog.model.task.Task;
import edu.ncsu.csc216.product_backlog.model.task.Task.Type;

/**
 * The product class manages a list of tasks under it
 * Contains an ArrayList of tasks that represent the tasks that are needed to be completed for the product
 * @author Ethan Browne
 */
public class Product {
	/**The name of the product*/
	private String productName;
	/**List of tasks that the products has*/
	private ArrayList<Task> taskList;
	/**The id of the next task that will be added to the task list*/
	private int counter;
	
	/**
	 * Creates a product and sets the name, counter to 0, and creates an empty task list
	 * @param name the name of the product
	 */
	public Product(String name) {
		setProductName(name);
		setTaskCounter();
		taskList = new ArrayList<Task>();
	}
	/**
	 * adds the task to the list in sorted order by id
	 * @param task the task to add to the list
	 */
	public void addTask(Task task) {
		int pos = 0;
		for (int i = 0; i < taskList.size(); i++) {
			if (taskList.get(i).getTaskId() < task.getTaskId()) {
				pos = i + 1;
			} else if (taskList.get(i).getTaskId() == task.getTaskId()) {
				throw new IllegalArgumentException("Task cannot be added.");
			}
		}
		taskList.add(pos, task);
		setTaskCounter();
	}
	
	/**
	 * creates a new task and adds the task to the list 
	 * @param title the title of the task
	 * @param type the type of task
	 * @param creator the creator of the task
	 * @param note the notes related to the task
	 */
	public void addTask(String title, Type type, String creator, String note) {
		Task t = new Task(this.counter, title, type, creator, note);
		addTask(t);
	}

	/**
	 * Returns the products name
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * Sets the products name
	 * @param productName the productName to set
	 * @throws IllegalArgumentException if name is null or empty
	 */
	public void setProductName(String productName) {
		if (productName == null || productName.length() == 0) {
			throw new IllegalArgumentException("Invalid product name.");
		}
		this.productName = productName;
	}

	/**
	 * returns the list of tasks
	 * @return the taskList
	 */
	public ArrayList<Task> getTasks() {
		return taskList;
	}
	/**
	 * Finds the largest task id in the task list and sets the counter field to the max + 1
	 * If the list is empty, the counter is set to 1
	 */
	private void setTaskCounter() {
		if (taskList == null || taskList.size() == 0) {
			this.counter = 1;
		} else {
			int highestId = taskList.get(0).getTaskId();
			for (int i = 0; i < taskList.size(); i++) {
				if(taskList.get(i).getTaskId() > highestId) {
					highestId = taskList.get(i).getTaskId();
				}
			}
			this.counter = highestId + 1;
		}
	}
	/**
	 * Returns a specific task in the taskList
	 * @param id the ID of the specific task to return
	 * @return the task that needs to be returned
	 */
	public Task getTaskById(int id) {
		for (int i = 0; i < taskList.size(); i++) {
			if(taskList.get(i).getTaskId() == id) {
				return taskList.get(i);
			}
		}
		return null;
	}
	/**
	 * Deletes a specific task in the taskList
	 * @param id the ID of the specific task to delete
	 */
	public void deleteTaskById(int id) {
		for (int i = 0; i < taskList.size(); i++) {
			if(taskList.get(i).getTaskId() == id) {
				taskList.remove(i);
			}
		}
	}
	/**
	 * Executes a command on a specific task in the taskList
	 * @param id the ID of the specific task to execute command on
	 * @param c the command to execute
	 */
	public void executeCommand(int id, Command c) {
		for (int i = 0; i < taskList.size(); i++) {
			if(taskList.get(i).getTaskId() == id) {
				taskList.get(i).update(c);
			}
		}
	}
}
