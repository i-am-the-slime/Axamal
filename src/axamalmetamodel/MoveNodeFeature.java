package axamalmetamodel;

import javax.naming.Context;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.IMoveShapeFeature;
import org.eclipse.graphiti.features.context.IMoveShapeContext;
import org.eclipse.graphiti.features.impl.DefaultMoveShapeFeature;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import xml.Node;

public class MoveNodeFeature extends DefaultMoveShapeFeature implements
		IMoveShapeFeature {

	public MoveNodeFeature(IFeatureProvider fp) {
		super(fp);
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public boolean canMoveShape(IMoveShapeContext context) {
        boolean canMove = super.canMoveShape(context);
        if (!canMove)
        {			
        	ContainerShape container = context.getTargetContainer();
			if (container instanceof Diagram || getBusinessObjectForPictogramElement(container) instanceof Node)
        	{
				canMove = true;
			}
		}
        return canMove;
    }

	@Override
	public void moveShape(IMoveShapeContext context)
	{
		super.moveShape(context);
		ContainerShape source = context.getSourceContainer();
		if (!(source instanceof Diagram))
		{
			((Node)getBusinessObjectForPictogramElement(source)).getSubnodes().remove((Node) getBusinessObjectForPictogramElement(context.getPictogramElement()));
		}
		
		ContainerShape destination = context.getTargetContainer();
		if (!(destination instanceof Diagram))
		{
			((Node)getBusinessObjectForPictogramElement(destination)).getSubnodes().add((Node) getBusinessObjectForPictogramElement(context.getPictogramElement()));
		}
	}

}
