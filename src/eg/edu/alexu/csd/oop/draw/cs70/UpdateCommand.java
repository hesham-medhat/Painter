package eg.edu.alexu.csd.oop.draw.cs70;

import java.awt.Point;

import eg.edu.alexu.csd.oop.draw.ICommand;
import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.Stroke;
import gui.ColourAdapter;
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
	private ShapeController scNew;
	private ShapeController scOld;
	Point newPosition;
	Point oldPosition;

	/**
	 * The one and only constructor.
	 * 
	 * @param oldShape
	 *            to be updated.
	 * @param newShape
	 *            to be updated to.
	 */
	public UpdateCommand(Pane drawingPane, ShapeController scOld, ShapeController scNew) {
		super();
		this.scNew = scNew;
		this.scOld = scOld;
		this.oldShape = scOld.getShape();
		this.newShape = scNew.getShape();
		this.drawingPane = drawingPane;
		newPosition = newShape.getPosition();
		oldPosition = oldShape.getPosition();
	}

	@Override
	public void execute() {
		//drawingPane.getChildren().remove(scOld.getFx());
		//newShape.draw(drawingPane);
		ColourAdapter.setColours(newShape, ColourAdapter.getFxColour(oldShape.getFillColor()),
				ColourAdapter.getFxColour(oldShape.getColor()));
		((Stroke) newShape).getFxShape().relocate(newShape.getPosition().getX(), newShape.getPosition().getY());
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
		ColourAdapter.setColours(oldShape, ColourAdapter.getFxColour(newShape.getFillColor()), ColourAdapter.getFxColour(newShape.getColor()));
		((Stroke) oldShape).getFxShape().relocate(oldShape.getPosition().getX(), oldShape.getPosition().getY());
		oldShape.draw(drawingPane);
		scNew.enableSelect(false);
		scOld.enableSelect(true);

	}

}
