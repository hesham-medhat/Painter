package eg.edu.alexu.csd.oop.draw;

/**
 * Interface for any command. This helps with undo/redo applying
 * command design pattern.
 * @author Marina
 *
 */
public interface ICommand {
	public boolean isupdate ();


	/**
	 * Executes the command.
	 */
	public void execute();

	/**
	 * Gets the command type as string.
	 * @return command type.
	 */
	public String getCommand();

	/**
	 * Gets the receiver that this command was controlling.
	 * @param receiver as String.
	 * @return receiver as Shape.
	 */
	public Shape getReceiver(String receiver);

	/**
	 * Negates the command's effect.
	 */
	public void unexecute();

}
