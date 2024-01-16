/**
 * 
 */
package edu.ncsu.csc216.product_backlog.model.io;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests ProductsReader Class
 */
class ProductsReaderTest {

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.io.ProductsReader#ProductsReader()}.
	 */
	@Test
	void testProductsReader() {
		ProductsReader p = new ProductsReader();
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> p.readProductsFile(null));
		assertEquals("Unable to load file.", e1.getMessage());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.io.ProductsReader#readProductsFile(java.lang.String)}.
	 */
	@Test
	void testReadProductsFile() {
		ProductsReader p = new ProductsReader();
		assertDoesNotThrow( () -> p.readProductsFile("test-files/exp_task_backlog.txt"), "Should not throw exception");
		assertEquals("Product", p.readProductsFile("test-files/exp_task_backlog.txt").get(0).getProductName());
		assertEquals("title1", p.readProductsFile("test-files/exp_task_backlog.txt").get(0).getTasks().get(0).getTitle());
		assertEquals("A Product", p.readProductsFile("test-files/exp_task_backlog.txt").get(1).getProductName());
		assertEquals("title02", p.readProductsFile("test-files/exp_task_backlog.txt").get(1).getTaskById(2).getTitle());
		
		assertDoesNotThrow( () -> p.readProductsFile("test-files/tasks1.txt"), "Should not throw exception");
		assertEquals("Shopping Cart Simulation", p.readProductsFile("test-files/tasks1.txt").get(0).getProductName());
		assertEquals("Express Carts", p.readProductsFile("test-files/tasks1.txt").get(0).getTasks().get(0).getTitle());
		assertEquals(6, p.readProductsFile("test-files/tasks1.txt").get(0).getTasks().size());
		assertEquals("Add Course", p.readProductsFile("test-files/tasks1.txt").get(1).getTaskById(6).getTitle());
		
		assertDoesNotThrow( () -> p.readProductsFile("test-files/tasks2.txt"), "Should not throw exception");
	}

}
