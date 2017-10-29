package eg.edu.alexu.csd.oop.draw.cs70;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import eg.edu.alexu.csd.oop.draw.Shape;

public class Drawer implements DrawingEngine {

	private final ArrayList<Stroke> ShapesOnCanvas = new ArrayList<>();
	private final ArrayList<ICommand> actionsPerformed = new ArrayList<>();
	private final ArrayList<ICommand> actionsUNPerformed = new ArrayList<>();

	@Override
	public void refresh(final Graphics canvas) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addShape(final Shape shape) {
		ShapesOnCanvas.add((Stroke) shape);
		final DrawCommand draw = new DrawCommand((Stroke) shape);
		draw.execute();
		actionsPerformed.add(draw);
	}

	@Override
	public void removeShape(final Shape shape) {
		ShapesOnCanvas.remove(shape);
		final RemoveCommand remove = new RemoveCommand((Stroke) shape);
		remove.execute();
		actionsPerformed.add(remove);
	}

	@Override
	public void updateShape(final Shape oldShape, final Shape newShape) {
		// TODO Auto-generated method stub

	}

	@Override
	public Shape[] getShapes() {
		final Object[] shapes = ShapesOnCanvas.toArray();
		return (Stroke[]) shapes;
	}

	@Override
	public List<Class<? extends Shape>> getSupportedShapes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void undo() {
		final ICommand action = actionsPerformed.get(actionsPerformed.size() - 1);
		action.unexecute();
		actionsUNPerformed.add(action);
		if (action.getCommand() == "draw") {
			ShapesOnCanvas.remove(action.getReciever());

		} else if (action.getCommand() == "remove") {
			ShapesOnCanvas.add(action.getReciever());
		}

	}

	@Override
	public void redo() {
		final ICommand action = actionsUNPerformed.get(actionsUNPerformed.size() - 1);
		action.execute();
		actionsPerformed.add(action);
		if (action.getCommand() == "draw") {
			ShapesOnCanvas.add(action.getReciever());

		} else if (action.getCommand() == "remove") {
			ShapesOnCanvas.remove(action.getReciever());

		}
	}

	@Override
	public void save(final String path) {
		// TODO Auto-generated method stub

	}

	@Override
	public void load(final String path) {
		// TODO Auto-generated method stub

	}

}
