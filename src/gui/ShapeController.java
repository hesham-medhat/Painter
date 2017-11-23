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
		Point oldPosition = shape.getPosition();
		Point newPosition = new Point((int) (clickDifference.getX() + oldPosition.getX()),
				(int) (clickDifference.getY() + oldPosition.getY()));
		Shape newShape = null;
		try {
			newShape = (Shape) shape.clone();
			newShape.setPosition(newPosition);
			((Stroke) newShape).setFxShape(this.getFx());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		ShapeController newsc = new ShapeController(drawingPane, drawer, newShape);
		newsc.getShape().setPosition(newPosition);
		newsc.getFx().relocate(newPosition.getX(), newPosition.getY());
		drawer.updateShape(drawingPane, this, newsc);
		return newsc;
	}

	protected ShapeController copy(Point2D newPosition) {
		Shape newShape = null;
		try {
			newShape = (Shape) shape.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		javafx.scene.shape.Shape newFx = ((Stroke) shape).makeFx();
		Point newPos = new Point((int) newPosition.getX(),
				(int)newPosition.getY());
		newShape.setPosition(newPos);
		((Stroke) newShape).setFxShape(newFx);
		ShapeController sc = new ShapeController(drawingPane, drawer, newShape);
		drawer.addShape(drawingPane, sc);
		newFx.relocate(newPosition.getX(), newPosition.getY());
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
