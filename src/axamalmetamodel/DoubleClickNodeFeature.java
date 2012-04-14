package axamalmetamodel;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.styles.Orientation;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.ICreateService;
import org.eclipse.graphiti.services.IGaService;

import xml.Attribute;
import xml.Node;
import xml.XmlFactory;

public class DoubleClickNodeFeature extends AbstractCustomFeature implements ICustomFeature {

	public DoubleClickNodeFeature(IFeatureProvider fp)
	{
		super(fp);
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		return getBusinessObjectForPictogramElement(context.getInnerPictogramElement()) instanceof Node;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute(ICustomContext context)
	{
		AttributeEditor attrEditor = ((XMLDiagramfeatureProvider)getFeatureProvider()) .getAttributeEditor();
		if ( attrEditor != null )
		{
			ICreateService createService = Graphiti.getCreateService();
			IGaService layoutService = Graphiti.getGaService();
			
			List<PictogramElement> p = Graphiti.getLinkService().getPictogramElements(getDiagram(), attrEditor);
			ContainerShape outerContainerShape = (ContainerShape)(p.get(0));
			EList<Shape> contents = outerContainerShape.getChildren();
			// remove all previous attribute fields
			Shape title = contents.get(0);
			contents.clear();
			contents.add(title);
			
			Node theNode = (Node) getBusinessObjectForPictogramElement(context.getInnerPictogramElement());
			@SuppressWarnings("unchecked")
			EList<Attribute> attribs = theNode.getHasAttr();
			
/*			// TEST
			Attribute newAttr = XmlFactory.eINSTANCE.createAttribute();
			newAttr.setName("Asghar");
			newAttr.setValue("Gholam");
			theNode.getHasAttr().add(newAttr);
			Attribute newAttr1 = XmlFactory.eINSTANCE.createAttribute();
			newAttr1.setName("KHar");
			newAttr1.setValue("Gaav");
			theNode.getHasAttr().add(newAttr1);
			// TEST
*/			
			final int BORDER_SIZE = 5;
			final int TEXT_SHAPE_HEIGHT = 20;
			
			//  ---------- add attributes here --------------
			for (int i = 0; i < attribs.size(); i++)
			{
				Attribute attribute = attribs.get(i);
				Shape shape = createService.createShape(outerContainerShape, false);
				// create and set text graphics algorithm
				Text text = layoutService.createText(getDiagram(), shape, ""+attribute.getName()+" = "+attribute.getValue() , "Arial", 10);
//				text.setStyle(StyleUtil.getStyleForNodeText(getDiagram()));
				text.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
				text.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
				layoutService.setLocationAndSize(text, BORDER_SIZE, BORDER_SIZE + (i+1)*TEXT_SHAPE_HEIGHT, outerContainerShape.getGraphicsAlgorithm().getWidth()-2*BORDER_SIZE, TEXT_SHAPE_HEIGHT);
			}
		}
	}

	@Override
	public String getDescription() {
		return "Updates the Attribute Editor Window so it manipulates the current node's attributes";
	}

	@Override
	public String getName() {
		return "Show/Set Attributes";
	}

}
