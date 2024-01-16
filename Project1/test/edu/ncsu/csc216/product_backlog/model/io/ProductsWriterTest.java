/**
 * 
 */
package edu.ncsu.csc216.product_backlog.model.io;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.product_backlog.model.product.Product;
import edu.ncsu.csc216.product_backlog.model.task.Task.Type;

/**
 * Tests ProductsWriter Class
 */
class ProductsWriterTest {

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.io.ProductsWriter#ProductsWriter()}.
	 */
	@Test
	void testProductsWriter() {
		ArrayList<Product> array = new ArrayList<Product>();
		array.add(new Product("a"));
		array.add(new Product("b"));
		ProductsWriter w = new ProductsWriter();
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> w.writeProductsToFile(null, array));
		assertEquals("Unable to save file.", e1.getMessage());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.io.ProductsWriter#writeProductsToFile(java.lang.String, java.util.ArrayList)}.
	 */
	@Test
	void testWriteProductsToFile() {
		ArrayList<Product> array = new ArrayList<Product>();
		Product a = new Product("a");
		a.addTask("taskA", Type.BUG, "Me", "TaskAnote");
		array.add(a);
		Product b = new Product("b");
		b.addTask("taskB", Type.KNOWLEDGE_ACQUISITION, "Someone", "TaskBnote");
		array.add(b);
		ProductsWriter w = new ProductsWriter();
		assertDoesNotThrow( () -> w.writeProductsToFile("testWriteProductsToFile", array), "Should not throw exception");
		assertEquals("a", ProductsReader.readProductsFile("testWriteProductsToFile").get(0).getProductName());
		assertEquals("Me", ProductsReader.readProductsFile("testWriteProductsToFile").get(0).getTaskById(1).getCreator());
		
	}

}
