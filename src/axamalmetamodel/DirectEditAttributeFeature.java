package axamalmetamodel;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IDirectEditingContext;
import org.eclipse.graphiti.features.impl.AbstractDirectEditingFeature;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;

import xml.Attribute;
import xml.Node;

public class DirectEditAttributeFeature extends AbstractDirectEditingFeature {

	public DirectEditAttributeFeature(IFeatureProvider fp) {
		super(fp);
		// TODO Auto-generated constructor stub
	}

	public int getEditingType() {
        // there are several possible editor-types supported:
        // text-field, checkbox, color-chooser, combobox, ...
        return TYPE_TEXT;
    }
 
    @Override
    public boolean canDirectEdit(IDirectEditingContext context) {
        PictogramElement pe = context.getPictogramElement();
        GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
        // support direct editing, if it is a EClass, and the user clicked
        // directly on the text and not somewhere else in the rectangle
        if (ga instanceof Text) {
            return true;
        }
        // direct editing not supported in all other cases
        return true;
    }
 
    public String getInitialValue(IDirectEditingContext context) {
        // return the current name of the EClass
        PictogramElement pe = context.getPictogramElement();
        Node node = (Node) getBusinessObjectForPictogramElement(pe);
        return node.getName();
    }
 
    @Override
    public String checkValueValid(String value, IDirectEditingContext context) {
    	if (value.length() < 1)
            return "Please enter any text as an attribute (name=value).";
        if (value.contains(" "))
            return "Spaces are not allowed in attriubtes.";
        if (value.contains("\n"))
            return "Line breaks are not allowed in attributes.";
        if (!value.contains("="))
            return "Please separate name and value with an equals sign (name=value)";
 
        // null means, that the value is valid
        return null;
    }
 
    public void setValue(String value, IDirectEditingContext context) {
        // set the new name for the MOF class
        PictogramElement pe = context.getPictogramElement();
        Attribute attribute = (Attribute) getBusinessObjectForPictogramElement(pe);
        // TODO: This is not supposed to be necessary
        ((Text)pe.getGraphicsAlgorithm()).setValue(value);
        String[] array = value.split(":");
        attribute.setName(array[0]);
        attribute.setValue(array[1]);
        
        
        // Explicitly update the shape to display the new value in the diagram
        // Note, that this might not be necessary in future versions of Graphiti
        // (currently in discussion)
 
        // we know, that pe is the Shape of the Text, so its container is the
        // main shape of the EClass
        updatePictogramElement(((Shape) pe).getContainer());
    }

}
