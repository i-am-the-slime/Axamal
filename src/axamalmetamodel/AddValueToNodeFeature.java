package axamalmetamodel;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

import xml.Node;

public class AddValueToNodeFeature extends AbstractCustomFeature implements	ICustomFeature {

	public AddValueToNodeFeature(IFeatureProvider fp) {
		super(fp);
	}
	
	@Override
    public String getName() {
        return "Add text content";
    }
 
    @Override
    public String getDescription() {
        return "Adds the text content inside the tag";
    }
 
    @Override
    public boolean canExecute(ICustomContext context) {
		return getBusinessObjectForPictogramElement(context.getInnerPictogramElement()) instanceof Node;
    }
 
    @Override
    public void execute(ICustomContext context)
    {
    	Node theNode = (Node) getBusinessObjectForPictogramElement(context.getInnerPictogramElement());
		String s = (String)JOptionPane.showInputDialog("Enter the content text inside the tag:",theNode.getValue());
		theNode.setValue(s);
    }
}
