package eg.edu.alexu.csd.oop.draw.cs70;

public class DrawCommand implements ICommand {

	private Stroke reciever;
	public static final String COMMAND_TYPE = "draw";

	public DrawCommand(Stroke reciever) {
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
	public Stroke getReciever() {
		return reciever;
	}

}
