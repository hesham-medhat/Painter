package eg.edu.alexu.csd.oop.draw.cs70;

import eg.edu.alexu.csd.oop.draw.ICommand;
import eg.edu.alexu.csd.oop.draw.Shape;

public class RemoveCommand implements ICommand {
	/**
	 * Shape that receives the command.
	 */
	private Shape receiver;
	/**
	 * Command's type.
	 */
	public static final String COMMAND_TYPE = "remove";

	public RemoveCommand(Shape receiver) {
		super();
		this.receiver = receiver;
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
	public Shape getReceiver(String receiver) {
		return this.receiver;
	}

	@Override
	public String getCommand() {
		return COMMAND_TYPE;
	}

	

}
