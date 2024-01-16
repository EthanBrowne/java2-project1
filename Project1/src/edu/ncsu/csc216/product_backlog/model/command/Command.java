/**
 * 
 */
package edu.ncsu.csc216.product_backlog.model.command;

/**
 * Command Class which take users actions and updates the state of task task
 * @author Ethan Browne
 */
public class Command {
	
	/**
	 * Contains the different states that a task can be
	 **/
	public enum CommandValue {
		/**
	     * Represents a command to move a task to the Backlog stage.
	     */
		BACKLOG,
		/**
	     * Represents a command to move a task to the Owned stage.
	     */
		CLAIM,
		/**
	     * Represents a command to move a task to the Processing stage.
	     */
		PROCESS,
		/**
	     * Represents a command to move a task to the Verifying stage.
	     */
		VERIFY,
		/**
	     * Represents a command to move a task to the Done stage.
	     */
		COMPLETE,
		/**
	     * Represents a command to move a task to the Rejected stage.
	     */
		REJECT }
	
	/**CommandValue representing what Stage Task is in*/
	private CommandValue c;
	/**String representing the owner*/
	private String owner;
	/**String representing the notes*/
	private String noteText;
	
	/**c == null
	 * Creates a Command object
	 *@param c CommandValue representing what Stage Task is in
	 *@param owner String representing the owner
	 *@param noteText String representing the notes
	 *@throws IllegalArgumentException if c is null
	 *@throws IllegalArgumentException if noteText is null or empty
	 *@throws IllegalArgumentException if owner is null or if owner is empty and c is CLAIM 
	 */
	public Command(CommandValue c, String owner, String noteText) {
		if (c == null || noteText == null || noteText.length() == 0) {
			throw new IllegalArgumentException("Invalid command.");
		} else if ((owner == null || owner.length() == 0) && c == CommandValue.CLAIM) {
			throw new IllegalArgumentException("Invalid command.");
		} else if (null != owner && c != CommandValue.CLAIM) {
			throw new IllegalArgumentException("Invalid command.");
		}
		this.c = c;
		this.owner = owner;
		this.noteText = noteText;
	}

	/**
	 * Returns the CommandValue representing what Stage Task is in
	 * @return the c
	 */
	public CommandValue getCommand() {
		return c;
	}

	/**
	 * Returns the String representing the owner
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * Returns the String representing the notes
	 * @return the noteText
	 */
	public String getNoteText() {
		return noteText;
	}

}
