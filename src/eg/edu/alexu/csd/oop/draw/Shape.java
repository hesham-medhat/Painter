package eg.edu.alexu.csd.oop.draw;

public interface Shape {

	/**
	 * Sets the central position of the shape.
	 * 
	 * @param position
	 *            to be set
	 */
	public void setPosition(java.awt.Point position);

	/**
	 * Gets the central position of the shape.
	 * 
	 * @return position as java.awt.Point
	 */
	public java.awt.Point getPosition();

	/**
	 * Update shape specific properties (e.g., radius).
	 * 
	 * @param properties
	 *            map of properties with Strings as keys.
	 */
	public void setProperties(java.util.Map<String, Double> properties);

	/**
	 * Gets the properties of the shape.
	 * 
	 * @return properties as a map with Strings as keys.
	 */
	public java.util.Map<String, Double> getProperties();

	/**
	 * Sets the border color of the shape.
	 * 
	 * @param color
	 *            to be set.
	 */
	public void setColor(java.awt.Color color);

	/**
	 * Gets the color of the shape.
	 * 
	 * @return color as java.awt.Color object.
	 */
	public java.awt.Color getColor();

	/**
	 * Sets the filler color of the shape within its borders.
	 * 
	 * @param color
	 *            to be set.
	 */
	public void setFillColor(java.awt.Color color);

	/**
	 * Gets the filler color of the shape within its borders.
	 * 
	 * @return Filler color as java.awt.Color object.
	 */
	public java.awt.Color getFillColor();

	/**
	 * Redraw the shape on the canvas.
	 */
	public void draw(java.awt.Graphics canvas);

	/**
	 * Create a deep clone of the shape.
	 */
	public Object clone() throws CloneNotSupportedException;
}