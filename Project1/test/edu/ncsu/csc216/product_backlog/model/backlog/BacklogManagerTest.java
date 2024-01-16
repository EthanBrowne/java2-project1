/**
 * 
 */
package edu.ncsu.csc216.product_backlog.model.backlog;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.product_backlog.model.command.Command;
import edu.ncsu.csc216.product_backlog.model.command.Command.CommandValue;
import edu.ncsu.csc216.product_backlog.model.io.ProductsReader;
import edu.ncsu.csc216.product_backlog.model.task.Task.Type;

/**
 * Tests BacklogManager Class
 */
class BacklogManagerTest {
	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.backlog.BacklogManager#getInstance()}.
	 */
	@Test
	void testGetInstance() {
		BacklogManager b = BacklogManager.getInstance();
		assertEquals(b, BacklogManager.getInstance());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.backlog.BacklogManager#resetManager()}.
	 */
	@Test
	void testResetManager() {
		BacklogManager b = BacklogManager.getInstance();
		b.addProduct("ExampleProduct");
		assertEquals(b.getProductList()[0], "ExampleProduct");
		b.resetManager();
		assertEquals(0, BacklogManager.getInstance().getProductList().length);
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.backlog.BacklogManager#addProduct(java.lang.String)}.
	 */
	@Test
	void testAddProduct() {
		BacklogManager b = BacklogManager.getInstance();
		b.addProduct("ExampleProduct");
		assertEquals(b.getProductList()[0], "ExampleProduct");
		b.resetManager();
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.backlog.BacklogManager#editProduct(java.lang.String)}.
	 */
	@Test
	void testEditProduct() {
		BacklogManager b = BacklogManager.getInstance();
		b.addProduct("ExampleProduct");
		b.editProduct("NewName");
		assertEquals(b.getProductName(), "NewName");
		b.resetManager();
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.backlog.BacklogManager#deleteProduct()}.
	 */
	@Test
	void testDeleteProduct() {
		BacklogManager b = BacklogManager.getInstance();
		b.addProduct("ExampleProduct");
		b.deleteProduct();
		assertNull(b.getProductName());
		b.resetManager();
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.backlog.BacklogManager#loadProduct(java.lang.String)}.
	 */
	@Test
	void testLoadProduct() {
		BacklogManager b = BacklogManager.getInstance();
		b.addProduct("ExampleProduct");
		b.addProduct("OtherProduct");
		assertEquals(b.getProductName(), "OtherProduct");
		b.loadProduct("ExampleProduct");
		assertEquals(b.getProductName(), "ExampleProduct");
		b.resetManager();
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.backlog.BacklogManager#getProductName()}.
	 */
	@Test
	void testGetProductName() {
		BacklogManager b = BacklogManager.getInstance();
		b.addProduct("OtherProduct");
		assertEquals(b.getProductName(), "OtherProduct");
		b.resetManager();
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.backlog.BacklogManager#getProductList()}.
	 */
	@Test
	void testGetProductList() {
		BacklogManager b = BacklogManager.getInstance();
		b.addProduct("ExampleProduct");
		b.addProduct("OtherProduct");
		assertEquals(b.getProductList()[0], "ExampleProduct");
		assertEquals(b.getProductList()[1], "OtherProduct");
		b.resetManager();
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.backlog.BacklogManager#clearProducts()}.
	 */
	@Test
	void testClearProducts() {
		BacklogManager b = BacklogManager.getInstance();
		b.addProduct("ExampleProduct");
		b.addProduct("OtherProduct");
		b.clearProducts();
		assertEquals(b.getProductName(), null);
		assertEquals(b.getProductList().length, 0);
		b.resetManager();
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.backlog.BacklogManager#loadFromFile(java.lang.String)}.
	 */
	@Test
	void testLoadFromFile() {
		BacklogManager b = BacklogManager.getInstance();
		b.resetManager();
		assertDoesNotThrow( () -> b.loadFromFile("test-files/exp_task_backlog.txt"), "Should not throw exception");
		assertEquals("Product", b.getProductList()[0]);
		assertEquals("A Product", b.getProductList()[1]);
		b.resetManager();
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.backlog.BacklogManager#saveToFile(java.lang.String)}.
	 */
	@Test
	void testSaveToFile() {
		BacklogManager b = BacklogManager.getInstance();
		b.resetManager();
		b.addProduct("ExampleProduct");
		b.addTaskToProduct("t1", Type.FEATURE, "Me", "Hello");
		b.addTaskToProduct("t2", Type.BUG, "You", "Hi");
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> b.saveToFile(null));
		assertEquals("Unable to save file.", e1.getMessage());
		b.saveToFile("testSaveToFile");
		assertEquals("ExampleProduct", ProductsReader.readProductsFile("testSaveToFile").get(0).getProductName());
		assertEquals(Type.FEATURE, ProductsReader.readProductsFile("testSaveToFile").get(0).getTaskById(1).getType());
		b.resetManager();
		
		b.addProduct("p1");
		assertEquals(0, b.getTasksAsArray().length);
		b.addTaskToProduct("t1", Type.KNOWLEDGE_ACQUISITION, "a", "b");
		b.addTaskToProduct("t2", Type.KNOWLEDGE_ACQUISITION, "a", "b");
		b.addTaskToProduct("t3", Type.KNOWLEDGE_ACQUISITION, "a", "b");
		assertEquals(3, b.getTasksAsArray().length);
		assertEquals("t1", b.getTaskById(1).getTitle());
		assertEquals("t2", b.getTaskById(2).getTitle());
		assertEquals("t3", b.getTaskById(3).getTitle());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.backlog.BacklogManager#getTasksAsArray()}.
	 */
	@Test
	void testGetTasksAsArray() {
		BacklogManager b = BacklogManager.getInstance();
		b.resetManager();
		b.addProduct("ExampleProduct");
		b.addTaskToProduct("t1", Type.FEATURE, "Me", "Hello");
		b.addTaskToProduct("t2", Type.BUG, "You", "Hi");
		assertEquals(b.getTasksAsArray()[0][0], "1");
		assertEquals(b.getTasksAsArray()[1][0], "2");
		assertEquals(b.getTasksAsArray()[0][1], "Backlog");
		assertEquals(b.getTasksAsArray()[1][1], "Backlog");
		assertEquals(b.getTasksAsArray()[0][2], "Feature");
		assertEquals(b.getTasksAsArray()[1][2], "Bug");
		assertEquals(b.getTasksAsArray()[0][3], "t1");
		assertEquals(b.getTasksAsArray()[1][3], "t2");
		b.resetManager();
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.backlog.BacklogManager#getTaskById(int)}.
	 */
	@Test
	void testGetTaskById() {
		BacklogManager b = BacklogManager.getInstance();
		b.addProduct("ExampleProduct");
		b.addTaskToProduct("bruh", Type.FEATURE, "Me", "Hello");
		assertEquals(b.getTaskById(1).getTitle(), "bruh");
		b.resetManager();
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.backlog.BacklogManager#executeCommand(int, edu.ncsu.csc216.product_backlog.model.command.Command)}.
	 */
	@Test
	void testExecuteCommand() {
		BacklogManager b = BacklogManager.getInstance();
		b.resetManager();
		b.addProduct("ExampleProduct");
		b.addTaskToProduct("bruh", Type.FEATURE, "Me", "Hello");
		Command c = new Command(CommandValue.CLAIM, "Owner", ":)");
		b.executeCommand(1, c);
		assertEquals(b.getTaskById(1).getStateName(), "Owned");
		b.resetManager();
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.backlog.BacklogManager#deleteTaskById(int)}.
	 */
	@Test
	void testDeleteTaskById() {
		BacklogManager b = BacklogManager.getInstance();
		b.addProduct("ExampleProduct");
		b.addTaskToProduct("bruh", Type.FEATURE, "Me", "Hello");
		assertEquals(b.getTaskById(1).getTitle(), "bruh");
		b.deleteTaskById(1);
		assertNull(b.getTaskById(1));
		b.resetManager();
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.backlog.BacklogManager#addTaskToProduct(java.lang.String, edu.ncsu.csc216.product_backlog.model.task.Task.Type, java.lang.String, java.lang.String)}.
	 */
	@Test
	void testAddTaskToProduct() {
		BacklogManager b = BacklogManager.getInstance();
		b.addProduct("ExampleProduct");
		b.addTaskToProduct("bruh", Type.FEATURE, "Me", "Hello");
		assertEquals(b.getTaskById(1).getTitle(), "bruh");
		b.resetManager();
	}

}
