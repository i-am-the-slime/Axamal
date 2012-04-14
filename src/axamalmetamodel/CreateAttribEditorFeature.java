package axamalmetamodel;

import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.impl.AbstractCreateFeature;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import xml.Attribute;
import xml.Node;
import xml.XmlFactory;

public class CreateAttribEditorFeature extends AbstractCreateFeature implements
		ICreateFeature {

	public CreateAttribEditorFeature(IFeatureProvider fp) {
		super(fp, "Attribute Editor Window", "Show a window that can edit attributes of the currently selected node");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canCreate(ICreateContext context) {
		XMLDiagramfeatureProvider a = (XMLDiagramfeatureProvider) getFeatureProvider();
		return (context.getTargetContainer() instanceof Diagram) && (a.getAttributeEditor() == null);	
	}

	@Override
	public Object[] create(ICreateContext context) {
		AttributeEditor theAttribEditor = new AttributeEditor();
		getDiagram().eResource().getContents().add(theAttribEditor);	//TODO: separate the resource for domain objects and graphical things.
		addGraphicalRepresentation(context, theAttribEditor);
		getFeatureProvider().getDirectEditingInfo().setActive(true);
		
		((XMLDiagramfeatureProvider)getFeatureProvider()).setAttributeEditor(theAttribEditor);
		
		return new Object[] { theAttribEditor };
	}

}
