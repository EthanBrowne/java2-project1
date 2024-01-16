package edu.ncsu.csc216.product_backlog.model.task;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.product_backlog.model.command.Command;
import edu.ncsu.csc216.product_backlog.model.command.Command.CommandValue;
import edu.ncsu.csc216.product_backlog.model.task.Task.Type;

class TaskTest {
	// CONSTANTS FOR SMALL CONSTRUCTOR
	/**A constant that represents a valid Id of the task.*/
	private static final int ID = 8;
	/**A constant that represents a valid title of the task.*/
	private static final String TITLE = "Awesome Task";
	/**A constant that represents a valid type of task.*/
	private static final Type TYPE = Type.BUG;
	/**A constant that represents a valid creator of the task.*/
	private static final String CREATOR = "Jimbob";
	/**A constant that represents a valid note of the task.*/
	private static final String NOTE = "Hello";
	
	// CONSTANTS FOR BIG CONSTRUCTOR
	/**A constant that represents a valid State of the task.*/
	private static final String STATE = "Owned";
	/**A constant that represents a valid string type of task.*/
	private static final String STRINGTYPE = "KA";
	/**A constant that represents a valid Owner of the task.*/
	private static final String OWNER = "Santa";
	/**A constant that represents a valid verify value of the task.*/
	private static final String VERIFIED = "false";
	/**A constant that represents valid notes of the task.*/
	private static final ArrayList<String> NOTES = new ArrayList<String>(Arrays.asList("Hello", "Goodbye"));
	
	@Test
	void testTaskConstructorSmall() {
		Task testTask = new Task(ID, TITLE, TYPE, CREATOR, NOTE);
		assertEquals(ID, testTask.getTaskId());
		assertEquals(TITLE, testTask.getTitle());
		assertEquals(TYPE, testTask.getType());
		assertEquals(CREATOR, testTask.getCreator());
		ArrayList<String> a = new ArrayList<String>(Arrays.asList("[Backlog] " + NOTE));
		assertEquals(a, testTask.getNotes());
	}

	@Test
	void testTaskConstructorLarge() {
		Task testTask = new Task(ID, STATE, TITLE, STRINGTYPE, CREATOR, OWNER, VERIFIED, NOTES);
		assertEquals(ID, testTask.getTaskId());
		assertEquals(STATE, testTask.getStateName());
		assertEquals(TITLE, testTask.getTitle());
		assertEquals(Type.KNOWLEDGE_ACQUISITION, testTask.getType());
		assertEquals(CREATOR, testTask.getCreator());
		assertEquals(OWNER, testTask.getOwner());
		assertFalse(testTask.isVerified());
		assertEquals(NOTES, testTask.getNotes());
	}

	@Test
	void testGetTaskId() {
		Task testTask = new Task(ID, TITLE, TYPE, CREATOR, NOTE);
		assertEquals(ID, testTask.getTaskId());
		
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> new Task(0, TITLE, TYPE, CREATOR, NOTE));
		assertEquals("Invalid task information.", e1.getMessage());
	}

	@Test
	void testGetStateName() {
		Task testTask = new Task(ID, TITLE, TYPE, CREATOR, NOTE);
		assertEquals("Backlog", testTask.getStateName());
		
		Task testTask1 = new Task(ID, STATE, TITLE, STRINGTYPE, CREATOR, OWNER, VERIFIED, NOTES);
		assertEquals(STATE, testTask1.getStateName());
		
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> new Task(ID, "", TITLE, STRINGTYPE, CREATOR, OWNER, VERIFIED, NOTES));
		assertEquals("Invalid task information.", e1.getMessage());
	}

	@Test
	void testGetType() {
		Task testTask = new Task(ID, TITLE, TYPE, CREATOR, NOTE);
		assertEquals(TYPE, testTask.getType());
		
		Task testTask1 = new Task(ID, STATE, TITLE, STRINGTYPE, CREATOR, OWNER, VERIFIED, NOTES);
		assertEquals(Type.KNOWLEDGE_ACQUISITION, testTask1.getType());
		
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> new Task(ID, STATE, TITLE, "", CREATOR, OWNER, VERIFIED, NOTES));
		assertEquals("Invalid task information.", e1.getMessage());
	}

	@Test
	void testGetTitle() {
		Task testTask = new Task(ID, TITLE, TYPE, CREATOR, NOTE);
		assertEquals(TITLE, testTask.getTitle());
		
		Task testTask1 = new Task(ID, STATE, TITLE, STRINGTYPE, CREATOR, OWNER, VERIFIED, NOTES);
		assertEquals(TITLE, testTask1.getTitle());
		
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> new Task(ID, STATE, "", STRINGTYPE, CREATOR, OWNER, VERIFIED, NOTES));
		assertEquals("Invalid task information.", e1.getMessage());
	}

	@Test
	void testGetCreator() {
		Task testTask = new Task(ID, TITLE, TYPE, CREATOR, NOTE);
		assertEquals(CREATOR, testTask.getCreator());
		
		Task testTask1 = new Task(ID, STATE, TITLE, STRINGTYPE, CREATOR, OWNER, VERIFIED, NOTES);
		assertEquals(CREATOR, testTask1.getCreator());
		
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> new Task(ID, STATE, TITLE, STRINGTYPE, null, OWNER, VERIFIED, NOTES));
		assertEquals("Invalid task information.", e1.getMessage());
	}

	@Test
	void testGetOwner() {
		Task testTask = new Task(ID, TITLE, TYPE, CREATOR, NOTE);
		assertEquals("unowned", testTask.getOwner());
		
		Task testTask1 = new Task(ID, STATE, TITLE, STRINGTYPE, CREATOR, OWNER, VERIFIED, NOTES);
		assertEquals(OWNER, testTask1.getOwner());
		
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> new Task(ID, STATE, TITLE, STRINGTYPE, CREATOR, null, VERIFIED, NOTES));
		assertEquals("Invalid task information.", e1.getMessage());
	}

	@Test
	void testIsVerified() {
		Task testTask = new Task(ID, TITLE, TYPE, CREATOR, NOTE);
		assertFalse(testTask.isVerified());
		
		Task testTask1 = new Task(ID, STATE, TITLE, STRINGTYPE, CREATOR, OWNER, VERIFIED, NOTES);
		assertFalse(testTask1.isVerified());
		
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> new Task(ID, STATE, TITLE, STRINGTYPE, CREATOR, OWNER, "hi", NOTES));
		assertEquals("Invalid task information.", e1.getMessage());
	}

	@Test
	void testGetNotes() {
		Task testTask = new Task(ID, TITLE, TYPE, CREATOR, NOTE);
		ArrayList<String> a = new ArrayList<String>(Arrays.asList("[Backlog] " + NOTE));
		assertEquals(a, testTask.getNotes());
		
		Task testTask1 = new Task(ID, STATE, TITLE, STRINGTYPE, CREATOR, OWNER, VERIFIED, NOTES);
		assertEquals(NOTES, testTask1.getNotes());
		
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> new Task(ID, STATE, TITLE, STRINGTYPE, CREATOR, OWNER, VERIFIED, null));
		assertEquals("Invalid task information.", e1.getMessage());
	}

	@Test
	void testGetNotesList() {
		Task testTask = new Task(ID, TITLE, TYPE, CREATOR, NOTE);
		assertEquals("- [Backlog] " + NOTE, testTask.getNotesList());
		
		String string = "";
		for (int i = 0; i < NOTES.size(); i++) {
			string = string + "- " + NOTES.get(i) + "\n";
		}
		string = string.substring(0, string.length() - 1);
		Task testTask1 = new Task(ID, STATE, TITLE, STRINGTYPE, CREATOR, OWNER, VERIFIED, NOTES);
		assertEquals(string, testTask1.getNotesList());
		
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> new Task(ID, STATE, TITLE, STRINGTYPE, CREATOR, OWNER, VERIFIED, null));
		assertEquals("Invalid task information.", e1.getMessage());
	}

	@Test
	void testGetTypeLongName() {
		Task testTask = new Task(ID, TITLE, TYPE, CREATOR, NOTE);
		assertEquals("Bug", testTask.getTypeLongName());
		
		Task testTask1 = new Task(ID, STATE, TITLE, STRINGTYPE, CREATOR, OWNER, VERIFIED, NOTES);
		assertEquals("Knowledge Acquisition", testTask1.getTypeLongName());
	}

	@Test
	void testGetTypeShortName() {
		Task testTask = new Task(ID, TITLE, TYPE, CREATOR, NOTE);
		assertEquals("B", testTask.getTypeShortName());
		
		Task testTask1 = new Task(ID, STATE, TITLE, STRINGTYPE, CREATOR, OWNER, VERIFIED, NOTES);
		assertEquals("KA", testTask1.getTypeShortName());
	}

	@Test
	void testAddNoteToList() {
		Task testTask = new Task(ID, STATE, TITLE, STRINGTYPE, CREATOR, OWNER, VERIFIED, NOTES);
		assertEquals(NOTES, testTask.getNotes());
		
		testTask.addNoteToList(NOTE);
		ArrayList<String> s = NOTES;
		s.add(NOTE);
		assertEquals(s, testTask.getNotes());
	}

	@Test
	void testToString() {
		Task testTask = new Task(ID, STATE, TITLE, STRINGTYPE, CREATOR, OWNER, VERIFIED, NOTES);
		assertEquals("* " + ID + "," + STATE + "," + TITLE + "," + STRINGTYPE + "," + CREATOR + "," + OWNER + "," + VERIFIED + "\n" + testTask.getNotesList(), testTask.toString());
	}

	@Test
	void testUpdate() {
		Task testTask = new Task(ID, STATE, TITLE, "F", CREATOR, OWNER, VERIFIED, NOTES);
		Command c = new Command(CommandValue.REJECT, null, "Reject");
		Command c1 = new Command(CommandValue.BACKLOG, null, "Backlog");
		Command c2 = new Command(CommandValue.CLAIM, OWNER, "Owned");
		Command c3 = new Command(CommandValue.PROCESS, null, "proccessing");
		Command c4 = new Command(CommandValue.VERIFY, null, "verifying");
		Command c5 = new Command(CommandValue.COMPLETE, null, "done");
		
		testTask.update(c);
		assertEquals(testTask.getStateName(), "Rejected");
		assertEquals(testTask.getOwner(), "unowned");	
	
		testTask.update(c1);
		assertEquals("Backlog", testTask.getStateName());
		assertEquals(testTask.getOwner(), "unowned");
		
		Exception e1 = assertThrows(UnsupportedOperationException.class, () -> testTask.update(c1));
		assertEquals("Invalid transition.", e1.getMessage());
		
		testTask.update(c2);
		assertEquals("Owned", testTask.getStateName());
		assertEquals(testTask.getOwner(), OWNER);
		
		Exception e5 = assertThrows(UnsupportedOperationException.class, () -> testTask.update(c4));
		assertEquals("Invalid transition.", e5.getMessage());
		
		testTask.update(c3);
		assertEquals("Processing", testTask.getStateName());
		assertEquals(testTask.getOwner(), OWNER);
		
		testTask.update(c4);
		assertEquals("Verifying", testTask.getStateName());
		assertEquals(testTask.getOwner(), OWNER);
		assertFalse(testTask.isVerified());
		
		testTask.update(c5);
		assertEquals("Done", testTask.getStateName());
		assertEquals(testTask.getOwner(), OWNER);
		assertTrue(testTask.isVerified());
		
		Exception e2 = assertThrows(UnsupportedOperationException.class, () -> testTask.update(c5));
		assertEquals("Invalid transition.", e2.getMessage());
		
		testTask.update(c1);
		testTask.update(c2);
		
		testTask.update(c3);
		assertEquals("Processing", testTask.getStateName());
		assertEquals(testTask.getOwner(), OWNER);
		
		testTask.update(c3);
		assertEquals("Processing", testTask.getStateName());
		assertEquals(testTask.getOwner(), OWNER);
		
		Exception e4 = assertThrows(UnsupportedOperationException.class, () -> testTask.update(c2));
		assertEquals("Invalid transition.", e4.getMessage());
		
		testTask.update(c1);
		assertEquals("Backlog", testTask.getStateName());
		assertEquals(testTask.getOwner(), "unowned");
		
		testTask.update(c);
		assertEquals(testTask.getStateName(), "Rejected");
		assertEquals(testTask.getOwner(), "unowned");
		
		Exception e3 = assertThrows(UnsupportedOperationException.class, () -> testTask.update(c));
		assertEquals("Invalid transition.", e3.getMessage());
	}

	@Test
	void testGetNotesArray() {
		Task testTask = new Task(ID, STATE, TITLE, STRINGTYPE, CREATOR, OWNER, VERIFIED, NOTES);
		assertEquals("Hello", testTask.getNotesArray()[0]);
		assertEquals("Goodbye", testTask.getNotesArray()[1]);
		assertEquals(NOTES.size(), testTask.getNotesArray().length);
	}

}
