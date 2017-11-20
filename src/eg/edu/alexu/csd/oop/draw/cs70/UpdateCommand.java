package eg.edu.alexu.csd.oop.draw.cs70;

import eg.edu.alexu.csd.oop.draw.ICommand;
import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.Stroke;
import gui.ShapeController;
import javafx.scene.layout.Pane;

/**
 * Class that updates a shape.
 * 
 * @author Marina
 *
 */
public class UpdateCommand implements ICommand {

	/**
	 * Type of command.
	 */
	public static final String COMMAND_TYPE = "update";
	/**
	 * Old shape.
	 */
	public static final String OLD_SHAPE = "old shape";
	/**
	 * New shape.
	 */
	public static final String NEW_SHAPE = "new shape";
	/**
	 * Reference of the old shape to be updated.
	 */
	private final Shape oldShape;
	/**
	 * Reference of the new shape that is updated with.
	 */
	private final Shape newShape;
	private Pane drawingPane;
	ShapeController scNew;
	ShapeController scOld;

	/**
	 * The one and only constructor.
	 * 
	 * @param oldShape
	 *            to be updated.
	 * @param newShape
	 *            to be updated to.
	 */
	public UpdateCommand(Pane drawingPane, ShapeController scNew, ShapeController scOld) {
		super();
		this.oldShape = scOld.getShape();
		this.newShape = scNew.getShape();
		this.drawingPane = drawingPane;

	}

	@Override
	public void execute() {
		newShape.draw(drawingPane);
		drawingPane.getChildren().remove(((Stroke) oldShape).getFxShape());
		scNew.enableSelect(true);
		scOld.enableSelect(false);

	}

	@Override
	public String getCommand() {
		return UpdateCommand.COMMAND_TYPE;
	}

	@Override
	public Shape getReceiver(final String receiver) {
		if (receiver.equals(UpdateCommand.NEW_SHAPE)) {
			return newShape;
		} else if (receiver.equals(UpdateCommand.OLD_SHAPE)) {
			return oldShape;
		}
		return null;
	}

	@Override
	public void unexecute() {
		drawingPane.getChildren().remove(((Stroke) newShape).getFxShape());
		oldShape.draw(drawingPane);
		scNew.enableSelect(false);
		scOld.enableSelect(true);

	}

}
