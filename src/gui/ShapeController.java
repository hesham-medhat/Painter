package gui;

import java.awt.Point;

import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.Stroke;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

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

	protected void move(Point2D newCenter) {
		Shape newShape = null;
		try {
			newShape = (Shape) shape.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		((Stroke) newShape).setFxShape(((Stroke) shape).getFxShape());
		newShape.setPosition(new Point((int) newCenter.getX(), (int) newCenter.getY()));
		drawer.updateShape(shape, newShape, drawingPane);
		shape = newShape;
	}

	protected void copy(Point2D position) {
		Shape newShape = null;
		try {
			newShape = (Shape) shape.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		try {
			javafx.scene.shape.Shape newFxShape = ((Stroke) newShape).getFxShape().getClass().newInstance();
			((Stroke) newShape).setFxShape(newFxShape);
		} catch (InstantiationException | IllegalAccessException e) {
			System.err.println("Couldn't instantiate another fxShape");
			e.printStackTrace();
		}
		drawer.addShape(newShape, drawingPane);
	}

	/**
	 * Returns the RGB awt color equivalent of the fxColour.
	 * 
	 * @param fxColour
	 *            as paint.
	 * @return java.awt.Color equivalent.
	 */
	public static java.awt.Color getSwingColour(Color fxColour) {
		java.awt.Color color = new java.awt.Color((int) fxColour.getRed(), (int) fxColour.getGreen(),
				(int) fxColour.getBlue());
		return color;
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
