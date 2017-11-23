package gui;

import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.Stroke;
import javafx.scene.paint.Color;

/**
 * This adapter facilitates dealing with different colour types.
 * All methods are static.
 * @author H
 *
 */
public abstract class ColourAdapter {

	/**
	 * Sets the colours of the shape.
	 * @param shape input shape.
	 * @param fc Fill colour as Paint.
	 * @param bc Border stroke colour as Paint.
	 */
	public static void setColours(Shape shape, Color fc, Color bc) {
		shape.setColor(getSwingColour(bc));
		shape.setFillColor(getSwingColour(fc));
		javafx.scene.shape.Shape fxShape = ((Stroke) shape).getFxShape();
		fxShape.setFill(fc);
		fxShape.setStroke(bc);
	}

	/**
	 * Returns the RGB awt color equivalent of the fxColour.
	 * 
	 * @param fxColour
	 *            as paint.
	 * @return java.awt.Color equivalent.
	 */
	public static java.awt.Color getSwingColour(Color fxColour) {
		java.awt.Color color = new java.awt.Color((int) (fxColour.getRed()*255), (int) (fxColour.getGreen()*255),
				(int) (fxColour.getBlue()*255));
		return color;
	}

	/**
	 * Returns the javafx color equivalent of the fxColour.
	 * 
	 * @param swingColour
	 *            
	 * @return Color fx equivalent.
	 */
	public static Color getFxColour(java.awt.Color swingColour) {
		Color color = new Color(((double) swingColour.getRed())/256, ((double) swingColour.getGreen())/256,
				((double) swingColour.getBlue())/256, 1);
		return color;
	}

}
