package eg.edu.alexu.csd.oop.draw.cs70;

import eg.edu.alexu.csd.oop.draw.ICommand;
import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.Stroke;
import javafx.scene.layout.Pane;

public class DrawCommand implements ICommand {

	private Shape receiver;
	private static final String COMMAND_TYPE = "draw";
	private Pane drawingPane;

	public DrawCommand(Shape receiverIn, Pane drawingPaneIn) {
		super();
		this.receiver = receiverIn;
		this.drawingPane = drawingPaneIn;
	}

	@Override
	public void execute() {
		receiver.draw(drawingPane);

	}

	@Override
	public void unexecute() {
		drawingPane.getChildren().remove(((Stroke) receiver).getFxShape());

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
