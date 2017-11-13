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
	private Pane drawingPane;
	private Line drawingLine;
	@FXML
	Label feedback;
	private Point2D firstClick;
	private Point2D secondClick;
	private Point2D thirdClick;

	private boolean pluginFound;
	private String drawingNow;

	/**
	 * The drawing engine.
	 */
	private Drawer drawer;

	public Controller() {
		drawer = new Drawer();
		pluginFound = false;
		/*
		shapeMakers = new JFXButton[7];
		shapeMakers[0] = lineMaker;
		shapeMakers[1] = circleMaker;
		shapeMakers[2] = recMaker;
		shapeMakers[3] = squareMaker;
		shapeMakers[4] = triangleMaker;
		shapeMakers[5] = ellipseMaker;
		shapeMakers[6] = pluginMaker;
		*/
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
		feedback.setText("Set the starting and ending points of the line.");
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
		drawingPane.getChildren().add(fxLine);
		//TODO: line.setFxShape(fxLine);
		drawer.addShape(line);
		finishDrawing();
	}

	private void finishDrawing() {
		firstClick = null;
		secondClick = null;
		drawingNow = null;
		drawingPane.getChildren().remove(drawingLine);
		drawingLine = null;
		setDrawingButtonsDisabled(false);
		feedback.setText("");
	}

	@FXML
	private void paneClick(MouseEvent me) {
		if (drawingNow == null) {
			return;
		}
		if (firstClick == null) {
			firstClick = new Point2D(me.getX(), me.getY());
		} else if (secondClick == null) {// Clicked once before.
			secondClick = new Point2D(me.getX(), me.getY());
			if ("line".equals(drawingNow)) {
				drawLine();
			}
		} else if (thirdClick == null) {// Clicked twice before.
			thirdClick = new Point2D(me.getX(), me.getY());
		}
	}

}
