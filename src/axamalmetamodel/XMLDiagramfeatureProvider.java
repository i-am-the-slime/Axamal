package axamalmetamodel;

import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IDirectEditingFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.IDirectEditingContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.features.DefaultFeatureProvider;

import xml.Node;

public class XMLDiagramfeatureProvider extends DefaultFeatureProvider implements
		IFeatureProvider {

	public XMLDiagramfeatureProvider(IDiagramTypeProvider dtp) {
		super(dtp);
		
	}
	
	@Override
	public ICreateFeature[] getCreateFeatures() {
		// TODO Auto-generated method stub
		return new ICreateFeature[] {new CreateNodeFeature(this)};
	}
	
	@Override
	public IAddFeature getAddFeature(IAddContext context) {
		if (context.getNewObject() instanceof Node) {
			return new AddNodeFeature(this);
		}
		return super.getAddFeature(context);
	}
	
	@Override
    public IDirectEditingFeature getDirectEditingFeature(
        IDirectEditingContext context) {
        PictogramElement pe = context.getPictogramElement();
        Object bo = getBusinessObjectForPictogramElement(pe);
        if (bo instanceof Node) {
            return new DirectEditNodeFeature(this);
        }
        else {
        	System.err.println(bo);
        }
        return super.getDirectEditingFeature(context);
    }

}
