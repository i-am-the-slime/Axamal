package axamalmetamodel;

import org.eclipse.graphiti.dt.AbstractDiagramTypeProvider;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.tb.IToolBehaviorProvider;

public class DiagramTypeProvider1 extends AbstractDiagramTypeProvider implements
		IDiagramTypeProvider {

	public DiagramTypeProvider1() {
		setFeatureProvider(new XMLDiagramfeatureProvider(this));
	}

	@Override
	public IToolBehaviorProvider[] getAvailableToolBehaviorProviders() {
		return new IToolBehaviorProvider[]{new XMLDiagramToolBehaviorProvider(this)};
	}
	
	

}
