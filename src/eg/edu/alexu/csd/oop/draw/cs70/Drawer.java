package eg.edu.alexu.csd.oop.draw.cs70;

import java.awt.Graphics;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import java.net.*;

import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import eg.edu.alexu.csd.oop.draw.ICommand;
import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.Stroke;

public class Drawer implements DrawingEngine {
	private static final int MAX_SIZE = 20;
	private static final String CLASSES_PACKAGE = "bin.eg.edu.alexu.csd.oop.draw.cs70";

	private final ArrayList<Shape> shapes = new ArrayList<>();

	private final ArrayList<ICommand> actionsPerformed = new ArrayList<>();
	private final ArrayList<ICommand> actionsUNPerformed = new ArrayList<>();

	@Override
	public void refresh(final Graphics canvas) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addShape(final Shape shape) {
		shapes.add(shape);
		final DrawCommand draw = new DrawCommand(shape);
		draw.execute();
		addCommand(actionsPerformed, draw);
	}

	@Override
	public void removeShape(final Shape shape) {
		shapes.remove(shape);
		final RemoveCommand remove = new RemoveCommand(shape);
		remove.execute();
		addCommand(actionsPerformed, remove);
	}

	@Override
	public void updateShape(final Shape oldShape, final Shape newShape) {
		shapes.remove(oldShape);
		shapes.add(newShape);
		UpdateCommand update = new UpdateCommand(oldShape, newShape);
		update.execute();
		addCommand(actionsPerformed, update);
	}

	@Override
	public Shape[] getShapes() {
		Shape[] shapesArr = new Shape[shapes.size()];
		int i = 0;
		for (Shape sh : shapes) {
			shapesArr[i++] = sh;
		}
		return shapesArr;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Class<? extends Shape>> getSupportedShapes() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		assert classLoader != null;
		String path = CLASSES_PACKAGE.replace('.', '/');
		try {
			Enumeration<URL> resources = classLoader.getResources(path);
			List<File> dirs = new ArrayList<File>();
			while (resources.hasMoreElements()) {
				URL resource = resources.nextElement();
				dirs.add(new File(resource.getFile()));
			}
			ArrayList<Class<? extends Object>> classes = new ArrayList<Class<? extends Object>>();
			for (File directory : dirs) {
				try {
					classes.add(Class
							.forName(CLASSES_PACKAGE + '.' + directory.getName().substring(0, directory.getName().length() - 6)));
				} catch (ClassNotFoundException e) {
					
					e.printStackTrace();
				}
			}
			List<Class<? extends Shape>> supportedShapes = new ArrayList<Class<? extends Shape>>();
			for (@SuppressWarnings("rawtypes") Class candidate : classes) {
				Class[] implementedInterfaces = candidate.getInterfaces();
				if (implementedInterfaces.length != 0) {
					if (implementedInterfaces[0].toString().equals("interface " + Shape.class.getName())) {
						supportedShapes.add(candidate);
					}
				}
			}
			return supportedShapes;
		} catch (IOException ioE) {
			System.err.println("Failed to get supported shapes.");
			ioE.printStackTrace();
			return null;
		}
	}

	@Override
	public void undo() {
		if (actionsPerformed.size() == 0) {
			return;
		} else {
			final ICommand action = actionsPerformed.get(actionsPerformed.size() - 1);
			actionsPerformed.remove(actionsPerformed.size() - 1);
			action.unexecute();
			addCommand(actionsUNPerformed, action);
			if (action.getCommand().equals("draw")) {
				shapes.remove(action.getReceiver(null));
			} else if (action.getCommand().equals("remove")) {
				shapes.add(action.getReceiver(null));
			} else if (action.getCommand().equals("update")) {
				shapes.add(action.getReceiver("old shape"));
				shapes.remove(action.getReceiver("new shape"));
			}
		}
	}

	@Override
	public void redo() {
		if (actionsUNPerformed.size() == 0) {
			return;
		} else {
			final ICommand action = actionsUNPerformed.get(actionsUNPerformed.size() - 1);
			actionsUNPerformed.remove(actionsUNPerformed.size() - 1);
			action.execute();
			addCommand(actionsPerformed, action);
			if (action.getCommand().equals("draw")) {
				shapes.add(action.getReceiver(null));

			} else if (action.getCommand().equals("remove")) {
				shapes.remove(action.getReceiver(null));

			} else if (action.getCommand().equals("update")) {
				shapes.remove(action.getReceiver("old shape"));
				shapes.add(action.getReceiver("new shape"));
			}
		}
	}

	@Override
	public void save(final String path) {
		if (path == null || path.length() < 5 || !path.contains(".")) {
			throw new RuntimeException("Invalid path.");
		}
		String extension = path.substring(path.lastIndexOf('.'));
		if (extension.equals(".xml")) {
			// TODO: xml saving.
		} else if (extension.equals(".json")) {
			JsonArrayBuilder arrBuilder = Json.createArrayBuilder();// For
																	// shapes
			for (Shape sh : shapes) {
				arrBuilder.add(((Stroke) sh).buildJsonArray());
			}
			try {
				FileWriter writer = new FileWriter(path);
				JsonWriter jw = Json.createWriter(writer);
				jw.writeArray(arrBuilder.build());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			throw new RuntimeException("Invalid extension.");
		}

	}

	@Override
	public void load(final String path) {
		// TODO Auto-generated method stub
	}

	private void addCommand(ArrayList<ICommand> array, ICommand command) {
		if (array.size() == MAX_SIZE) {
			array.remove(0);
		}
		array.add(command);
	}

}
