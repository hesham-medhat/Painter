package eg.edu.alexu.csd.oop.draw.cs70;

import eg.edu.alexu.csd.oop.draw.Shape;

public interface ICommand {

	public void execute();

	public void unexecute();

	public String getCommand();

	public Shape getReceiver(String reciever);

}
