package axamalmetamodel;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.impl.AbstractCreateFeature;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import xml.Node;
import xml.XmlFactory;

public class CreateNodeFeature extends AbstractCreateFeature implements
		ICreateFeature {

	public CreateNodeFeature(IFeatureProvider fp) {
		super(fp, "Create Node", "Add a node");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canCreate(ICreateContext context) {
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

	@Override
	public Object[] create(ICreateContext context) {
		Node theNode = XmlFactory.eINSTANCE.createNode();
		context.getTargetContainer().eResource().getContents().add(theNode);	//TODO: separate the resource for domain objects and graphical things.
		addGraphicalRepresentation(context, theNode);
		getFeatureProvider().getDirectEditingInfo().setActive(true);
		
		return new Object[] { theNode };
	}

}
