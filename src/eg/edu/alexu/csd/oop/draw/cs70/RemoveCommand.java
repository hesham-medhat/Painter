package eg.edu.alexu.csd.oop.draw.cs70;

import eg.edu.alexu.csd.oop.draw.Shape;

public class RemoveCommand implements ICommand {
	private Shape reciever;
	public static final String COMMAND_TYPE = "remove";

	public RemoveCommand(Shape reciever) {
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

	/**
	 * @return the reciever
	 */
	public Shape getReciever(String reciever) {
		return this.reciever;
	}

	@Override
	public String getCommand() {
		return COMMAND_TYPE;
	}

	

}
