package axamalmetamodel;

import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.IDirectEditingInfo;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.impl.AbstractAddShapeFeature;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Polyline;
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
import org.eclipse.graphiti.util.IColorConstant;

import xml.Node;

public class AddAttribEditorFeature extends AbstractAddShapeFeature implements
IAddFeature {

	public AddAttribEditorFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canAdd(IAddContext context) {
		return context.getTargetContainer() instanceof Diagram;
	}

	public static final int STANDARD_NODE_WIDTH = 300;
	public static final int NODE_HEIGHT = 30;
	public static final int BORDER_SIZE = 5;
	public static final int TEXT_SHAPE_HEIGHT = 20;

	@Override
	public PictogramElement add(IAddContext context) {
		ICreateService createService = Graphiti.getCreateService();
		IGaService layoutService = Graphiti.getGaService();
		AttributeEditor theaAttribEditor = (AttributeEditor) context.getNewObject();
/*
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
		}*/

		//Setup the new node
		try{
			ContainerShape outerContainerShape = createService.createContainerShape(context.getTargetContainer(), true);
			RoundedRectangle boardRectangle = createService.createRoundedRectangle(outerContainerShape, 15, 15);
//			boardRectangle.setStyle(StyleUtil.getStyleForNode(getDiagram()));
			boardRectangle.setForeground(manageColor(IColorConstant.DARK_GRAY));
            boardRectangle.setBackground(manageColor(IColorConstant.WHITE));

			boardRectangle.setLineWidth(1);
			layoutService.setLocationAndSize(boardRectangle, context.getX(), context.getY(), 200, 300);
			
			link(outerContainerShape, theaAttribEditor);
			/*
			// SHAPE WITH LINE
	        {
	            // create shape for line
	            Shape shape = createService.createShape(outerContainerShape, false);
	            // create and set graphics algorithm
	        	Polyline polyline = createService.createPolyline(outerContainerShape,new int[] { 0, 20, 100, 20 });
//	            Polyline polyline = layoutService.createPolyline(shape, new int[] { 0, 20, boardRectangle.getWidth(), 20 });
	            polyline.setForeground(manageColor(IColorConstant.BLACK));

	            polyline.setLineWidth(2);
	        }*/
			// SHAPE WITH TEXT
			{
				// create shape for text
				Shape shape = createService.createShape(outerContainerShape, false);
				// create and set text graphics algorithm
				Text text = layoutService.createText(getDiagram(), shape, "Attribute Editor", "Arial", 13);
//				text.setStyle(StyleUtil.getStyleForNodeText(getDiagram()));
				text.setFont(Graphiti.getGaService().manageFont(getDiagram(), "Arial", 13, true, true));
				text.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
				text.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
				layoutService.setLocationAndSize(text, BORDER_SIZE, BORDER_SIZE, 200, 20);
			}
			return outerContainerShape;
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;

	}

}
