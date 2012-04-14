package axamalmetamodel;

import javax.swing.JOptionPane;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.impl.AbstractAddShapeFeature;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

import xml.Attribute;
import xml.Node;

public class AddAttributeFeature extends AbstractAddShapeFeature implements
IAddFeature {

	public AddAttributeFeature(IFeatureProvider fp) {
		super(fp);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canAdd(IAddContext context) {
		if (context.getTargetContainer() instanceof Diagram)
		{
			return true;
		}
		for (EObject x : context.getTargetContainer().getLink().getBusinessObjects()) {
			if (x instanceof Node) {
				return true;
			}
		}	
		return false;
	}

	public static final int STANDARD_NODE_WIDTH = 300;
	public static final int NODE_HEIGHT = 30;
	public static final int BORDER_SIZE = 5;
	public static final int TEXT_SHAPE_HEIGHT = 20;

	@Override
	public PictogramElement add(IAddContext context) {
		String s = (String)JOptionPane.showInputDialog("Enter new Attribute's name and value:","name = value");
		String attributeNameString = s.substring(0, s.indexOf("="));
		String attributeValueString = s.substring(s.indexOf("=")+1).trim();
		//TODO: Have window that sets both
		Attribute attribute = (Attribute) context.getNewObject();
		attribute.setName(attributeNameString);
		attribute.setValue(attributeValueString);
		return null;
	}

}
