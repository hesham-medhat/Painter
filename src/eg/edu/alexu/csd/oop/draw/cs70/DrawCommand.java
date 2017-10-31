package eg.edu.alexu.csd.oop.draw.cs70;

import eg.edu.alexu.csd.oop.draw.ICommand;
import eg.edu.alexu.csd.oop.draw.Shape;

public class DrawCommand implements ICommand {

	private Shape receiver;
	public static final String COMMAND_TYPE = "draw";

	public DrawCommand(Shape receiver) {
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
	public String getCommand() {
		return COMMAND_TYPE;
	}

	/**
	 * @return the receiver
	 */
	public Shape getReceiver(String receiver) {
		return this.receiver;
	}

	

}
