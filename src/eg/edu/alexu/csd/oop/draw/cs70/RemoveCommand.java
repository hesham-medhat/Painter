package eg.edu.alexu.csd.oop.draw.cs70;


public class RemoveCommand implements ICommand {
	private Stroke reciever;
	public static final String COMMAND_TYPE = "remove";

	public RemoveCommand(Stroke reciever) {
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
	public Stroke getReciever() {
		return reciever;
	}

	@Override
	public String getCommand() {
		return COMMAND_TYPE;
	}

}
