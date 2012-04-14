package axamalmetamodel;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;

import javax.swing.JFileChooser;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

import xml.Node;

public class WriteToFileFeature extends AbstractCustomFeature {

	private boolean hasDoneChanges = false;
	
	public WriteToFileFeature(IFeatureProvider fp) {
		super(fp);
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public String getName() {
        return "Generate XML";
    }
 
    @Override
    public String getDescription() {
        return "Generates XML from the model.";
    }
 
    @Override
    public boolean canExecute(ICustomContext context) {
    	//This is available from anywhere
        return true;
    }
 
    @Override
    public void execute(ICustomContext context) {
        PictogramElement[] pes = context.getPictogramElements();
        if (pes != null && pes.length == 1) {
            Object bo = getBusinessObjectForPictogramElement(pes[0]);
            if (bo instanceof Node) {
                Node node = (Node) bo;
                JFileChooser fileChooser = new JFileChooser();
                int ret = fileChooser.showOpenDialog(null);
                if (ret == JFileChooser.APPROVE_OPTION) {
                	File f = fileChooser.getSelectedFile();
                	WriteNodesToFile.write(f, node);
                }
                
            }
        }
    }
 
    @Override
    public boolean hasDoneChanges() {
           return this.hasDoneChanges;
    }
}
