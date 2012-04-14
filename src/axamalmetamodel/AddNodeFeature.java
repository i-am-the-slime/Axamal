package axamalmetamodel;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.IDirectEditingInfo;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.impl.AbstractAddShapeFeature;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
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

	public static final int STANDARD_NODE_WIDTH = 300;
	public static final int NODE_HEIGHT = 30;
	public static final int BORDER_SIZE = 5;
	public static final int TEXT_SHAPE_HEIGHT = 20;
	
	@Override
	public PictogramElement add(IAddContext context) {
		ICreateService createService = Graphiti.getCreateService();
		IGaService layoutService = Graphiti.getGaService();
		Node theNode = (Node) context.getNewObject();
		
		ContainerShape cont = context.getTargetContainer();
		int nodeWidth;
		int nodeHeight;
		int nodeX, nodeY;
		String nodeName = null;
		if(cont==getDiagram()){ //Outermost node
			nodeHeight = NODE_HEIGHT + 2*BORDER_SIZE;
			nodeWidth = STANDARD_NODE_WIDTH;
			nodeName = "NewNode";
			nodeX = 5;
			nodeY = 5;
		} else {
			// Containing node instance
			Node containerNode = (Node) getBusinessObjectForPictogramElement(cont);
			GraphicsAlgorithm containingRect = cont.getGraphicsAlgorithm();
			
			// Set size and name depending on parent
			nodeHeight = NODE_HEIGHT;
			nodeWidth = cont.getGraphicsAlgorithm().getWidth()-2*BORDER_SIZE-2;
			nodeName = containerNode.getName()+"'s_Child";
			nodeX = BORDER_SIZE;
			nodeY = (NODE_HEIGHT+BORDER_SIZE)*(1+getNumberOfAllSubnodes(containerNode));
			// Add it as a sub-node
			containerNode.getSubnodes().add(theNode);
			
			// Make the containing node bigger such that it can contain the new one
			GraphicsAlgorithm c = containingRect;
			while (c!=null) {
				c.setHeight(c.getHeight()+NODE_HEIGHT+BORDER_SIZE);
				c = c.getParentGraphicsAlgorithm();
			}
		}
		
		// Add to the domain model.
		theNode.setName(nodeName);
		
		
		//Setup the new node
		try{
			
			ContainerShape outerContainerShape = createService.createContainerShape(context.getTargetContainer(), true);
			RoundedRectangle boardRectangle = createService.createRoundedRectangle(outerContainerShape, 15, 15);
			boardRectangle.setStyle(StyleUtil.getStyleForNode(getDiagram()));
			boardRectangle.setLineWidth(1);
			layoutService.setLocationAndSize(boardRectangle, nodeX, nodeY, nodeWidth, nodeHeight);
			
			link(outerContainerShape, theNode);
			
	        // SHAPE WITH TEXT
	        {
	            // create shape for text
	            Shape shape = createService.createShape(outerContainerShape, false);
	            // create and set text graphics algorithm
	            Text text = layoutService.createText(getDiagram(), shape, theNode.getName(), "Arial", 13);
	           	text.setStyle(StyleUtil.getStyleForNodeText(getDiagram()));
	            text.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
	            text.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
	            layoutService.setLocationAndSize(text, BORDER_SIZE, BORDER_SIZE, outerContainerShape.getGraphicsAlgorithm().getWidth()-2*BORDER_SIZE, TEXT_SHAPE_HEIGHT);
	            
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
	            layoutPictogramElement(shape);
	        }
			return outerContainerShape;
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	
	}
	
	private int getNumberOfAllSubnodes(Node n) {
		int i = n.getSubnodes().size();
		for( Node subNode : (EList<Node>)n.getSubnodes()) {
			i += getNumberOfAllSubnodes(subNode);
		}
		return i;
	}

}
