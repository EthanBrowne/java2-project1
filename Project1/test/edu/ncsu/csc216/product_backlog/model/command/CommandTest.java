/**
 * 
 */
package edu.ncsu.csc216.product_backlog.model.command;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.product_backlog.model.command.Command.CommandValue;

/**
 * Tests the command class
 * @author Ethan Browne
 */
class CommandTest {
	/**Constant that represents the value of the command*/
	private static final CommandValue C = CommandValue.CLAIM;
	/**String constant representing the owner*/
	private static final String OWNER = "jimmy";
	/**String constant representing the note*/
	private static final String NOTESTRING = "note";
	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.command.Command#Command(edu.ncsu.csc216.product_backlog.model.command.Command.CommandValue, java.lang.String, java.lang.String)}.
	 */
	@Test
	void testCommand() {
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> new Command(null, OWNER, NOTESTRING));
		assertEquals("Invalid command.", e1.getMessage());
		
		Exception e2 = assertThrows(IllegalArgumentException.class, () -> new Command(C, null, NOTESTRING));
		assertEquals("Invalid command.", e2.getMessage());
		
		Exception e3 = assertThrows(IllegalArgumentException.class, () -> new Command(C, OWNER, null));
		assertEquals("Invalid command.", e3.getMessage());
		
		Command command = new Command(C, OWNER, NOTESTRING);
		assertEquals(C, command.getCommand());
		assertEquals(OWNER, command.getOwner());
		assertEquals(NOTESTRING, command.getNoteText());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.command.Command#getC()}.
	 */
	@Test
	void testGetC() {
		Command command = new Command(C, OWNER, NOTESTRING);
		assertEquals(C, command.getCommand());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.command.Command#getOwner()}.
	 */
	@Test
	void testGetOwner() {
		Command command = new Command(C, OWNER, NOTESTRING);
		assertEquals(OWNER, command.getOwner());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.command.Command#getNoteText()}.
	 */
	@Test
	void testGetNoteText() {
		Command command = new Command(C, OWNER, NOTESTRING);
		assertEquals(NOTESTRING, command.getNoteText());
	}

}
