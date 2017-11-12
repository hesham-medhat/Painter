package eg.edu.alexu.csd.oop.draw.cs70;

import java.util.List;

import eg.edu.alexu.csd.oop.draw.Shape;

public class Test {

	public static void main(String[] args) {
		Drawer test = new Drawer();
		List<Class<?extends Shape>> list = test.getSupportedShapes();
	}

}
