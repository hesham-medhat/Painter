package gui;

import java.awt.Point;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;

import eg.edu.alexu.csd.oop.draw.cs70.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Controller {

	@FXML
	private JFXButton lineMaker;
	@FXML
	private JFXButton circleMaker;
	@FXML
	private JFXButton recMaker;
	@FXML
	private JFXButton squareMaker;
	@FXML
	private JFXButton triangleMaker;
	@FXML
	private JFXButton ellipseMaker;
	@FXML
	private JFXButton pluginMaker;
	@FXML
	private JFXButton remove;
	@FXML
	private JFXButton copy;
	@FXML
	private JFXButton select;
	@FXML
	private JFXButton move;
	@FXML
	private ColorPicker fcPicker;
	@FXML
	private ColorPicker bcPicker;
	@FXML
	private JFXButton applyColours;
	@FXML
	private JFXButton save;
	@FXML
	private JFXButton load;

	@FXML
	Label feedback;
	@FXML
	public Pane drawingPane;
	private Line drawingLine;
	private double drawingLineLength = 0;
	private Rectangle drawingRec;
	private javafx.scene.shape.Ellipse drawingEllipse;
	private javafx.scene.shape.Circle drawingCircle;
	private javafx.scene.shape.Polygon drawingTriangle;
	private Point2D firstClick;
	private Point2D secondClick;
	private Point2D thirdClick;
	private Color fc;
	private Color bc;
	private String drawingNow;
	private boolean expectingAction;

	/**
	 * The drawing engine.
	 */
	private Drawer drawer;
	private ArrayList<ShapeController> shapeControllerList = new ArrayList<>();

	public Controller() {
		drawer = new Drawer();
	}

	private void readColours() {
		fc = fcPicker.getValue();
		bc = bcPicker.getValue();
	}

	private final void setWidthInRange(MouseEvent me) {
		if (me.getX() >= firstClick.getX()) {
			drawingRec.setWidth(me.getX() - firstClick.getX());
		}
	}

	private final void setHeightInRange(MouseEvent me) {
		if (me.getY() >= firstClick.getY()) {
			drawingRec.setHeight(me.getY() - firstClick.getY());
		}
	}

	private void paneInteraction(boolean set) {
		lineMaker.setDisable(set);
		circleMaker.setDisable(set);
		recMaker.setDisable(set);
		squareMaker.setDisable(set);
		triangleMaker.setDisable(set);
		ellipseMaker.setDisable(set);
		pluginMaker.setDisable(set);
		fcPicker.setDisable(set);
		bcPicker.setDisable(set);
		save.setDisable(set);
		load.setDisable(set);
	}

	@FXML
	private void colourize (ActionEvent a) {
		if (expectingAction) {
			readColours();
			for (ShapeController sc : shapeControllerList) {
				if (sc.isSelected()) {
					this.colorShape(sc.getFx());
					sc.shape.setColor(ShapeController.getSwingColour(bc));
					sc.shape.setFillColor(ShapeController.getSwingColour(fc));
				}
			}
			expectAction(false);
		}
	}

	private void expectAction(boolean set) {
		expectingAction = set;
		if (!set) {//Deselect all.
			for (final ShapeController sc : shapeControllerList) {
				sc.setSelected(false);
			}
		}
		set = !set;
		remove.setDisable(set);
		move.setDisable(set);
		copy.setDisable(set);
		applyColours.setDisable(set);
		fcPicker.setDisable(set);
		bcPicker.setDisable(set);
	}

	/**
	 * Colors the given fxShape.
	 * @param visual fxShape.
	 */
	private void colorShape(Shape visual) {
		visual.setFill(fc);
		visual.setStroke(bc);
	}

	@FXML
	private void makeLine(final ActionEvent e) {
		readColours();
		drawingNow = "line";
		paneInteraction(true);
		feedback.setText("Set the starting point of the line.");
	}

	@FXML
	private void makeCircle(final ActionEvent e) {
		readColours();
		drawingNow = "circle";
		paneInteraction(true);
		feedback.setText("Set the circle's center.");
	}

	@FXML
	private void makeRec(final ActionEvent e) {
		readColours();
		drawingNow = "rec";
		paneInteraction(true);
		feedback.setText("Set the rectangle's upper-left corner.");
	}

	@FXML
	private void makeSquare(final ActionEvent e) {
		readColours();
		drawingNow = "square";
		paneInteraction(true);
		feedback.setText("Set the square's upper-left corner.");

	}

	@FXML
	private void makeTriangle(final ActionEvent e) {
		readColours();
		drawingNow = "triangle";
		paneInteraction(true);
		feedback.setText("Set the triangle's first vertex.");
	}

	@FXML
	private void makeEllipse(final ActionEvent e) {
		readColours();
		drawingNow = "ellipse";
		paneInteraction(true);
		feedback.setText("Set the ellipse's center.");
	}

	@FXML
	private void makePlugin(final ActionEvent e) {
		readColours();
		drawingNow = "plugin"; //TODO
		paneInteraction(true);
	}

	private void drawLine() {
		double fpx = firstClick.getX();
		double fpy = firstClick.getY();
		double spx = secondClick.getX();
		double spy = secondClick.getY();
		LineSegment line = new LineSegment(fpx, fpy, spx, spy);
		line.setFxShape(drawingLine);
		drawingPane.getChildren().remove(drawingLine);
		line.setColor(ShapeController.getSwingColour(bc));
		line.setFillColor(ShapeController.getSwingColour(fc));
		drawer.addShape(line, drawingPane);
		ShapeController shapeController = new ShapeController(drawingPane, drawer, line);
		shapeControllerList.add(shapeController);
		finishDrawing();
	}

	private void drawCircle() {
		double rpx = firstClick.getX();
		double rpy = firstClick.getY();
		double radius = drawingLineLength;
		Circle circle = new Circle(radius, rpx, rpy);
		circle.setFxShape(drawingCircle);
		circle.setColor(ShapeController.getSwingColour(bc));
		circle.setFillColor(ShapeController.getSwingColour(fc));
		drawingPane.getChildren().remove(drawingCircle);
		drawer.addShape(circle, drawingPane);
		ShapeController shapeController = new ShapeController(drawingPane, drawer, circle);
		shapeControllerList.add(shapeController);
		finishDrawing();
	}

	private void drawEllipse() {
		double rpx = firstClick.getX();
		double rpy = firstClick.getY();
		double radiusX = Math.abs(firstClick.getX() - secondClick.getX());
		double radiusY = Math.abs(firstClick.getY() - thirdClick.getY());
		Ellipse ellipse = new Ellipse(radiusX, radiusY, rpx, rpy);
		ellipse.setColor(ShapeController.getSwingColour(bc));
		ellipse.setFillColor(ShapeController.getSwingColour(fc));
		ellipse.setFxShape(drawingEllipse);
		drawingPane.getChildren().remove(drawingEllipse);
		drawer.addShape(ellipse, drawingPane);
		ShapeController shapeController = new ShapeController(drawingPane, drawer, ellipse);
		shapeControllerList.add(shapeController);
		finishDrawing();
	}

	private void drawRec() {
		eg.edu.alexu.csd.oop.draw.cs70.Rectangle rectangle = new eg.edu.alexu.csd.oop.draw.cs70.Rectangle(
				firstClick.getX(), firstClick.getY(), Math.abs(firstClick.getX() - secondClick.getX()),
				Math.abs(firstClick.getY() - thirdClick.getY()));
		rectangle.setFxShape(drawingRec);
		rectangle.setColor(ShapeController.getSwingColour(bc));
		rectangle.setFillColor(ShapeController.getSwingColour(fc));
		drawingPane.getChildren().remove(drawingRec);
		drawer.addShape(rectangle, drawingPane);
		ShapeController shapeController = new ShapeController(drawingPane, drawer, rectangle);
		shapeControllerList.add(shapeController);
		finishDrawing();
	}

	private void drawSquare() {
		eg.edu.alexu.csd.oop.draw.cs70.Rectangle square = new eg.edu.alexu.csd.oop.draw.cs70.Rectangle(
				firstClick.getX(), firstClick.getY(), Math.abs(firstClick.getX() - secondClick.getX()),
				Math.abs(firstClick.getY() - secondClick.getY()));
		square.setFxShape(drawingRec);
		square.setColor(ShapeController.getSwingColour(bc));
		square.setFillColor(ShapeController.getSwingColour(fc));
		drawingPane.getChildren().remove(drawingRec);
		drawer.addShape(square, drawingPane);
		ShapeController shapeController = new ShapeController(drawingPane, drawer, square);
		shapeControllerList.add(shapeController);
		finishDrawing();
	}

	private void drawTriangle() {
		eg.edu.alexu.csd.oop.draw.cs70.Triangle triangle = new eg.edu.alexu.csd.oop.draw.cs70.Triangle(
				firstClick.getX(), secondClick.getX(), thirdClick.getX(), firstClick.getY(), secondClick.getY(),
				thirdClick.getY());
		triangle.setFxShape(drawingTriangle);
		triangle.setColor(ShapeController.getSwingColour(bc));
		triangle.setFillColor(ShapeController.getSwingColour(fc));
		drawingPane.getChildren().remove(drawingTriangle);
		drawer.addShape(triangle, drawingPane);
		ShapeController shapeController = new ShapeController(drawingPane, drawer, triangle);
		shapeControllerList.add(shapeController);
		finishDrawing();
	}

	private void finishDrawing() {
		firstClick = null;
		secondClick = null;
		thirdClick = null;
		drawingNow = null;
		drawingLine = null;
		drawingLineLength = 0;
		drawingRec = null;
		drawingEllipse = null;
		drawingCircle = null;
		drawingTriangle = null;
		paneInteraction(false);
		feedback.setText("");
	}

	@FXML
	private void moveDrawer(final MouseEvent me) {
		if (firstClick != null) {
			if (drawingNow.equals("circle")) {
				drawingLine.setEndX(me.getX());
				drawingLine.setEndY(me.getY());
				drawingLineLength = Math
						.sqrt(Math.pow(firstClick.getX() - me.getX(), 2) + Math.pow(firstClick.getY() - me.getY(), 2));
				drawingCircle.setRadius(drawingLineLength);
			}

			else if (drawingNow.equals("rec")) {
				if (drawingRec != null) {
					setHeightInRange(me);
				} else {
					if (me.getX() >= firstClick.getX()) {
						drawingLine.setEndX(me.getX());
					}
				}
			} else if (drawingNow.equals("square")) {
				if (drawingRec != null) {
					setWidthInRange(me);
					setHeightInRange(me);

				}
			} else if (drawingNow.equals("ellipse")) {
				if (drawingEllipse != null) {
					drawingEllipse.setRadiusY(Math.abs(me.getY() - firstClick.getY()));

				} else {
					drawingLine.setEndX(me.getX());
				}
			} else if (drawingNow.equals("triangle")) {
				if (drawingTriangle != null) {
					drawingTriangle.getPoints().setAll(new Double[] { firstClick.getX(), firstClick.getY(),
							secondClick.getX(), secondClick.getY(), me.getX(), me.getY() });
				} else {
					drawingLine.setEndX(me.getX());
					drawingLine.setEndY(me.getY());
				}
			} else if (drawingNow.equals("line")) {
				drawingLine.setEndX(me.getX());
				drawingLine.setEndY(me.getY());
			}
		}

	}

	@FXML
	private void paneClick(MouseEvent me) {
		if (drawingNow == null || drawingNow.equals("selecting")) {
			return;
		}
		if (firstClick == null) {// Didn't have first click yet.
			firstClick = new Point2D(me.getX(), me.getY());
			drawingLine = new Line(me.getX(), me.getY(), me.getX(), me.getY());
			colorShape(drawingLine);
			drawingPane.getChildren().add(drawingLine);
			if (drawingNow.equals("line")) {
				feedback.setText("Set the ending point of the line.");
			} else if (drawingNow.equals("circle")) {
				drawingCircle = new javafx.scene.shape.Circle(
						firstClick.getX(), firstClick.getY(), drawingLineLength);
				colorShape(drawingCircle);
				drawingPane.getChildren().add(drawingCircle);
				feedback.setText("Set the circle's radius.");
			} else if (drawingNow.equals("rec")) {
				feedback.setText("Set the rectangle's upper-right corner.");
			} else if (drawingNow.equals("ellipse")) {
				feedback.setText("Set the ellipse's width.");
			} else if (drawingNow.equals("square")) {
				drawingRec = new Rectangle(firstClick.getX(), firstClick.getY(),
						Math.abs(firstClick.getX() - me.getX()), Math.abs(me.getY() - firstClick.getY()));
				colorShape(drawingRec);
				drawingPane.getChildren().add(drawingRec);
				feedback.setText("Set the square's lower right corner.");
			} else if (drawingNow.equals("triangle")) {
				feedback.setText("Set the triangle's second vertex.");
			}
		} else if (secondClick == null) {// Clicked once before.
			secondClick = new Point2D(me.getX(), me.getY());
			if (drawingNow.equals("line")) {
				drawLine();
			} else if (drawingNow.equals("circle")) {
				drawCircle();
			} else if (drawingNow.equals("rec")) {
				secondClick = new Point2D(me.getX(), firstClick.getY());
				drawingRec = new Rectangle(firstClick.getX(), firstClick.getY(),
						Math.abs(firstClick.getX() - me.getX()), Math.abs(me.getY() - firstClick.getY()));
				colorShape(drawingRec);
				drawingPane.getChildren().add(drawingRec);
				feedback.setText("Set the rectangle's lower-right corner.");
			} else if (drawingNow.equals("ellipse")) {
				secondClick = new Point2D(me.getX(), firstClick.getY());
				drawingEllipse = new javafx.scene.shape.Ellipse(firstClick.getX(), firstClick.getY(),
						Math.abs(firstClick.getX() - me.getX()), Math.abs(me.getY() - firstClick.getY()));
				colorShape(drawingEllipse);
				drawingPane.getChildren().add(drawingEllipse);
				feedback.setText("Set the ellipse's height.");
			} else if (drawingNow.equals("square")) {
				drawSquare();
			} else if (drawingNow.equals("triangle")) {
				drawingTriangle = new Polygon();
				drawingTriangle.getPoints().addAll(new Double[] { firstClick.getX(), firstClick.getY(),
						secondClick.getX(), secondClick.getY(), me.getX(), me.getY() });
				colorShape(drawingTriangle);
				drawingPane.getChildren().remove(drawingLine);
				drawingPane.getChildren().add(drawingTriangle);
				feedback.setText("Set the triangle's third vertex.");
			}
		} else if (thirdClick == null) {// Clicked twice before.
			thirdClick = new Point2D(me.getX(), me.getY());
			if (drawingNow.equals("rec")) {
				drawRec();
			} else if (drawingNow.equals("ellipse")) {
				drawEllipse();
			} else if (drawingNow.equals("triangle")) {
				drawTriangle();
			}
		}
	}

	@FXML
	private void startSelect(MouseEvent me) {
		if (drawingNow != null && drawingNow.equals("selecting")) {
			firstClick = new Point2D(me.getX(), me.getY());
			feedback.setText("Drag to select items by center.");
			drawingRec = new Rectangle(firstClick.getX(), firstClick.getY(),
					Math.abs(firstClick.getX() - me.getX()), Math.abs(me.getY() - firstClick.getY()));
			drawingRec.setFill(Color.TRANSPARENT);
			drawingRec.setStroke(Color.LIGHTBLUE);
			drawingPane.getChildren().add(drawingRec);
		}
	}

	@FXML
	private void initiateSelect(ActionEvent e) {
		paneInteraction(true);
		feedback.setText("Start dragging by top left corner for selection.");
		drawingNow = "selecting";
	}

	@FXML
	private void selecting(MouseEvent me) {
		if (drawingNow != null && drawingNow.equals("selecting")) {
			if (drawingRec != null) {
				setWidthInRange(me);
				setHeightInRange(me);
			}
		}
	}

	@FXML
	private void finishSelect(MouseEvent me) {
		if (drawingNow != null && drawingNow.equals("selecting")) {
			for (ShapeController sc : shapeControllerList) {
				Point position = sc.shape.getPosition();
				final double x = position.getX();
				final double y = position.getY();
				if (drawingRec.intersects(x, y, 0, 0)) {
					sc.setSelected(true);
				}
			}
			drawingPane.getChildren().remove(drawingRec);
			finishDrawing();
			expectAction(true);
		}
	}

}
