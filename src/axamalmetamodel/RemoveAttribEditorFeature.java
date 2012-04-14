package axamalmetamodel;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.IRemoveFeature;
import org.eclipse.graphiti.features.context.IRemoveContext;
import org.eclipse.graphiti.features.impl.DefaultRemoveFeature;

public class RemoveAttribEditorFeature extends DefaultRemoveFeature implements
		IRemoveFeature {

	public RemoveAttribEditorFeature(IFeatureProvider fp) {
		super(fp);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void postRemove(IRemoveContext context) {
		((XMLDiagramfeatureProvider)getFeatureProvider()).setAttributeEditor(null);
		super.preRemove(context);
	}

}
