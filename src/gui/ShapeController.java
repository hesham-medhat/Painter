package gui;

import java.awt.Point;

import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.Stroke;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

public class ShapeController {

	protected Shape shape;
	private boolean selected;
	private DrawingEngine drawer;
	protected Pane drawingPane;

	public ShapeController(Pane drawingPaneIn, DrawingEngine drawerIn, Shape shapeIn) {
		shape = shapeIn;
		drawer = drawerIn;
		drawingPane = drawingPaneIn;
	}

	protected void move(Point2D clickDifference) {
		Shape newShape = null;
		try {
			newShape = (Shape) shape.clone();
			((Stroke) newShape).setFxShape(this.getFx());
			Point oldPosition = shape.getPosition();
			Point newPosition = new Point((int) (clickDifference.getX() + oldPosition.getX()), (int) (clickDifference.getY() + oldPosition.getY()));
			newShape.setPosition(newPosition);
			this.getFx().relocate(newPosition.getX(), newPosition.getY());
			drawer.updateShape(shape, newShape, drawingPane);
			shape = newShape;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	protected ShapeController copy(Point2D clickDifference) {
		Shape newShape = null;
		try {
			newShape = (Shape) shape.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		javafx.scene.shape.Shape newFx = ((Stroke) shape).makeFx();
		Point oldPosition = shape.getPosition();
		Point newPosition = new Point((int) (clickDifference.getX() + oldPosition.getX()), (int) (clickDifference.getY() + oldPosition.getY()));
		newFx.relocate(newPosition.getX(), newPosition.getY());
		newShape.setPosition(newPosition);
		((Stroke) newShape).setFxShape(newFx);
		drawer.addShape(newShape, drawingPane);
		return new ShapeController(drawingPane, drawer, newShape);
	}

	/**
	 * @return the selected
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * @param selectedSet the selected to set
	 */
	public void setSelected(boolean selectedSet) {
		this.selected = selectedSet;
		if (selectedSet) {
			((Stroke) shape).getFxShape().setStrokeWidth(3.0);
		} else {
			((Stroke) shape).getFxShape().setStrokeWidth(1.0);
		}
	}

	/**
	 * Returns the fxShape of the shape that this controller controls.
	 * @return fxShape GUI representation.
	 */
	public javafx.scene.shape.Shape getFx() {
		return ((Stroke) shape).getFxShape();
	}

}
