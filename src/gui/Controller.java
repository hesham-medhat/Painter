package gui;

import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;

import eg.edu.alexu.csd.oop.draw.cs70.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

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
	public Pane drawingPane;
	private Line drawingLine;
	private double drawingLineLength = 0;
	private Rectangle drawingRec;
	private javafx.scene.shape.Ellipse drawingEllipse;
	private javafx.scene.shape.Circle drawingCircle;
	private javafx.scene.shape.Polygon drawingTriangle;
	@FXML
	Label feedback;
	private Point2D firstClick;
	private Point2D secondClick;
	private Point2D thirdClick;

	private String drawingNow;

	/**
	 * The drawing engine.
	 */
	private Drawer drawer;
	private ArrayList<ShapeController> shapeControllerList = new ArrayList<>();

	public Controller() {
		drawer = new Drawer();
	}

	private void setDrawingButtonsDisabled(boolean set) {
		lineMaker.setDisable(set);
		circleMaker.setDisable(set);
		recMaker.setDisable(set);
		squareMaker.setDisable(set);
		triangleMaker.setDisable(set);
		ellipseMaker.setDisable(set);
		pluginMaker.setDisable(set);
	}

	@FXML
	private void makeLine(final ActionEvent e) {
		drawingNow = "line";
		setDrawingButtonsDisabled(true);
		feedback.setText("Set the starting point of the line.");
	}

	@FXML
	private void makeCircle(final ActionEvent e) {
		drawingNow = "circle";
		setDrawingButtonsDisabled(true);
		feedback.setText("Set the circle's center.");
	}

	@FXML
	private void makeRec(final ActionEvent e) {
		drawingNow = "rec";
		setDrawingButtonsDisabled(true);
		feedback.setText("Set the rectangle's upper-left corner.");
	}

	@FXML
	private void makeSquare(final ActionEvent e) {
		drawingNow = "square";
		setDrawingButtonsDisabled(true);
		feedback.setText("Set the square's upper-left corner.");

	}

	@FXML
	private void makeTriangle(final ActionEvent e) {
		drawingNow = "triangle";
		setDrawingButtonsDisabled(true);
		feedback.setText("Set the triangle's first corner.");
	}

	@FXML
	private void makeEllipse(final ActionEvent e) {
		drawingNow = "ellipse";
		setDrawingButtonsDisabled(true);
		feedback.setText("Set the ellipse's center.");
	}

	@FXML
	private void makePlugin(final ActionEvent e) {
		drawingNow = "plugin";
		setDrawingButtonsDisabled(true);
	}

	private void drawLine() {
		double fpx = firstClick.getX();
		double fpy = firstClick.getY();
		double spx = secondClick.getX();
		double spy = secondClick.getY();
		LineSegment line = new LineSegment(fpx, fpy, spx, spy);
		line.setFxShape(drawingLine);
		drawingPane.getChildren().remove(drawingLine);
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
		setDrawingButtonsDisabled(false);
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
					drawingRec.setHeight(Math.abs(me.getY() - firstClick.getY()));
				} else {
					if (me.getX() >= firstClick.getX()) {
						drawingLine.setEndX(me.getX());
					}

				}
			} else if (drawingNow.equals("square")) {
				if (drawingRec != null) {
					drawingRec.setHeight(Math.abs(me.getY() - firstClick.getY()));
					drawingRec.setWidth(Math.abs(me.getY() - firstClick.getY()));

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
		if (drawingNow == null) {
			return;
		}
		if (firstClick == null) {// Didn't have first click yet.
			firstClick = new Point2D(me.getX(), me.getY());
			drawingLine = new Line(me.getX(), me.getY(), me.getX(), me.getY());
			drawingPane.getChildren().add(drawingLine);

			if (drawingNow.equals("line")) {
				feedback.setText("Set the ending point of the line.");
			} else if (drawingNow.equals("circle")) {
				drawingCircle = new javafx.scene.shape.Circle(firstClick.getX(), firstClick.getY(), drawingLineLength);
				drawingPane.getChildren().add(drawingCircle);
				feedback.setText("Set the circle's radius.");
			} else if (drawingNow.equals("rec")) {
				feedback.setText("Set the rectangle's upper-right corner.");
			} else if (drawingNow.equals("ellipse")) {
				feedback.setText("Set the ellipse's width.");
			} else if (drawingNow.equals("square")) {
				drawingRec = new Rectangle(firstClick.getX(), firstClick.getY(),
						Math.abs(firstClick.getX() - me.getX()), Math.abs(me.getY() - firstClick.getY()));
				drawingPane.getChildren().add(drawingRec);
				feedback.setText("Set the square's lower right corner.");
			} else if (drawingNow.equals("triangle")) {
				feedback.setText("Set the second corner of triangle.");

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
				drawingPane.getChildren().add(drawingRec);
				feedback.setText("Set the rectangle's lower-right corner.");
			} else if (drawingNow.equals("ellipse")) {
				secondClick = new Point2D(me.getX(), firstClick.getY());
				drawingEllipse = new javafx.scene.shape.Ellipse(firstClick.getX(), firstClick.getY(),
						Math.abs(firstClick.getX() - me.getX()), Math.abs(me.getY() - firstClick.getY()));
				drawingPane.getChildren().add(drawingEllipse);
				feedback.setText("Set the ellipse's height.");
			} else if (drawingNow.equals("square")) {
				drawSquare();
			} else if (drawingNow.equals("triangle")) {
				drawingTriangle = new Polygon();
				drawingTriangle.getPoints().addAll(new Double[] { firstClick.getX(), firstClick.getY(),
						secondClick.getX(), secondClick.getY(), me.getX(), me.getY() });
				drawingPane.getChildren().remove(drawingLine);
				drawingPane.getChildren().add(drawingTriangle);
				feedback.setText("Set the triangle's third corner.");

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

}
