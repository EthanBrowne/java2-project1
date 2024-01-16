/**
 * 
 */
package edu.ncsu.csc216.product_backlog.model.product;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.product_backlog.model.command.Command;
import edu.ncsu.csc216.product_backlog.model.command.Command.CommandValue;
import edu.ncsu.csc216.product_backlog.model.task.Task;
import edu.ncsu.csc216.product_backlog.model.task.Task.Type;

/**
 * Tests ProductTest class
 */
class ProductTest {
	/**Constant that represents a valid product name*/
	private static final String PRODUCT_NAME = "Name";
	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.product.Product#Product(java.lang.String)}.
	 */
	@Test
	void testProduct() {
		Product testProduct = new Product(PRODUCT_NAME);
		assertEquals(PRODUCT_NAME, testProduct.getProductName());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.product.Product#addTask(edu.ncsu.csc216.product_backlog.model.task.Task)}.
	 */
	@Test
	void testAddTaskTask() {
		Product testProduct = new Product(PRODUCT_NAME);
		ArrayList<Task> l = new ArrayList<Task>();
		assertEquals(l, testProduct.getTasks());
		Task t = new Task(4, "name", Type.FEATURE, "me", "hello world");
		Task t1 = new Task(5, "name", Type.FEATURE, "me", "hello world");
		Task t2 = new Task(3, "name", Type.FEATURE, "me", "hello world");
		testProduct.addTask(t);
		testProduct.addTask(t1);
		testProduct.addTask(t2);
		l.add(t);
		l.add(t1);
		l.add(0, t2);
		assertEquals(l, testProduct.getTasks());
		
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> testProduct.addTask(t));
		assertEquals("Task cannot be added.", e1.getMessage());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.product.Product#addTask(java.lang.String, edu.ncsu.csc216.product_backlog.model.task.Task.Type, java.lang.String, java.lang.String)}.
	 */
	@Test
	void testAddTaskStringTypeStringString() {
		Product testProduct = new Product(PRODUCT_NAME);
		ArrayList<Task> l = new ArrayList<Task>();
		assertEquals(l, testProduct.getTasks());
		testProduct.addTask("name", Type.FEATURE, "me", "hello world");
		l = testProduct.getTasks();
		assertEquals(l.get(0).getTaskId(), 1);
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.product.Product#getProductName()}.
	 */
	@Test
	void testGetProductName() {
		Product testProduct = new Product(PRODUCT_NAME);
		assertEquals(PRODUCT_NAME, testProduct.getProductName());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.product.Product#setProductName(java.lang.String)}.
	 */
	@Test
	void testSetProductName() {
		Product testProduct = new Product(PRODUCT_NAME);
		assertEquals(PRODUCT_NAME, testProduct.getProductName());
		testProduct.setProductName("New Name");
		assertEquals("New Name", testProduct.getProductName());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.product.Product#getTasks()}.
	 */
	@Test
	void testGetTasks() {
		Product testProduct = new Product(PRODUCT_NAME);
		ArrayList<Task> l = new ArrayList<Task>();
		assertEquals(l, testProduct.getTasks());
		Task t = new Task(4, "name", Type.FEATURE, "me", "hello world");
		testProduct.addTask(t);
		l.add(t);
		assertEquals(l, testProduct.getTasks());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.product.Product#setCounter(int)}.
	 */
	@Test
	void testSetCounter() {
		Product testProduct = new Product(PRODUCT_NAME);
		testProduct.addTask("name", Type.FEATURE, "me", "hello world");
		assertEquals(1, testProduct.getTasks().get(0).getTaskId());
		testProduct.addTask("different name", Type.FEATURE, "me", "hello world");
		assertEquals(2, testProduct.getTasks().get(1).getTaskId());
		
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.product.Product#getTaskById(int)}.
	 */
	@Test
	void testGetTaskById() {
		Product testProduct = new Product(PRODUCT_NAME);
		testProduct.addTask("name", Type.FEATURE, "me", "hello world");
		assertEquals(1, testProduct.getTasks().get(0).getTaskId());
		assertEquals("name", testProduct.getTaskById(1).getTitle());
		assertNull(testProduct.getTaskById(42));
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.product.Product#deleteTaskById(int)}.
	 */
	@Test
	void testDeleteTaskById() {
		Product testProduct = new Product(PRODUCT_NAME);
		testProduct.addTask("name", Type.FEATURE, "me", "hello world");
		assertEquals(1, testProduct.getTasks().get(0).getTaskId());
		testProduct.deleteTaskById(1);
		assertEquals(0, testProduct.getTasks().size());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.product.Product#executeCommand(int, edu.ncsu.csc216.product_backlog.model.command.Command)}.
	 */
	@Test
	void testExecuteCommand() {
		Product testProduct = new Product(PRODUCT_NAME);
		testProduct.addTask("name", Type.FEATURE, "me", "hello world");
		assertEquals(1, testProduct.getTasks().get(0).getTaskId());
		Command c = new Command(CommandValue.CLAIM, "me", "hi");
		testProduct.executeCommand(1, c);
		assertEquals(testProduct.getTaskById(1).getStateName(), "Owned");
	}

}
