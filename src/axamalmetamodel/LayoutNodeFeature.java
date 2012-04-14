package axamalmetamodel;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.datatypes.IDimension;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.features.impl.AbstractLayoutFeature;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.RoundedRectangle;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;

import xml.Node;

public class LayoutNodeFeature extends AbstractLayoutFeature {

	private static final int MIN_HEIGHT = 30;
    private static final int MIN_WIDTH = 50;

	
	public LayoutNodeFeature(IFeatureProvider fp) {
		super(fp);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canLayout(ILayoutContext context) {
		PictogramElement pe = context.getPictogramElement();
		if ( !(pe instanceof ContainerShape) ){
			return false;
		}
		EList<EObject> businessObjects = pe.getLink().getBusinessObjects();
	       return businessObjects.size() == 1  && businessObjects.get(0) instanceof Node;
	}

	@Override
	public boolean layout(ILayoutContext context) {
        boolean anythingChanged = false;
        ContainerShape containerShape = (ContainerShape) context.getPictogramElement();
        GraphicsAlgorithm containerGa = containerShape.getGraphicsAlgorithm();
 
        // height
        if (containerGa.getHeight() < MIN_HEIGHT) {
            containerGa.setHeight(MIN_HEIGHT);
            anythingChanged = true;
        }
 
        // width
        if (containerGa.getWidth() < MIN_WIDTH) {
            containerGa.setWidth(MIN_WIDTH);
            anythingChanged = true;
        }
 
        int containerWidth = containerGa.getWidth();
        
        for (Shape shape : containerShape.getChildren()){
            GraphicsAlgorithm graphicsAlgorithm = shape.getGraphicsAlgorithm();
            IGaService gaService = Graphiti.getGaService();
            IDimension size = gaService.calculateSize(graphicsAlgorithm);
            if (containerWidth != size.getWidth()) {
            	//Properly align text.
            	if (graphicsAlgorithm instanceof Text){
	                gaService.setWidth(graphicsAlgorithm, containerWidth);
	                anythingChanged = true;
            	} //Properly align subnodes
            	else if (graphicsAlgorithm instanceof RoundedRectangle) {
            		gaService.setWidth(graphicsAlgorithm, containerWidth-AddNodeFeature.BORDER_SIZE*2);
            		anythingChanged = true;
            	}
                
            }
        }
        return anythingChanged;
    }

}
