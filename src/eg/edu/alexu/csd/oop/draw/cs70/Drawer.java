package eg.edu.alexu.csd.oop.draw.cs70;

import java.net.URL;
import java.net.URLClassLoader;
import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.xml.parsers.*;
import javax.json.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import eg.edu.alexu.csd.oop.draw.ICommand;
import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.Stroke;
import gui.ShapeController;
import javafx.scene.layout.Pane;

public class Drawer implements DrawingEngine {
	/**
	 * Maximum size of saved commands.
	 */
	private static final int MAX_SIZE = 20;

	/**
	 * List of shapes.
	 */
	private ArrayList<Shape> shapes = new ArrayList<>();

	/**
	 * List of actions performed for undo.
	 */
	private final ArrayList<ICommand> actionsPerformed = new ArrayList<>();
	/**
	 * List of actions UNPerformed for redo.
	 */
	private final ArrayList<ICommand> actionsUNPerformed = new ArrayList<>();
	/**
	 * Keeps track of whether this drawing engine has found a plugin.
	 */
	private boolean pluginFound = false;
	/**
	 * Plugin shape found. Or null if hasn't been found yet.
	 */
	private Class<? extends Shape> pluginShape;

	@Override
	public void refresh(Pane canvas) {
		for (Shape sh : shapes) {
			sh.draw(canvas);
		}
	}

	@Override
	public void addShape(Pane drawingPane, final ShapeController sc) {
		shapes.add(sc.getShape());
		final DrawCommand draw = new DrawCommand(drawingPane, sc);
		draw.execute();
		addCommand(actionsPerformed, draw);
		actionsUNPerformed.clear();
	}

	@Override
	public void removeShape(Pane drawingPane, final ShapeController sc) {
		shapes.remove(sc.getShape());
		final RemoveCommand remove = new RemoveCommand(drawingPane, sc);
		remove.execute();
		addCommand(actionsPerformed, remove);
	}

	@Override
	public void updateShape(Pane drawingPane, final ShapeController oldShape, final ShapeController newShape) {
		shapes.remove(oldShape.getShape());
		shapes.add(newShape.getShape());
		UpdateCommand update = new UpdateCommand(drawingPane, oldShape, newShape);
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

	@SuppressWarnings("unchecked")
	public Class<? extends Shape> findPlugin(String path) {
		try {
			JarFile jarFile = new JarFile(path);
			Enumeration<JarEntry> en = jarFile.entries();
			URL[] urls = { new URL("jar:file:" + path + "!/") };
			URLClassLoader cl = URLClassLoader.newInstance(urls);
			Class<? extends eg.edu.alexu.csd.oop.draw.Shape> c = null;
			while (en.hasMoreElements()) {
				JarEntry je = en.nextElement();
				if (je.isDirectory() || !je.getName().endsWith(".class")) {
					continue;
				}
				jarFile.close();
				// -6 because of .class
				String className = je.getName().substring(0, je.getName().length() - 6);
				className = className.replace('/', '.');
				c = (Class<? extends eg.edu.alexu.csd.oop.draw.Shape>) cl.loadClass(className);
			}
			pluginFound = true;
			pluginShape = c;
			return c;
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Class<? extends Shape>> getSupportedShapes() {
		List<Class<? extends Shape>> result = new ArrayList<>();
		Class<? extends Shape> shape;
		try {
			shape = (Class) Class.forName("eg.edu.alexu.csd.oop.draw.cs70.Circle");
			result.add(shape);
			shape = (Class) Class.forName("eg.edu.alexu.csd.oop.draw.cs70.Ellipse");
			result.add(shape);
			shape = (Class) Class.forName("eg.edu.alexu.csd.oop.draw.cs70.LineSegment");
			result.add(shape);
			shape = (Class) Class.forName("eg.edu.alexu.csd.oop.draw.cs70.Rectangle");
			result.add(shape);
			shape = (Class) Class.forName("eg.edu.alexu.csd.oop.draw.cs70.Square");
			result.add(shape);
			shape = (Class) Class.forName("eg.edu.alexu.csd.oop.draw.cs70.Triangle");
			result.add(shape);
			if (pluginFound) {
				result.add(pluginShape);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return result;
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
	public void save(final String path) throws Exception {
		if (path == null || !path.contains(".")) {
			throw new RuntimeException("Invalid path.");
		}
		final String extension = path.substring(path.lastIndexOf('.'));
		if (extension.equals(".xml")) {
			Document doc;
			final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			try {
				final DocumentBuilder db = dbf.newDocumentBuilder();
				// create instance of DOM
				doc = db.newDocument();
				final Element root = doc.createElement("Shapes");
				for (final Shape sh : shapes) {
					root.appendChild(((Stroke) sh).buildXMLElement(doc));
				}
				try {
					final Transformer tr = TransformerFactory.newInstance().newTransformer();
					tr.setOutputProperty(OutputKeys.INDENT, "yes");
					tr.setOutputProperty(OutputKeys.METHOD, "xml");
					tr.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
					tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "roles.dtd");
					tr.setOutputProperty("{http://xml.apache" + ".org/xslt}indent-amount", "4");
					// send DOM to file
					tr.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(path)));

				} catch (final TransformerException te) {
					System.out.println(te.getMessage());
				} catch (final IOException ioe) {
					System.out.println(ioe.getMessage());
				}
			} catch (final ParserConfigurationException e) {
				e.printStackTrace();
			}
		} else if (extension.equals("json")) {
			JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
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
	public void load(final String path) throws Exception {
		if (path == null || path.length() < 5 || !path.contains(".")) {
			throw new RuntimeException("Invalid path.");
		}
		String extension = path.substring(path.lastIndexOf('.'));
		if (extension.equals(".xml")) {
			Document dom;
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			try {
				DocumentBuilder db = dbf.newDocumentBuilder();
				dom = db.parse(path);
				clearDS();
				NodeList nl = dom.getFirstChild().getChildNodes();
				for (int i = 0; i < nl.getLength(); i++) {
					Node n = nl.item(i);
					shapes.add(Stroke.parseXMLShape(n));
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (extension.equals("json")) {
			FileReader reader = new FileReader(path);
			JsonReader jr = Json.createReader(reader);
			JsonArray shapesArray = jr.readArray();
			clearDS();
			if (shapesArray != null) {
				for (JsonValue jO : shapesArray) {
					JsonArray jShape = (JsonArray) jO;
					Shape readShape = Stroke.readJsonArray(jShape);
					shapes.add(readShape);
				}
			}
		} else {
			throw new RuntimeException("Invalid extension.");
		}
	}

	private void addCommand(ArrayList<ICommand> array, ICommand command) {
		if (array.size() == MAX_SIZE) {
			array.remove(0);
		}
		array.add(command);
	}

	public boolean isPluginFound() {
		return pluginFound;
	}

	private void clearDS() {
		actionsPerformed.clear();
		actionsUNPerformed.clear();
		shapes.clear();
	}

}
