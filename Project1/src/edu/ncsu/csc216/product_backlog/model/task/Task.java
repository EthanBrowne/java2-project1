/**
 * 
 */
package edu.ncsu.csc216.product_backlog.model.task;

import java.util.ArrayList;

import edu.ncsu.csc216.product_backlog.model.command.Command;
import edu.ncsu.csc216.product_backlog.model.command.Command.CommandValue;

/**
 * Task class that contains all of the methods needed for controlling the task state.
 * Has constants representing the States of the class and the type of class.
 * This class contains a private interface and 6 private classes
 * @author Ethan Browne
 */
public class Task {
	/**
	 * Contains the different states that a task can be
	 */
	public enum Type {
		/**
		 * Represents a task type of Feature.
		 */
		FEATURE,
		/**
		 * Represents a task type of Bug.
		 */
		BUG,
		/**
		 * Represents a task type of Technical Work.
		 */
		TECHNICAL_WORK,
		/**
		 * Represents a task type of Knowledge Acquisition.
		 */
		KNOWLEDGE_ACQUISITION }
	//Constants for State Names
	/**A constant string for the backlog state’s name with the value of Backlog.*/
	public static final String BACKLOG_NAME = "Backlog";
	/**A constant string for the owned state’s name with the value of Owned.*/
	public static final String OWNED_NAME = "Owned";
	/**A constant string for the processing state’s name with the value of Processing.*/
	public static final String PROCESSING_NAME = "Processing";
	/**A constant string for the verifying state’s name with the value of Verifying.*/
	public static final String VERIFYING_NAME = "Verifying";
	/**A constant string for the done state’s name with the value of Done.*/
	public static final String DONE_NAME = "Done";
	/**A constant string for the rejected state’s name with the value of Rejected.*/
	public static final String REJECTED_NAME = "Rejected";
	
	//Constants for Type Long Names
	/**A constant string for the feature type with a value of Feature.*/
	public static final String FEATURE_NAME = "Feature";
	/**A constant string for the bug type with a value of Bug.*/
	public static final String BUG_NAME = "Bug";
	/**A constant string for the technical work type with a value of Technical Work.*/
	public static final String TECHNICAL_WORK_NAME = "Technical Work";
	/**A constant string for the knowledge acquisition type with a value of Knowledge Acquisition.*/
	public static final String KNOWLEDGE_ACQUISITION_NAME = "Knowledge Acquisition";
	
	//Constants for Type Short Names
	/**A constant string for the feature type with a value of F.*/
	public static final String T_FEATURE = "F";
	/**A constant string for the bug type with a value of B.*/
	public static final String T_BUG = "B";
	/**A constant string for the technical work type with a value of TW.*/
	public static final String T_TECHNICAL_WORK = "TW";
	/**A constant string for the knowledge acquisition type with a value of KA.*/
	public static final String T_KNOWLEDGE_ACQUISITION = "KA";
	
	//Constant placeholders
	/**A constant string for a Task that is not owned with a value of unowned. This is used to simplify file I/O.*/
	public static final String UNOWNED = "unowned";
	
	/** Id for an task.*/
	private int taskId;
	/**Current state for the task of type TaskState.*/
	private TaskState currentState;
	/**Type of task of type Type (i.e., an enumeration value).*/
	private Type type;
	/**Title of the task as provided by the user on creation.*/
	private String title;
	/**The name of the user who created the task as provided on creation.*/
	private String creator;
	/**The name of the user who owns and is responsible for resolving the task.*/
	private String owner;
	/** True if the task has been verified on transition VerifyingA.*/
	private boolean isVerified;
	/**An ArrayList of Strings that contain the task description and any notes added as the task progresses through the FSM.*/
	private ArrayList<String> notes;
	
	/**Final instance of the BacklogState inner class.*/
	private final TaskState backlogState = new BacklogState();
	/**Final instance of the OwnedState inner class.*/
	private final TaskState ownedState = new OwnedState();
	/**Final instance of the ProcessingState inner class.*/
	private final TaskState processingState = new ProcessingState();
	/**Final instance of the VerifyingState inner class.*/
	private final TaskState verifyingState = new VerifyingState();
	/**Final instance of the DoneState inner class.*/
	private final TaskState doneState = new DoneState();
	/**Final instance of the RejectedState inner class.*/
	private final TaskState rejectedState = new RejectedState();
	
	/**
	 * Creates a task
	 * @param id the id of the task
	 * @param title the title of the task
	 * @param type the type of task
	 * @param creator the creator of the task
	 * @param note the details of the task
	 * @throws IllegalArgumentException If the id is zero or less
	 * @throws IllegalArgumentException If any of parameters are empty strings or null
	 */
	public Task(int id, String title, Type type, String creator, String note) {
		setTaskId(id);
		setState(BACKLOG_NAME);
		setTitle(title);
		setType(type);
		setCreator(creator);
		setOwner(UNOWNED);
		this.notes = new ArrayList<String>();
		addNoteToList(note);
	}
	/**
	 * Creates a task
	 * @param id the id of the task
	 * @param state the state of the task
	 * @param title the title of the task
	 * @param type the type of task
	 * @param creator the creator of the task
	 * @param owner the owner of the task
	 * @param verified whether or not the task is verified
	 * @param notes the details of the task
	 * @throws IllegalArgumentException If the id is zero or less
	 * @throws IllegalArgumentException If any of parameters are empty strings or null
	 */
	public Task(int id, String state, String title, String type, String creator, String owner, String verified, ArrayList<String> notes) {
		setTaskId(id);
		setState(state);
		setTitle(title);
		setTypeFromString(type);
		setCreator(creator);
		setOwner(owner);
		setVerified(verified);
		setNotes(notes);
	}
	
	/**
	 * Returns the tasks id
	 * @return the taskId
	 */
	public int getTaskId() {
		return taskId;
	}


	/**
	 * Sets the tasks Id
	 * @param taskId the taskId to set
	 */
	private void setTaskId(int taskId) {
		if (taskId <= 0) {
			throw new IllegalArgumentException("Invalid task information.");
		}
		this.taskId = taskId;
	}
	/**
	 * Returns the state of the task
	 * @return the state
	 */
	public String getStateName() {
		return currentState.getStateName();
	}


	/**
	 * Sets the state of the task based on a string representing the state
	 * @param currentState the currentState to set
	 */
	private void setState(String currentState) {
		if (currentState == null || currentState.length() == 0) {
			throw new IllegalArgumentException("Invalid task information.");
		} else if (BACKLOG_NAME.equals(currentState)) {
			this.currentState = backlogState;
		} else if (OWNED_NAME.equals(currentState)) {
			this.currentState = ownedState;
		} else if (PROCESSING_NAME.equals(currentState)) {
			this.currentState = processingState;
		} else if (VERIFYING_NAME.equals(currentState)) {
			this.currentState = verifyingState;
		} else if (DONE_NAME.equals(currentState)) {
			this.currentState = doneState;
		} else if (REJECTED_NAME.equals(currentState)) {
			this.currentState = rejectedState;
		} else {
			throw new IllegalArgumentException("Invalid task information.");
		}
	}


	/**
	 * Returns the task type
	 * @return the type
	 */
	public Type getType() {
		return type;
	}


	/**
	 * Sets the type of the task
	 * @param type the type to set
	 */
	private void setType(Type type) {
		if (type == null) {
			throw new IllegalArgumentException("Invalid task information.");
		}
		this.type = type;
	}
	
	/**
	 * Sets the type of the task from a String representing the type
	 * @param type the type to set
	 */
	private void setTypeFromString(String type) {
		if (type == null || type.length() == 0) {
			throw new IllegalArgumentException("Invalid task information.");
		} else if (type.equals(T_FEATURE)) {
			this.type = Type.FEATURE;
		} else if (type.equals(T_BUG)) {
			this.type = Type.BUG;
		} else if (type.equals(T_TECHNICAL_WORK)) {
			this.type = Type.TECHNICAL_WORK;
		} else if (type.equals(T_KNOWLEDGE_ACQUISITION)) {
			this.type = Type.KNOWLEDGE_ACQUISITION;
		} else {
			throw new IllegalArgumentException("Invalid task information.");
		}
	}


	/**
	 * Returns the title of the task
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}


	/**
	 * Sets the title of the task
	 * @param title the title to set
	 */
	private void setTitle(String title) {
		if (title == null || title.length() == 0) {
			throw new IllegalArgumentException("Invalid task information.");
		}
		this.title = title;
	}


	/**
	 * Returns the creator of the task
	 * @return the creator
	 */
	public String getCreator() {
		return creator;
	}


	/**
	 * Sets the creator of the task
	 * @param creator the creator to set
	 */
	private void setCreator(String creator) {
		if (creator == null || creator.length() == 0) {
			throw new IllegalArgumentException("Invalid task information.");
		}
		this.creator = creator;
	}


	/**
	 * Returns the owner of the task
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}


	/**
	 * Sets the owner of the task
	 * @param owner the owner to set
	 */
	private void setOwner(String owner) {
		if (owner == null || owner.length() == 0) {
			throw new IllegalArgumentException("Invalid task information.");
		}
		this.owner = owner;
	}


	/**
	 * Returns whether or not the task is verified
	 * @return the isVerified
	 */
	public boolean isVerified() {
		return isVerified;
	}


	/**
	 * Sets whether or not the task is verified
	 * @param isVerified the isVerified to set
	 */
	private void setVerified(String isVerified) {
		if (isVerified == null || isVerified.length() == 0) {
			throw new IllegalArgumentException("Invalid task information.");
		} else if ("true".equals(isVerified)) {
			this.isVerified = true;
		} else if ("false".equals(isVerified)) {
			this.isVerified = false;
		} else {
			throw new IllegalArgumentException("Invalid task information.");
		}
	}


	/**
	 * Returns String ArrayList of the notes of the tasks
	 * @return the notes
	 */
	public ArrayList<String> getNotes() {
		return notes;
	}
	
	/**
	 * Returns a String that represents the note list
	 * @return the note
	 */
	public String getNotesList() {
		String string = "";
		for (int i = 0; i < notes.size(); i++) {
			string = string + "- " + notes.get(i) + "\n";
		}
		string = string.substring(0, string.length() - 1);
		return string;
	}


	/**
	 * Sets the notes of the task
	 * @param notes the notes to set
	 */
	private void setNotes(ArrayList<String> notes) {
		if (notes == null || notes.size() == 0) {
			throw new IllegalArgumentException("Invalid task information.");
		}
		this.notes = notes;
	}
	/**
	 * Returns the long name of type
	 * @return Long name of type
	 */
	public String getTypeLongName(){
		if (this.type == Type.BUG) {
			return BUG_NAME;
		} else if (this.type == Type.FEATURE) {
			return FEATURE_NAME;
		} else if (this.type == Type.KNOWLEDGE_ACQUISITION) {
			return KNOWLEDGE_ACQUISITION_NAME;
		} else {
			return TECHNICAL_WORK_NAME;
		}
	}
	/**
	 * Returns the short name of type
	 * @return Short name of type
	 */
	public String getTypeShortName(){
		if (this.type == Type.BUG) {
			return T_BUG;
		} else if (this.type == Type.FEATURE) {
			return T_FEATURE;
		} else if (this.type == Type.KNOWLEDGE_ACQUISITION) {
			return T_KNOWLEDGE_ACQUISITION;
		} else {
			return T_TECHNICAL_WORK;
		}
	}
	/**
	 * Adds the note of the task onto the list of notes. Prepends with task's Statename
	 * @param note the note to add to the list
	 * throws IllegalArgumentException if note is null or empty
	 */
	public void addNoteToList(String note) {
		if (note == null || note.length() == 0) {
			throw new IllegalArgumentException("Invalid task information.");
		}
		this.notes.add("[" + getStateName() + "] " + note);
	}
	/**
	 * Returns the string representation of the Task
	 * @return String representation of task
	 */
	public String toString() {
		return "* " + this.taskId + "," + getStateName() + "," + this.title + "," + getTypeShortName() + "," + this.creator + "," + this.owner + "," + this.isVerified + "\n" + getNotesList();
	}
	/**
	 * Drives the finite state machine by delegating to the current state’s updateState(Command) method
	 * @param c the command to perform
	 * @throws UnsupportedOperationException if transition between states is not supported
	 */
	public void update(Command c) {
		this.currentState.updateState(c);
		addNoteToList(c.getNoteText());
	}
	/**
	 * Returns a string array of the notes
	 * @return list of the notes
	 */
	public String[] getNotesArray() {
		String[] stringList = new String[this.notes.size()];
		for (int i = 0; i < this.notes.size(); i++) {
			stringList[i] = this.notes.get(i);
		}
		return stringList;
	}
	/**
	 * Interface for states in the Task State Pattern.  All 
	 * concrete task states must implement the TaskState interface.
	 * 
	 * @author Dr. Sarah Heckman (sarah_heckman@ncsu.edu) 
	 */
	private interface TaskState {
		
		/**
		 * Update the Task based on the given Command
		 * An UnsupportedOperationException is thrown if the Command is not a
		 * is not a valid action for the given state.  
		 * @param c Command describing the action that will update the Task
		 * state.
		 * @throws UnsupportedOperationException if the Command is not a valid action
		 * for the given state.
		 */
		void updateState(Command c);
		
		/**
		 * Returns the name of the current state as a String.
		 * @return the name of the current state as a String.
		 */
		String getStateName();
	
	}
	/**
	 * The owned state of the task
	 * Implements TaskState Interface
	 */
	private class BacklogState implements TaskState {
		/**
		 * Creates a BacklogState state. Private method
		 */
		private BacklogState() {
			
		}
		/**
		 * Updates the state
		 * @param c the command of what state to update it to
		 * @throws UnsupportedOperationException if there is an invalid transition between states
		 */
		@Override
		public void updateState(Command c) {
			if (c.getCommand() == CommandValue.CLAIM) {
				setState(OWNED_NAME);
				setOwner(c.getOwner());
			} else if (c.getCommand() == CommandValue.REJECT) {
				setState(REJECTED_NAME);
			} else {
				throw new UnsupportedOperationException("Invalid transition.");
			}
	
		}
		/**
		 * Returns the state's name
		 * @return the state's name
		 */
		@Override
		public String getStateName() {
			return BACKLOG_NAME;
		}

	}
	/**
	 * The owned state of the task
	 * Implements TaskState Interface
	 */
	private class OwnedState implements TaskState {
		/**
		 * Creates a OwnedState state. Private method
		 */
		private OwnedState() {
			
		}
		/**
		 * Updates the state
		 * @param c the command of what state to update it to
		 * @throws UnsupportedOperationException if there is an invalid transition between states
		 */
		@Override
		public void updateState(Command c) {
			if (c.getCommand() == CommandValue.BACKLOG) {
				setState(BACKLOG_NAME);
				setOwner(UNOWNED);
			} else if (c.getCommand() == CommandValue.REJECT) {
				setState(REJECTED_NAME);
				setOwner(UNOWNED);
			} else if (c.getCommand() == CommandValue.PROCESS) {
				setState(PROCESSING_NAME);
			} else {
				throw new UnsupportedOperationException("Invalid transition.");
			}
			
		}
		/**
		 * Returns the state's name
		 * @return the state's name
		 */
		@Override
		public String getStateName() {
			return OWNED_NAME;
		}

	}
	/**
	 * The verifying state of the task
	 * Implements TaskState Interface
	 */
	private class VerifyingState implements TaskState {
		/**
		 * Creates a VerifyingState state. Private method
		 */
		private VerifyingState() {
			
		}
		/**
		 * Updates the state
		 * @param c the command of what state to update it to
		 * @throws UnsupportedOperationException if there is an invalid transition between states
		 */
		@Override
		public void updateState(Command c) {
			if (c.getCommand() == CommandValue.PROCESS) {
				setState(PROCESSING_NAME);
			} else if (c.getCommand() == CommandValue.COMPLETE) {
				setState(DONE_NAME);
				setVerified("true");
			} else {
				throw new UnsupportedOperationException("Invalid transition.");
			}
		}
		/**
		 * Returns the state's name
		 * @return the state's name
		 */
		@Override
		public String getStateName() {
			return VERIFYING_NAME;
		}

	}
	/**
	 * The processing state of the task
	 * Implements TaskState Interface
	 * @author Ethan Browne
	 */
	private class ProcessingState implements TaskState {
		/**
		 * Creates a ProcessingState state. Private method
		 */
		private ProcessingState() {
			
		}
		/**
		 * Updates the state
		 * @param c the command of what state to update it to
		 * @throws UnsupportedOperationException if there is an invalid transition between states
		 */
		@Override
		public void updateState(Command c) {
			if (c.getCommand() == CommandValue.BACKLOG) {
				setState(BACKLOG_NAME);
				setOwner(UNOWNED);
			} else if (c.getCommand() == CommandValue.VERIFY && getType() != Type.KNOWLEDGE_ACQUISITION) {
				setState(VERIFYING_NAME);
			} else if (c.getCommand() == CommandValue.PROCESS) {
				setState(PROCESSING_NAME);
			} else if (c.getCommand() == CommandValue.COMPLETE && getType() == Type.KNOWLEDGE_ACQUISITION) {
				setState(DONE_NAME);
			} else {
				throw new UnsupportedOperationException("Invalid transition.");
			}
		}
		/**
		 * Returns the state's name
		 * @return the state's name
		 */
		@Override
		public String getStateName() {
			return PROCESSING_NAME;
		}

	}
	/**
	 * The done state of the task
	 * Implements TaskState Interface
	 */
	private class DoneState implements TaskState {
		/**
		 * Creates a DoneState state. Private method
		 */
		private DoneState() {
			
		}
		/**
		 * Updates the state
		 * @param c the command of what state to update it to
		 * @throws UnsupportedOperationException if there is an invalid transition between states
		 */
		@Override
		public void updateState(Command c) {
			if (c.getCommand() == CommandValue.PROCESS) {
				setState(PROCESSING_NAME);
				setVerified("false");
			} else if (c.getCommand() == CommandValue.BACKLOG) {
				setState(BACKLOG_NAME);
				setVerified("false");
				setOwner(OWNED_NAME);
			} else {
				throw new UnsupportedOperationException("Invalid transition.");
			}	
			
		}
		/**
		 * Returns the state's name
		 * @return the state's name
		 */
		@Override
		public String getStateName() {
			return DONE_NAME;
		}

	}
	/**
	 * The rejected state of the task
	 * Implements TaskState Interface
	 */
	private class RejectedState implements TaskState {
		/**
		 * Creates a RejectedState state. Private method
		 */
		private RejectedState() {
			
		}
		/**
		 * Updates the state
		 * @param c the command of what state to update it to
		 * @throws UnsupportedOperationException if there is an invalid transition between states
		 */
		@Override
		public void updateState(Command c) {
			if (c.getCommand() == CommandValue.BACKLOG) {
				setState(BACKLOG_NAME);
			} else {
				throw new UnsupportedOperationException("Invalid transition.");
			}
		}
		/**
		 * Returns the state's name
		 * @return the state's name
		 */
		@Override
		public String getStateName() {
			return REJECTED_NAME;
		}

	}
}
