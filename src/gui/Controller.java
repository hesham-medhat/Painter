package gui;

import com.jfoenix.controls.JFXButton;

import eg.edu.alexu.csd.oop.draw.cs70.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

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
	public Pane drawingPane;
	private Line drawingLine1;
	private Line drawingLine2;
	private Line drawingLine3;
	private Line drawingLine4;
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
	}

	@FXML
	private void makeTriangle(final ActionEvent e) {
		drawingNow = "triangle";
		setDrawingButtonsDisabled(true);
	}

	@FXML
	private void makeEllipse(final ActionEvent e) {
		drawingNow = "ellipse";
		setDrawingButtonsDisabled(true);
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
		Line fxLine = new Line(fpx, fpy, spx, spy);
		LineSegment line = new LineSegment(fpx, fpy, spx, spy);
		line.setFxShape(fxLine);
		drawer.addShape(line, drawingPane);
		finishDrawing();
	}

	private void drawCircle() {
		double rpx = firstClick.getX();
		double rpy = firstClick.getY();
		double radius = firstClick.distance(secondClick);
		Circle circle = new Circle(radius, rpx, rpy);
		javafx.scene.shape.Circle fxCircle = new javafx.scene.shape.Circle(rpx, rpy, radius);
		circle.setFxShape(fxCircle);
		drawer.addShape(circle, drawingPane);
		finishDrawing();
	}
	
	private void drawEllipse() {
		double rpx = firstClick.getX();
		double rpy = firstClick.getY();
		double radiusX = firstClick.distance(secondClick);
		double radiusY = thirdClick.distance(secondClick);
		Ellipse ellipse = new Ellipse(radiusX, radiusY, rpx, rpy);
		javafx.scene.shape.Ellipse fxEllipse = new javafx.scene.shape.Ellipse(radiusX,radiusY,rpx, rpy);
		ellipse.setFxShape(fxEllipse);
		drawer.addShape(ellipse, drawingPane);
		finishDrawing();
	}

	private void finishDrawing() {
		firstClick = null;
		secondClick = null;
		thirdClick = null;
		drawingNow = null;
		drawingPane.getChildren().remove(drawingLine1);
		drawingLine1 = null;
		drawingLine2 = null;
		drawingLine3 = null;
		drawingLine4 = null;
		setDrawingButtonsDisabled(false);
		feedback.setText("");
	}

	@FXML
	private void moveDrawer(final MouseEvent me) {
		if (drawingLine1 != null) {
			if (drawingNow.equals("rec")) {
				if (drawingLine2 != null) {
					drawingLine2.setEndY(me.getY());
					drawingLine3.setEndY(me.getY());
					drawingLine4.setEndY(me.getY());
					drawingLine4.setStartY(me.getY());
				} else {
					drawingLine1.setEndX(me.getX());
				}
			} else {
				drawingLine1.setEndX(me.getX());
				drawingLine1.setEndY(me.getY());
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
			drawingLine1 = new Line(me.getX(), me.getY(), me.getX(), me.getY());
			drawingPane.getChildren().add(drawingLine1);
			if (drawingNow.equals("line")) {
				feedback.setText("Set the ending point of the line.");
			} else if (drawingNow.equals("circle")) {
				feedback.setText("Set the circle's radius.");
			} else if (drawingNow.equals("rec")) {
				feedback.setText("Set the rectangle's upper-right corner.");
			}
		} else if (secondClick == null) {// Clicked once before.
			secondClick = new Point2D(me.getX(), me.getY());
			if (drawingNow.equals("line")) {
				drawLine();
			} else if (drawingNow.equals("circle")) {
				drawCircle();
			} else if (drawingNow.equals("rec")) {
				secondClick = new Point2D(me.getX(), firstClick.getY());
				drawingLine2 = new Line(secondClick.getX(), secondClick.getY(), secondClick.getX(), me.getY());
				drawingLine3 = new Line(firstClick.getX(), firstClick.getY(), firstClick.getX(), me.getY());
				drawingLine4 = new Line(firstClick.getX(), me.getY(), secondClick.getX(), me.getY());
				drawingPane.getChildren().add(drawingLine2);
				drawingPane.getChildren().add(drawingLine3);
				drawingPane.getChildren().add(drawingLine4);
				feedback.setText("Set the rectangle's lower-right corner.");
			}
		} else if (thirdClick == null) {// Clicked twice before.
			thirdClick = new Point2D(me.getX(), me.getY());
		}
	}

}
