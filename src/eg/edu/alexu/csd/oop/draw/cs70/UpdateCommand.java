package eg.edu.alexu.csd.oop.draw.cs70;

import eg.edu.alexu.csd.oop.draw.ICommand;
import eg.edu.alexu.csd.oop.draw.Shape;

public class UpdateCommand implements ICommand {

	private Shape oldShape;
	private Shape newShape;
	public static final String COMMAND_TYPE = "update";
	public static final String OLD_SHAPE = "old shape";
	public static final String NEW_SHAPE = "new shape";

	public UpdateCommand(Shape oldShape, Shape newShape) {
		super();
		this.oldShape = oldShape;
		this.newShape = newShape;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

	@Override
	public void unexecute() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getCommand() {
		return COMMAND_TYPE;
	}

	@Override
	public Shape getReceiver(String receiver) {
		if (receiver.equals(NEW_SHAPE)) {
			return newShape;
		} else if (receiver.equals(OLD_SHAPE)) {
			return oldShape;
		}
		return null;
	}

}
