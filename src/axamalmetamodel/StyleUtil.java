package axamalmetamodel;

import java.util.Collection;

import org.eclipse.graphiti.mm.StyleContainer;
import org.eclipse.graphiti.mm.algorithms.styles.Style;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.util.ColorConstant;
import org.eclipse.graphiti.util.IColorConstant;

public class StyleUtil {
 
    private static final IColorConstant NODE_TEXT_FOREGROUND =
        new ColorConstant(250, 250, 245);
 
    private static final IColorConstant NODE_FOREGROUND =
        new ColorConstant(250, 250, 245);
 
    private static String DEFAULT_FONT = "Arial";
 
    public static Style getStyleForNode(Diagram diagram) {
        final String styleId = "NODE";
 
        Style style = findStyle(diagram, styleId);
 
        if (style == null) { // style not found - create new style
            IGaService gaService = Graphiti.getGaService();
            style = gaService.createStyle(diagram, styleId);
            style.setForeground(gaService.manageColor(diagram,
                 NODE_FOREGROUND));
            gaService.setRenderingStyle(style,
                 NodeColoredAreas.getNiceGreyAdaptions());
            style.setLineWidth(10);
        }
        return style;
    }
 
    public static Style getStyleForNodeText(Diagram diagram) {
        final String styleId = "NODE-TEXT";
 
        // this is a child style of the e-class-style
        Style parentStyle = getStyleForNode(diagram);
        Style style = findStyle(parentStyle, styleId);
 
        if (style == null) { // style not found - create new style
            IGaService gaService = Graphiti.getGaService();
            style = gaService.createStyle(getStyleForNode(diagram), styleId);
            // "overwrites" values from parent style
            style.setForeground(gaService.manageColor(diagram,
                NODE_TEXT_FOREGROUND));
            style.setFont(gaService.manageFont(diagram, DEFAULT_FONT, 5000, false, true));
        }
        return style;
    }
 
    // find the style with a given id in the style-container, can return null
    private static Style findStyle(StyleContainer styleContainer, String id) {
        // find and return style
        Collection<Style> styles = styleContainer.getStyles();
        if (styles != null) {
            for (Style style : styles) {
                if (id.equals(style.getId())) {
                    return style;
                }
            }
        }
        return null;
    }
}
