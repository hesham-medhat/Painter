package eg.edu.alexu.csd.oop.draw.cs70;

public interface ICommand {

	public void execute();

	public void unexecute();

	public String getCommand();

	public Stroke getReciever();

}
