package axamalmetamodel;

import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.context.IDoubleClickContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.tb.DefaultToolBehaviorProvider;
import org.eclipse.graphiti.tb.IToolBehaviorProvider;

public class XMLDiagramToolBehaviorProvider extends DefaultToolBehaviorProvider implements IToolBehaviorProvider {

	public XMLDiagramToolBehaviorProvider(IDiagramTypeProvider diagramTypeProvider)
	{
		super(diagramTypeProvider);
	}

	@Override
	public ICustomFeature getDoubleClickFeature(IDoubleClickContext context) {
		ICustomFeature doubleClickNodeFeature = new DoubleClickNodeFeature(getFeatureProvider());
		if (doubleClickNodeFeature.canExecute(context))
		{
			return doubleClickNodeFeature;
		}
		return super.getDoubleClickFeature(context);
	}


}
