package eg.edu.alexu.csd.oop.draw.cs70;

import eg.edu.alexu.csd.oop.draw.ICommand;
import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.Stroke;
import gui.ShapeController;
import javafx.scene.layout.Pane;

public class DrawCommand implements ICommand {

	private Shape receiver;
	private static final String COMMAND_TYPE = "draw";
	private Pane drawingPane;
	private ShapeController sc;
	public boolean isupdate = false ;

	public DrawCommand(Pane drawingPaneIn, ShapeController sc) {
		super();
		this.receiver = sc.getShape();
		this.drawingPane = drawingPaneIn;
		this.sc = sc;
	}

	@Override
	public void execute() {
		receiver.draw(drawingPane);
		sc.enableSelect(true);

	}

	@Override
	public void unexecute() {
		drawingPane.getChildren().remove(((Stroke) receiver).getFxShape());
		sc.enableSelect(false);
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

	@Override
	public boolean isupdate() {
		// TODO Auto-generated method stub
		return isupdate;
	}

}
