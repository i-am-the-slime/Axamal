package axamalmetamodel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.emf.common.util.EList;

import xml.Attribute;
import xml.Node;

public class WriteNodesToFile {
	public static void write(File f, Node container) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(f));
			String bigString = recursion(container);
			out.write(bigString);
			out.close();
		}
		catch (IOException e)
		{
			System.out.println("Exception " + e.getLocalizedMessage());		
		}
	}
	
	public static String recursion(Node node) {
		String bigString = "<" + node.getName() + " ";
		// add attributes
		for(Attribute a : (EList<Attribute>)node.getHasAttr()) {
			bigString += " " + a.getName() + "=\"" + a.getValue()+"\"";
		}
		bigString += ">\n";
		try {
			bigString += node.getValue() + "\n";
		} catch (NullPointerException e) {}
		
		for (Node n : (EList<Node>)node.getSubnodes()) {
			bigString += recursion(n);
		}
		bigString += "</" + node.getName() + ">\n";
		return bigString;
	}
}
