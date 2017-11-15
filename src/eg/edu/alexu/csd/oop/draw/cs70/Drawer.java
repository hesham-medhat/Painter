package eg.edu.alexu.csd.oop.draw.cs70;

import java.awt.Graphics;
//import java.io.File;
//import java.net.URL;
//import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.*;
//import javax.json.*;
//import javax.xml.transform.*;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
//import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import eg.edu.alexu.csd.oop.draw.ICommand;
import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.Stroke;
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
	private final ArrayList<ICommand> actionsPerformed =
			new ArrayList<>();
	/**
	 * List of actions UNPerformed for redo.
	 */
	private final ArrayList<ICommand> actionsUNPerformed =
			new ArrayList<>();


	@Override
	public void refresh(final Graphics canvas) {

	}

	@Override
	public void addShape(final Shape shape, Pane drawingPane) {
		shapes.add(shape);
		final DrawCommand draw = new DrawCommand(shape, drawingPane);
		draw.execute();
		addCommand(actionsPerformed, draw);
		actionsUNPerformed.clear();
	}

	@Override
	public void removeShape(final Shape shape, Pane drawingPane) {
		shapes.remove(shape);
		final RemoveCommand remove = new RemoveCommand(shape);
		remove.execute();
		addCommand(actionsPerformed, remove);
	}

	@Override
	public void updateShape(final Shape oldShape, final Shape newShape, Pane drawingPane) {
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
		List<Class<? extends Shape>> result = new ArrayList<>();
		Class<? extends Shape> shape ;
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
	public void save(final String path) {
/*		if (path == null || path.length() < 5 || !path.contains(".")) {
			throw new RuntimeException("Invalid path.");
		}
		String extension = path.substring(path.lastIndexOf('.'));
		if (extension.equals(".xml")) {
			Document doc;
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			try {
				DocumentBuilder db = dbf.newDocumentBuilder();
				// create instance of DOM
			    doc = db.newDocument();
			    Element root = doc.createElement("Shapes");
			    for (Shape sh : shapes) {
			    	root.appendChild(((Stroke) sh).buildXMLElement(doc));
			    }
			    try {
		            Transformer tr = TransformerFactory.newInstance().newTransformer();
		            tr.setOutputProperty(OutputKeys.INDENT, "yes");
		            tr.setOutputProperty(OutputKeys.METHOD, "xml");
		            tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		            tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "roles.dtd");
		            tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

		            // send DOM to file
		            tr.transform(new DOMSource(doc),
		                                 new StreamResult(new FileOutputStream(path)));

		        } catch (TransformerException te) {
		            System.out.println(te.getMessage());
		        } catch (IOException ioe) {
		            System.out.println(ioe.getMessage());
		        }
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}

			
		} else if (extension.equals("json")) {
//			JsonArrayBuilder arrBuilder = Json.createArrayBuilder();//For shapes
//			for (Shape sh : shapes) {
//				arrBuilder.add(((Stroke) sh).buildJsonArray());
//			}
//			try {
//				FileWriter writer = new FileWriter(path);
//				JsonWriter jw = Json.createWriter(writer);
//				jw.writeArray(arrBuilder.build());
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
		} else {
			throw new RuntimeException("Invalid extension.");
		}
*/
	}

	@Override
	public void load(final String path) {
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
				 shapes = new ArrayList<Shape>();
				 NodeList nl = dom.getFirstChild().getChildNodes();
				 for (int i = 0; i < nl.getLength(); i++) {
					 Node n = nl.item(i);
					 shapes.add(Stroke.parseXMLShape(n));
				 }
				 
			 } catch (Exception e) {
				 e.printStackTrace();
			 }
		} else if (extension.equals("json")) {
			
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

}
