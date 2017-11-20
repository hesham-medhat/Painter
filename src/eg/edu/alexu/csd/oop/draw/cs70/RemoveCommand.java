package eg.edu.alexu.csd.oop.draw.cs70;

import eg.edu.alexu.csd.oop.draw.ICommand;
import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.Stroke;
import gui.ShapeController;
import javafx.scene.layout.Pane;

public class RemoveCommand implements ICommand {
	/**
	 * Shape that receives the command.
	 */
	private Shape receiver;
	private Pane drawingPane;
	private ShapeController sc;

	/**
	 * Command's type.
	 */
	public static final String COMMAND_TYPE = "remove";

	public RemoveCommand(Pane drawingPaneIn, ShapeController sc) {
		super();
		this.receiver = sc.getShape();
		this.drawingPane = drawingPaneIn;
		this.sc = sc;
	}

	@Override
	public void execute() {
		drawingPane.getChildren().remove(((Stroke) receiver).getFxShape());
		sc.enableSelect(false);

	}

	@Override
	public void unexecute() {
		receiver.draw(drawingPane);
		sc.enableSelect(true);

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
