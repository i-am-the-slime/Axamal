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
 
    private static final IColorConstant E_CLASS_TEXT_FOREGROUND =
        new ColorConstant(227, 253, 255);
 
    private static final IColorConstant E_CLASS_FOREGROUND =
        new ColorConstant(255, 102, 0);
 
    private static String DEFAULT_FONT = "Arial";
 
    public static Style getStyleForEClass(Diagram diagram) {
        final String styleId = "E-CLASS";
 
        Style style = findStyle(diagram, styleId);
 
        if (style == null) { // style not found - create new style
            IGaService gaService = Graphiti.getGaService();
            style = gaService.createStyle(diagram, styleId);
            style.setForeground(gaService.manageColor(diagram,
                 E_CLASS_FOREGROUND));
            gaService.setRenderingStyle(style,
                 NodeColoredAreas.getNiceGreyAdaptions());
            style.setLineWidth(2);
        }
        return style;
    }
 
    public static Style getStyleForEClassText(Diagram diagram) {
        final String styleId = "E-CLASS-TEXT";
 
        // this is a child style of the e-class-style
        Style parentStyle = getStyleForEClass(diagram);
        Style style = findStyle(parentStyle, styleId);
 
        if (style == null) { // style not found - create new style
            IGaService gaService = Graphiti.getGaService();
            style = gaService.createStyle(getStyleForEClass(diagram), styleId);
            // "overwrites" values from parent style
            style.setForeground(gaService.manageColor(diagram,
                E_CLASS_TEXT_FOREGROUND));
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
