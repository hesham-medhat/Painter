package gui;

import java.awt.Point;

import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.Stroke;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

public class ShapeController {

	private Shape shape;

	/**
	 * @return the shape
	 */
	public Shape getShape() {
		return shape;
	}

	private boolean selected;
	private boolean selectEnable;
	private DrawingEngine drawer;
	protected Pane drawingPane;

	public ShapeController(Pane drawingPaneIn, DrawingEngine drawerIn, Shape shapeIn) {
		shape = shapeIn;
		drawer = drawerIn;
		drawingPane = drawingPaneIn;
		selectEnable = true;
	}

	protected ShapeController move(Point2D clickDifference) {

		Shape newShape = null;
		Shape oldShape = null;

		try {
			newShape = (Shape) shape.clone();
			oldShape = (Shape) shape.clone();

		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		javafx.scene.shape.Shape newFx = ((Stroke) shape).makeFx();
		javafx.scene.shape.Shape oldFx = ((Stroke) shape).makeFx();

		Point oldPosition = shape.getPosition();
		Point newPosition = new Point((int) (clickDifference.getX() + oldPosition.getX()),
				(int) (clickDifference.getY() + oldPosition.getY()));
		newFx.relocate(newPosition.getX(), newPosition.getY());
		oldFx.relocate(oldPosition.getX(), oldPosition.getY());

		newShape.setPosition(newPosition);
		oldShape.setPosition(oldPosition);

		((Stroke) newShape).setFxShape(newFx);
		((Stroke) oldShape).setFxShape(oldFx);

		ShapeController sc = new ShapeController(drawingPane, drawer, newShape);
		ShapeController sc1 = new ShapeController(drawingPane, drawer, oldShape);

		drawer.addShape(drawingPane, sc , true );
		drawer.removeShape(drawingPane, sc1 , true );
		drawingPane.getChildren().remove(this.getFx());
		return sc;
	}



	protected ShapeController copy(Point2D newPosition) {
		Shape newShape = null;
		try {
			newShape = (Shape) shape.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		javafx.scene.shape.Shape newFx = ((Stroke) shape).makeFx();
		Point oldPosition = shape.getPosition();
		Point newPos = new Point((int) newPosition.getX(),
				(int)newPosition.getY());
		newFx.relocate(newPosition.getX(), newPosition.getY());
		newShape.setPosition(newPos);
		((Stroke) newShape).setFxShape(newFx);
		ShapeController sc = new ShapeController(drawingPane, drawer, newShape);
		drawer.addShape(drawingPane, sc , false);
		return sc;
	}

	/**
	 * @return the selected
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * @param selectedSet
	 *            the selected to set
	 */
	public void setSelected(boolean selectedSet) {
		this.selected = selectedSet && selectEnable;
		if (selectedSet) {
			((Stroke) shape).getFxShape().setStrokeWidth(3.0);
		} else {
			((Stroke) shape).getFxShape().setStrokeWidth(1.0);
		}
	}

	/**
	 * Returns the fxShape of the shape that this controller controls.
	 * 
	 * @return fxShape GUI representation.
	 */
	public javafx.scene.shape.Shape getFx() {
		return ((Stroke) shape).getFxShape();
	}

	public void enableSelect(boolean enable) {
		selectEnable = enable;
	}

}
