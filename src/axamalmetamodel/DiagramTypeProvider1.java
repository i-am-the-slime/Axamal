package axamalmetamodel;

import org.eclipse.graphiti.dt.AbstractDiagramTypeProvider;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;

public class DiagramTypeProvider1 extends AbstractDiagramTypeProvider implements
		IDiagramTypeProvider {

	public DiagramTypeProvider1() {
		setFeatureProvider(new XMLDiagramfeatureProvider(this));
	}

}
