package eg.edu.alexu.csd.oop.draw.cs70;

import java.awt.Graphics;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import eg.edu.alexu.csd.oop.draw.Shape;

/**
 * @author Marina
 * drawer is the implementation for the interface drawing engine
 * that manipulates the shapes and classes inherited from the class stroke
 * it adds removes and updates shapes also perform the undo and redo operations
 *  in addition to save and load with different formats
 *
 */
public class Drawer implements DrawingEngine {

<<<<<<< HEAD

	private final ArrayList<Shape> shapes = new ArrayList<>();

=======
	/**
	 * a constant number to indicate maximum number of undo and redo allowed and
	 * the limits of the array list saving the commands performed and
	 * unperformed
	 */
	private static final int MAX_ARRAY_SIZE = 20;

	/**
	 * an array list containing all shapes on the canvas 
	 */
	
	private final ArrayList<Shape> ShapesOnCanvas = new ArrayList<>();
	
	/**
	 * an array list containing all commands performed to support the undo method with max size 
	 * 20 commands
	 */
>>>>>>> a9e8c897454a2e7c02fb2e555471a9b66a47dade
	private final ArrayList<ICommand> actionsPerformed = new ArrayList<>();
	/**
	 * an array list containing all commands unperformed to support the redo method with max size 
	 * 20 commands
	 */
	private final ArrayList<ICommand> actionsUNPerformed = new ArrayList<>();

	@Override
	public void refresh(final Graphics canvas) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param shape indicate any inherited shape from class stroke
	 * adds a shape on the canvas
	 */
	
	@Override
	public void addShape(final Shape shape) {
<<<<<<< HEAD
		shapes.add((Stroke) shape);
		final DrawCommand draw = new DrawCommand((Stroke) shape);
=======
		ShapesOnCanvas.add(shape);
		//create a command object
		final DrawCommand draw = new DrawCommand(shape);
>>>>>>> a9e8c897454a2e7c02fb2e555471a9b66a47dade
		draw.execute();
		//maintain max size of the array
		this.roundArray(actionsPerformed);
		actionsPerformed.add(draw);
	}

	@Override
	public void removeShape(final Shape shape) {
		shapes.remove(shape);
		final RemoveCommand remove = new RemoveCommand((Stroke) shape);
		remove.execute();
		this.roundArray(actionsPerformed);
		actionsPerformed.add(remove);
	}

	@Override
	public void updateShape(final Shape oldShape, final Shape newShape) {
		shapes.remove(oldShape);
		shapes.add(newShape);
		UpdateCommand update = new UpdateCommand(oldShape, newShape);
		update.execute();
		this.roundArray(actionsPerformed);
		actionsPerformed.add(update);
	}

	@Override
	public Shape[] getShapes() {
		final Object[] shapesArr = shapes.toArray();
		return (Shape[]) shapesArr;
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
		this.roundArray(actionsUNPerformed);
		actionsUNPerformed.add(action);
		if (action.getCommand().equals("draw")) {
			shapes.remove(action.getReciever(null));

		} else if (action.getCommand().equals("remove")) {
			shapes.add(action.getReciever(null));
		} else if (action.getCommand().equals("update")) {
			shapes.add(action.getReciever("old shape"));
			shapes.remove(action.getReciever("new shape"));
		}

	}

	@Override
	public void redo() {
		final ICommand action = actionsUNPerformed.get(actionsUNPerformed.size() - 1);
		action.execute();
		this.roundArray(actionsPerformed);
		actionsPerformed.add(action);
		if (action.getCommand() == "draw") {
			shapes.add(action.getReciever(null));

		} else if (action.getCommand() == "remove") {
			shapes.remove(action.getReciever(null));

<<<<<<< HEAD
		}else if (action.getCommand().equals("update")) {
			shapes.remove(action.getReciever("old shape"));
			shapes.add(action.getReciever("new shape"));
=======
		} else if (action.getCommand().equals("update")) {
			ShapesOnCanvas.remove(action.getReciever("old shape"));
			ShapesOnCanvas.add(action.getReciever("new shape"));
>>>>>>> a9e8c897454a2e7c02fb2e555471a9b66a47dade
		}
	}

	@Override
	public void save(final String path) {
		if (path == null || path.length() < 5) {
			throw new RuntimeException("Invalid path.");
		}
		String extension = path.substring(path.length() - 6);
		if (extension.substring(1).equals(".xml")) {
			try {
				FileWriter fw = new FileWriter(path);
				// TODO: xml saving.
				fw.close();
			} catch (IOException ioE) {
				ioE.printStackTrace();
			}
		} else if (extension.equals("json")) {
			try {
				FileWriter fw = new FileWriter(path);
				JSONObject jO = new JSONObject();
				JSONArray jShapes = new JSONArray();
				
				JSONArray jActionsPerformed = new JSONArray(actionsPerformed);
				JSONArray jActionsUNPerformed = new JSONArray(actionsUNPerformed);
				
				jO.append("shapes", jShapes);
				jO.append("actionsUNPerformed", jActionsPerformed);
				jO.append("actionsPerformed", jActionsUNPerformed);
				fw.close();
			} catch (JSONException jE) {
				jE.printStackTrace();
			} catch (IOException ioE) {
				ioE.printStackTrace();
			}
		}

	}

	@Override
	public void load(final String path) {
		// TODO Auto-generated method stub

	}

	private void roundArray(ArrayList<ICommand> array) {
		if (array.size() == MAX_ARRAY_SIZE) {
			array.remove(0);

		}
	}

}
