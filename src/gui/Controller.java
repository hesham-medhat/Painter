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
import javafx.scene.control.TextField;
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
	private JFXButton undoButton;
	@FXML
	private JFXButton redoButton;
	@FXML
	private TextField path;
	@FXML
	Label feedback;
	@FXML
	public Pane drawingPane;
	@FXML
	private javafx.scene.shape.Circle cc;
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
	private ArrayList<ShapeController> selectedShapes = new ArrayList<>();

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
		fcPicker.setDisable(set);
		bcPicker.setDisable(set);
		save.setDisable(set);
		load.setDisable(set);
		if (drawer.isPluginFound()) {
			pluginMaker.setDisable(set);
		}
	}

	@FXML
	private void saveState(ActionEvent e) {
		try {
			drawer.save(path.getText());
		} catch (Exception e1) {
			feedback.setText("Save failed.");
			e1.printStackTrace();
		}
	}

	@FXML
	private void loadState(ActionEvent e) {
		try {
			drawingPane.getChildren().clear();
			drawer.load(path.getText());
			shapeControllerList = new ArrayList<>();
			for (eg.edu.alexu.csd.oop.draw.Shape sh : drawer.getShapes()) {
				ShapeController controller = new ShapeController(drawingPane, drawer, sh);
				shapeControllerList.add(controller);
				sh.draw(drawingPane);
			}
		} catch (Exception e1) {
			feedback.setText("Load failed.");
			e1.printStackTrace();
		}
	}

	@FXML
	private void colourize(ActionEvent a) {
		if (expectingAction) {
			readColours();
			for (ShapeController sc : shapeControllerList) {
				if (sc.isSelected()) {
					ColourAdapter.setColours(sc.getShape(), fc, bc);
				}
			}
			expectAction(false);
		}
	}

	@FXML
	private void findPlugin(ActionEvent ae) {
		drawer.findPlugin(path.getText());
		if (drawer.isPluginFound()) {
			pluginMaker.setDisable(false);
		}
	}

	private void expectAction(boolean set) {
		expectingAction = set;
		if (!set) {// Deselect all.
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
	 * 
	 * @param visual
	 *            fxShape.
	 */
	private void colorVisualShape(Shape visual) {
		visual.setFill(fc);
		visual.setStroke(bc);
	}

	@FXML
	private void undo(final ActionEvent e) {
		drawer.undo();
	}

	@FXML
	private void redo(final ActionEvent e) {
		drawer.redo();
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
		drawingNow = "plugin";

		try {
			eg.edu.alexu.csd.oop.draw.Shape plugIn = drawer.findPlugin(path.getText()).newInstance();
			ShapeController shapeController = new ShapeController(drawingPane, drawer, plugIn);
			drawer.addShape(drawingPane, shapeController);
			paneInteraction(true);
		} catch (InstantiationException | IllegalAccessException e1) {
			feedback.setText("Couldn't make plugin.");
			finishDrawing();
			e1.printStackTrace();
		}

	}

	private void drawLine() {
		double fpx = firstClick.getX();
		double fpy = firstClick.getY();
		double spx = secondClick.getX();
		double spy = secondClick.getY();
		LineSegment line = new LineSegment(fpx, fpy, spx, spy);
		line.setFxShape(drawingLine);
		drawingPane.getChildren().remove(drawingLine);
		ColourAdapter.setColours(line, fc, bc);
		ShapeController shapeController = new ShapeController(drawingPane, drawer, line);
		drawer.addShape(drawingPane, shapeController);
		shapeControllerList.add(shapeController);
		finishDrawing();
	}

	private void drawCircle() {
		double rpx = firstClick.getX();
		double rpy = firstClick.getY();
		double radius = drawingLineLength;
		Circle circle = new Circle(radius, rpx, rpy);
		circle.setFxShape(drawingCircle);
		ColourAdapter.setColours(circle, fc, bc);
		drawingPane.getChildren().remove(drawingCircle);
		ShapeController shapeController = new ShapeController(drawingPane, drawer, circle);
		drawer.addShape(drawingPane, shapeController);
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
		ColourAdapter.setColours(ellipse, fc, bc);
		drawingPane.getChildren().remove(drawingLine);
		drawingPane.getChildren().remove(drawingEllipse);
		ShapeController shapeController = new ShapeController(drawingPane, drawer, ellipse);
		drawer.addShape(drawingPane, shapeController);
		shapeControllerList.add(shapeController);
		finishDrawing();
	}

	private void drawRec() {
		eg.edu.alexu.csd.oop.draw.cs70.Rectangle rectangle = new eg.edu.alexu.csd.oop.draw.cs70.Rectangle(
				firstClick.getX(), firstClick.getY(), Math.abs(firstClick.getX() - secondClick.getX()),
				Math.abs(firstClick.getY() - thirdClick.getY()));
		rectangle.setFxShape(drawingRec);
		ColourAdapter.setColours(rectangle, fc, bc);
		drawingPane.getChildren().remove(drawingLine);
		drawingPane.getChildren().remove(drawingRec);
		ShapeController shapeController = new ShapeController(drawingPane, drawer, rectangle);
		drawer.addShape(drawingPane, shapeController);
		shapeControllerList.add(shapeController);
		finishDrawing();
	}

	private void drawSquare() {
		eg.edu.alexu.csd.oop.draw.cs70.Rectangle square = new eg.edu.alexu.csd.oop.draw.cs70.Rectangle(
				firstClick.getX(), firstClick.getY(), Math.abs(firstClick.getX() - secondClick.getX()),
				Math.abs(firstClick.getY() - secondClick.getY()));
		square.setFxShape(drawingRec);
		ColourAdapter.setColours(square, fc, bc);
		drawingPane.getChildren().remove(drawingLine);
		drawingPane.getChildren().remove(drawingRec);
		ShapeController shapeController = new ShapeController(drawingPane, drawer, square);
		drawer.addShape(drawingPane, shapeController);
		shapeControllerList.add(shapeController);
		finishDrawing();
	}

	private void drawTriangle() {
		eg.edu.alexu.csd.oop.draw.cs70.Triangle triangle = new eg.edu.alexu.csd.oop.draw.cs70.Triangle(
				firstClick.getX(), secondClick.getX(), thirdClick.getX(), firstClick.getY(), secondClick.getY(),
				thirdClick.getY());
		triangle.setFxShape(drawingTriangle);
		ColourAdapter.setColours(triangle, fc, bc);
		drawingPane.getChildren().remove(drawingLine);
		drawingPane.getChildren().remove(drawingTriangle);
		ShapeController shapeController = new ShapeController(drawingPane, drawer, triangle);
		drawer.addShape(drawingPane, shapeController);
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
		if (firstClick == null) {
			if (drawingNow != null && drawingNow.equals("move")) {
				for (ShapeController sc : selectedShapes) {
					Point2D centerDifference = getCenterRelative(me);
					Point oldPosition = sc.getShape().getPosition();
					sc.getFx().relocate(centerDifference.getX() + oldPosition.getX(),
							centerDifference.getY() + oldPosition.getY());
				}
			}
		} else {
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
					drawingRec.setWidth(Math.abs(me.getX() - firstClick.getX()));
					drawingRec.setHeight(Math.abs(me.getX() - firstClick.getX()));
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
			colorVisualShape(drawingLine);
			drawingPane.getChildren().add(drawingLine);
			if (drawingNow.equals("line")) {
				feedback.setText("Set the ending point of the line.");
			} else if (drawingNow.equals("circle")) {
				drawingCircle = new javafx.scene.shape.Circle(firstClick.getX(), firstClick.getY(), drawingLineLength);
				colorVisualShape(drawingCircle);
				drawingPane.getChildren().remove(drawingLine);
				drawingPane.getChildren().add(drawingCircle);
				feedback.setText("Set the circle's radius.");
			} else if (drawingNow.equals("rec")) {
				feedback.setText("Set the rectangle's upper-right corner.");
			} else if (drawingNow.equals("ellipse")) {
				feedback.setText("Set the ellipse's width.");
			} else if (drawingNow.equals("square")) {
				drawingRec = new Rectangle(firstClick.getX(), firstClick.getY(),
						Math.abs(firstClick.getX() - me.getX()), Math.abs(me.getX() - firstClick.getX()));
				colorVisualShape(drawingRec);
				drawingPane.getChildren().add(drawingRec);
				feedback.setText("Set the square's lower right corner.");
			} else if (drawingNow.equals("triangle")) {
				feedback.setText("Set the triangle's second vertex.");
			} else if (drawingNow.equals("move")) {
				for (ShapeController sc : selectedShapes) {
					shapeControllerList.add(sc.move(getCenterRelative(me)));
					sc.setSelected(false);
				}
				finishDrawing();
			} else if (drawingNow.equals("copy")) {
				for (ShapeController sc : selectedShapes) {
					Point2D pos = new Point2D(me.getX(), me.getY());
					ShapeController newSC = sc.copy(pos);
					shapeControllerList.add(newSC);
				}
				finishDrawing();
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
				colorVisualShape(drawingRec);
				drawingPane.getChildren().add(drawingRec);
				feedback.setText("Set the rectangle's lower-right corner.");
			} else if (drawingNow.equals("ellipse")) {
				secondClick = new Point2D(me.getX(), firstClick.getY());
				drawingEllipse = new javafx.scene.shape.Ellipse(firstClick.getX(), firstClick.getY(),
						Math.abs(firstClick.getX() - me.getX()), Math.abs(me.getY() - firstClick.getY()));
				colorVisualShape(drawingEllipse);
				drawingPane.getChildren().add(drawingEllipse);
				feedback.setText("Set the ellipse's height.");
			} else if (drawingNow.equals("square")) {
				drawSquare();
			} else if (drawingNow.equals("triangle")) {
				drawingTriangle = new Polygon();
				drawingTriangle.getPoints().addAll(new Double[] { firstClick.getX(), firstClick.getY(),
						secondClick.getX(), secondClick.getY(), me.getX(), me.getY() });
				colorVisualShape(drawingTriangle);
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
			drawingRec = new Rectangle(firstClick.getX(), firstClick.getY(), Math.abs(firstClick.getX() - me.getX()),
					Math.abs(me.getY() - firstClick.getY()));
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
				Point position = sc.getShape().getPosition();
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

	@FXML
	private void moveAction(ActionEvent e) {
		selectedShapes.clear();
		if (expectingAction) {
			findSelected();
			drawingNow = "move";
		}
		expectAction(false);
	}

	@FXML
	private void copyAction(ActionEvent e) {
		selectedShapes.clear();
		if (expectingAction) {
			findSelected();
			drawingNow = "copy";
		}
		expectAction(false);
	}

	@FXML
	private void removeAction(ActionEvent e) {
		selectedShapes.clear();
		if (expectingAction) {
			findSelected();
			for (ShapeController sc : selectedShapes) {
				drawingPane.getChildren().remove(sc.getFx());
				drawer.removeShape(drawingPane, sc);
				sc.setSelected(false);
			}
			selectedShapes.clear();
			expectAction(false);
		}
	}

	private void findSelected() {
		for (ShapeController sc : shapeControllerList) {
			if (sc.isSelected()) {
				selectedShapes.add(sc);
			}
		}
	}

	private Point2D getCenterRelative(MouseEvent mouse) {
		return new Point2D(mouse.getX() - cc.getLayoutX(), mouse.getY() - cc.getLayoutY());
	}

}
