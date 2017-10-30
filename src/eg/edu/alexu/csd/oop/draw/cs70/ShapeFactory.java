package eg.edu.alexu.csd.oop.draw.cs70;

import eg.edu.alexu.csd.oop.draw.Shape;

public class ShapeFactory {
	public Shape factory(String ShapeType) {
		Shape requiredShape = null;
		if (ShapeType.equals("circle")) {
			requiredShape = new Circle();
		} else if (ShapeType.equals("line segment")) {
			requiredShape = new LineSegment();
		} else if (ShapeType.equals("rectangle")) {
			requiredShape = new Rectangle(0, 0, 0, 0);
		} else if (ShapeType.equals("triangle")) {
			requiredShape = new Triangle();

		} else if (ShapeType.equals("square")) {
			requiredShape = new Square(0, 0, 0);
		}
		return requiredShape;
	}
}
