package eg.edu.alexu.csd.oop.draw;

public interface ICommand {

	public void execute();

	public void unexecute();

	public String getCommand();

	public Shape getReceiver(String reciever);

}
