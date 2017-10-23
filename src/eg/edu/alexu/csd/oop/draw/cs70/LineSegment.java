package eg.edu.alexu.csd.oop.draw.cs70;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.Shape;
import javafx.geometry.Point2D;

/**
 * @author H
 *
 */
public class LineSegment implements Shape, Cloneable {

	Point center;
	Color color;
	Color fill;

	@Override
	public void setPosition(Point position) {
		if (position != null) {
			center = new Point((int) position.getX(),
					(int) position.getY());
		}
	}

	@Override
	public Point getPosition() {
		return center;
	}

	@Override
	public void setProperties(Map<String, Double> properties) {
		// TODO Auto-generated method stub

	}

	@Override
	public Map<String, Double> getProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setColor(Color color) {
		if (color != null) {
			this.color = color;
		}

	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public void setFillColor(Color color) {
		if (color != null) {
			this.fill = color;
		}

	}

	@Override
	public Color getFillColor() {
		return fill;
	}

	@Override
	public void draw(Graphics canvas) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return null;
	}

}
