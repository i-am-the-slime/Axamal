package axamalmetamodel;

import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IDirectEditingFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.ILayoutFeature;
import org.eclipse.graphiti.features.IMoveShapeFeature;
import org.eclipse.graphiti.features.IRemoveFeature;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.context.IDirectEditingContext;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.features.context.IMoveShapeContext;
import org.eclipse.graphiti.features.context.IRemoveContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.features.DefaultFeatureProvider;

import xml.Attribute;
import xml.Node;

public class XMLDiagramfeatureProvider extends DefaultFeatureProvider implements
		IFeatureProvider {

	private AttributeEditor theAttributeEditor = null;

	public XMLDiagramfeatureProvider(IDiagramTypeProvider dtp) {
		super(dtp);
		
	}
	
	@Override
	public ICreateFeature[] getCreateFeatures() {
		// TODO Auto-generated method stub
		return new ICreateFeature[] {new CreateNodeFeature(this) , new CreateAttributeFeature(this) , new CreateAttribEditorFeature(this)};
	}
	
	@Override
	public IAddFeature getAddFeature(IAddContext context) {
		if (context.getNewObject() instanceof Node) {
			return new AddNodeFeature(this);
		}
		if (context.getNewObject() instanceof AttributeEditor) {
			return new AddAttribEditorFeature(this);
		}
		if (context.getNewObject() instanceof Attribute) {
			return new AddAttributeFeature(this);
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
        } else if (bo instanceof Attribute) {
        	return new DirectEditAttributeFeature(this);
        }
        else {
        	System.err.println(bo);
        }
        return super.getDirectEditingFeature(context);
    }

	@Override
	public ILayoutFeature getLayoutFeature(ILayoutContext context) {
	    PictogramElement pictogramElement = context.getPictogramElement();
	    Object bo = getBusinessObjectForPictogramElement(pictogramElement);
	    if (bo instanceof Node) {
	        return new LayoutNodeFeature(this);
	    }
	    return super.getLayoutFeature(context);
	}
	
	@Override
	public IMoveShapeFeature getMoveShapeFeature(IMoveShapeContext context) {
	    Object bo = getBusinessObjectForPictogramElement(context.getShape());
	    if (bo instanceof Node) {
	        return new MoveNodeFeature(this);
	    }
	    return super.getMoveShapeFeature(context);
	 }

	@Override
	public IRemoveFeature getRemoveFeature(IRemoveContext context) {
		PictogramElement pictoElement = context.getPictogramElement();
//		if ((pictoElement instanceof ContainerShapeImpl) && (getBusinessObjectForPictogramElement(pictoElement) == null)) {
		if (getBusinessObjectForPictogramElement(pictoElement) instanceof AttributeEditor)
		{
			return new RemoveAttribEditorFeature(this);
		}
		return super.getRemoveFeature(context);
	}
	
	@Override
	public ICustomFeature[] getCustomFeatures(ICustomContext context) {
	    return new ICustomFeature[] { new WriteToFileFeature(this) , new AddValueToNodeFeature(this) };
	}

	public AttributeEditor getAttributeEditor() {
		return theAttributeEditor;
	}

	public void setAttributeEditor(AttributeEditor theAttributeEditor) {
		this.theAttributeEditor = theAttributeEditor;
	}
	
}
