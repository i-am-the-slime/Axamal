package axamalmetamodel;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.impl.AbstractCreateFeature;

import xml.Attribute;
import xml.Node;
import xml.XmlFactory;

public class CreateAttributeFeature extends AbstractCreateFeature implements
		ICreateFeature {

	public CreateAttributeFeature(IFeatureProvider fp) {
		super(fp, "Add attribute to a tag", "Adds an attribute to a tag");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canCreate(ICreateContext context) {
		for (EObject x : context.getTargetContainer().getLink().getBusinessObjects()) {
			if (x instanceof Node) {
				return true;
			}
		}	
		return false;
	}

	@Override
	public Object[] create(ICreateContext context) {
		Node parentNode = null;
		for (EObject x : context.getTargetContainer().getLink().getBusinessObjects()) {
			if (x instanceof Node) {
				parentNode = (Node)x;
			}
		}	
		Attribute attribute = XmlFactory.eINSTANCE.createAttribute();
		context.getTargetContainer().eResource().getContents().add(attribute);	//TODO: separate the resource for domain objects and graphical things.
		addGraphicalRepresentation(context, attribute);
		getFeatureProvider().getDirectEditingInfo().setActive(true);
		
		//Add it to the parent node
		parentNode.getHasAttr().add(attribute);
		
		return new Object[] { attribute };
	}

}
