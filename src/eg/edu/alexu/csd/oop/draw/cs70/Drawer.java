package eg.edu.alexu.csd.oop.draw.cs70;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import eg.edu.alexu.csd.oop.draw.Shape;

public class Drawer implements DrawingEngine {
	
	private ArrayList<Stroke> ShapesOnCanvas = new ArrayList<>();
	private ArrayList<ICommand> actionsPerformed = new ArrayList<>();
	private ArrayList<ICommand> actionsUNPerformed = new ArrayList<>();

	@Override
	public void refresh(Graphics canvas) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addShape(Shape shape) {
		ShapesOnCanvas.add((Stroke)shape);
		DrawCommand draw = new DrawCommand((Stroke)shape);
		draw.execute();
		actionsPerformed.add(draw);
	}

	@Override
	public void removeShape(Shape shape) {
		ShapesOnCanvas.remove((Stroke)shape);
		RemoveCommand remove = new RemoveCommand((Stroke)shape);
		remove.execute();
		actionsPerformed.add(remove);
	}

	@Override
	public void updateShape(Shape oldShape, Shape newShape) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Shape[] getShapes() {
		Object [] shapes = ShapesOnCanvas.toArray();
		return (Stroke [])shapes;
	}

	@Override
	public List<Class<? extends Shape>> getSupportedShapes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void undo() {
		ICommand action = actionsPerformed.get(actionsPerformed.size()-1);
		action.unexecute();
		actionsUNPerformed.add(action);
		
		
	}

	@Override
	public void redo() {
			ICommand action = actionsUNPerformed.get(actionsUNPerformed.size()-1);
			action.execute();
			actionsPerformed.add(action);
			if(action.getCommand()=="draw"){
				ShapesOnCanvas.add((Stroke)(action.getReciever()));
				
			}
	}

	@Override
	public void save(String path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void load(String path) {
		// TODO Auto-generated method stub
		
	}

}
