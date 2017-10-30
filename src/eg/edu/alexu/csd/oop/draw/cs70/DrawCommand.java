package eg.edu.alexu.csd.oop.draw.cs70;

import eg.edu.alexu.csd.oop.draw.Shape;

public class DrawCommand implements ICommand {

	private Shape reciever;
	public static final String COMMAND_TYPE = "draw";

	public DrawCommand(Shape reciever) {
		super();
		this.reciever = reciever;
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
	 * @return the reciever
	 */
	public Shape getReciever(String reciever) {
		return this.reciever;
	}

	

}
