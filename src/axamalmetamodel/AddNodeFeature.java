package axamalmetamodel;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.IDirectEditingInfo;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.impl.AbstractAddShapeFeature;
import org.eclipse.graphiti.mm.algorithms.RoundedRectangle;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.styles.Orientation;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.ICreateService;
import org.eclipse.graphiti.services.IGaService;

import xml.Node;

public class AddNodeFeature extends AbstractAddShapeFeature implements
		IAddFeature {

	public AddNodeFeature(IFeatureProvider fp) {
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

	private static final int STANDARD_NODE_WIDTH = 100;
	private static final int BORDER_SIZE = 5;
	@Override
	public PictogramElement add(IAddContext context) {
		ICreateService createService = Graphiti.getCreateService();
		IGaService layoutService = Graphiti.getGaService();
		Node theNode = (Node) context.getNewObject();
		theNode.setName("newNode");
		
		ContainerShape cont = context.getTargetContainer();
		if(cont==getDiagram()){
			//This is the outermost node
			ContainerShape outerContainerShape = createService.createContainerShape(context.getTargetContainer(), true);
			
			RoundedRectangle boardRectangle = createService.createRoundedRectangle(outerContainerShape, 15, 15);
			boardRectangle.setStyle(StyleUtil.getStyleForEClass(getDiagram()));
			layoutService.setLocationAndSize(boardRectangle, context.getX(), context.getY(), STANDARD_NODE_WIDTH, 100);
			
			link(outerContainerShape, theNode);
			
	        // SHAPE WITH TEXT
	        {
	            // create shape for text
	            Shape shape = createService.createShape(outerContainerShape, false);
	            // create and set text graphics algorithm
	            Text text = layoutService.createText(getDiagram(), shape, theNode.getName(), "Arial", 13);
	            text.setStyle(StyleUtil.getStyleForEClassText(getDiagram()));
	            text.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
	            layoutService.setLocationAndSize(text, 5, 5, outerContainerShape.getGraphicsAlgorithm().getWidth()-2*BORDER_SIZE, 20);
	            link(shape, theNode);
	            
	            // provide information to support direct-editing directly
	            // after object creation (must be activated additionally)
	            IDirectEditingInfo directEditingInfo =
	                getFeatureProvider().getDirectEditingInfo();
	            // set container shape for direct editing after object creation
	            directEditingInfo.setMainPictogramElement(outerContainerShape);
	            // set shape and graphics algorithm where the editor for
	            // direct editing shall be opened after object creation
	            directEditingInfo.setPictogramElement(shape);
	            directEditingInfo.setGraphicsAlgorithm(text);
	        }
	       

	        
	        return outerContainerShape;
		} else {
			//This is an inner node
			try{
				Node containerNode = (Node) getBusinessObjectForPictogramElement(cont);
				ContainerShape outerContainerShape = createService.createContainerShape(context.getTargetContainer(), true);
				RoundedRectangle boardRectangle = createService.createRoundedRectangle(outerContainerShape, 15, 15);
				boardRectangle.setStyle(StyleUtil.getStyleForEClass(getDiagram()));
				layoutService.setLocationAndSize(boardRectangle, context.getX(), context.getY(), 100, 100);
				//Default name for new node
				theNode.setName(containerNode.getName()+"'s_Child");
				// Add it as a sub-node
				containerNode.addSubNode(theNode);
				
				link(outerContainerShape, theNode);
				
		        // SHAPE WITH TEXT
		        {
		            // create shape for text
		            Shape shape = createService.createShape(outerContainerShape, false);
		            // create and set text graphics algorithm
		            Text text = layoutService.createText(getDiagram(), shape, theNode.getName(), "Arial", 13);
		           	text.setStyle(StyleUtil.getStyleForEClassText(getDiagram()));
		            text.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		            text.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		            layoutService.setLocationAndSize(text, BORDER_SIZE, BORDER_SIZE, outerContainerShape.getGraphicsAlgorithm().getWidth()-2*BORDER_SIZE, 20);
		            
		            link(shape, theNode);
		            
		            // provide information to support direct-editing directly
		            // after object creation (must be activated additionally)
		            IDirectEditingInfo directEditingInfo = getFeatureProvider().getDirectEditingInfo();
		            // set container shape for direct editing after object creation
		            directEditingInfo.setMainPictogramElement(outerContainerShape);
		            // set shape and graphics algorithm where the editor for
		            // direct editing shall be opened after object creation
		            directEditingInfo.setPictogramElement(shape);
		            directEditingInfo.setGraphicsAlgorithm(text);
		          
		        }
				return outerContainerShape;
			} catch (Exception e){
				e.printStackTrace();
			}
			return null;
		}
		
		
    
	}

}
