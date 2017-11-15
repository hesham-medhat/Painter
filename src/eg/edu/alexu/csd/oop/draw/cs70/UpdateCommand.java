package eg.edu.alexu.csd.oop.draw.cs70;

import eg.edu.alexu.csd.oop.draw.ICommand;
import eg.edu.alexu.csd.oop.draw.Shape;

/**
 * Class that updates a shape.
 * @author Marina
 *
 */
public class UpdateCommand implements ICommand {

	/**
	 * Type of command.
	 */
	public static final String COMMAND_TYPE = "update";
	/**
	 * Old shape.
	 */
	public static final String OLD_SHAPE = "old shape";
	/**
	 * New shape.
	 */
	public static final String NEW_SHAPE = "new shape";
	/**
	 * Reference of the old shape to be updated.
	 */
	private final Shape oldShape;
	/**
	 * Reference of the new shape that is updated with.
	 */
	private final Shape newShape;

	/**
	 * The one and only constructor.
	 * @param oldShape to be updated.
	 * @param newShape to be updated to.
	 */
	public UpdateCommand(final Shape oldShape, final Shape newShape) {
		super();
		this.oldShape = oldShape;
		this.newShape = newShape;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getCommand() {
		return UpdateCommand.COMMAND_TYPE;
	}

	@Override
	public Shape getReceiver(final String receiver) {
		if (receiver.equals(UpdateCommand.NEW_SHAPE)) {
			return newShape;
		} else if (receiver.equals(UpdateCommand.OLD_SHAPE)) {
			return oldShape;
		}
		return null;
	}

	@Override
	public void unexecute() {
		// TODO Auto-generated method stub

	}

}
