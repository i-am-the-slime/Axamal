//Defines the style for nodes

package axamalmetamodel;

import org.eclipse.emf.common.util.EList;
import org.eclipse.graphiti.mm.algorithms.styles.AdaptedGradientColoredAreas;
import org.eclipse.graphiti.mm.algorithms.styles.GradientColoredArea;
import org.eclipse.graphiti.mm.algorithms.styles.GradientColoredAreas;
import org.eclipse.graphiti.mm.algorithms.styles.LocationType;
import org.eclipse.graphiti.mm.algorithms.styles.StylesFactory;
import org.eclipse.graphiti.util.IGradientType;
import org.eclipse.graphiti.util.IPredefinedRenderingStyle;
import org.eclipse.graphiti.util.PredefinedColoredAreas;


public class NodeColoredAreas extends PredefinedColoredAreas implements NodeRenderingStyle {

private static GradientColoredAreas getNiceGreyDefaultAreas() {
   final GradientColoredAreas gradientColoredAreas =
         StylesFactory.eINSTANCE.createGradientColoredAreas();
   final EList<GradientColoredArea> gcas =
         gradientColoredAreas.getGradientColor();

   addGradientColoredArea(gcas, "A1BFC2", 0, //CCFFCC 
         LocationType.LOCATION_TYPE_ABSOLUTE_START, "A1BFC2", 1, //CCFFCC
         LocationType.LOCATION_TYPE_ABSOLUTE_START);
   addGradientColoredArea(gcas, "A1BFC2", 1, //CCFF99
         LocationType.LOCATION_TYPE_ABSOLUTE_START, "A9C0C2", 2, //CCFF99
         LocationType.LOCATION_TYPE_ABSOLUTE_START);
   addGradientColoredArea(gcas, "A9C0C2", 2, //CCFF66
         LocationType.LOCATION_TYPE_ABSOLUTE_START, "6A8689", 3, //CCFF66
         LocationType.LOCATION_TYPE_ABSOLUTE_START);
   addGradientColoredArea(gcas, "6A8689", 3, //66FF00
         LocationType.LOCATION_TYPE_ABSOLUTE_START, "556566", 2, //CCFFCC
         LocationType.LOCATION_TYPE_ABSOLUTE_END);
   addGradientColoredArea(gcas, "556566", 2, //CCFFCC
         LocationType.LOCATION_TYPE_ABSOLUTE_END, "556566", 0, //CCFFCC
         LocationType.LOCATION_TYPE_ABSOLUTE_END);
   gradientColoredAreas.setStyleAdaption
        (IPredefinedRenderingStyle.STYLE_ADAPTATION_DEFAULT);
   return gradientColoredAreas;
}

private static GradientColoredAreas getNiceGreyPrimarySelectedAreas() {
   final GradientColoredAreas gradientColoredAreas =
         StylesFactory.eINSTANCE.createGradientColoredAreas();
  gradientColoredAreas.setStyleAdaption
         (IPredefinedRenderingStyle.STYLE_ADAPTATION_PRIMARY_SELECTED);
   final EList<GradientColoredArea> gcas =
         gradientColoredAreas.getGradientColor();

   addGradientColoredArea(gcas, "A9C0C2", 0, //66CCCC
         LocationType.LOCATION_TYPE_ABSOLUTE_START, "A9C0C2", 1, //66CCCC
         LocationType.LOCATION_TYPE_ABSOLUTE_START);
   addGradientColoredArea(gcas, "A9C0C2", 1, //66CC99
         LocationType.LOCATION_TYPE_ABSOLUTE_START, "A9C0C2", 2, //66CC99
         LocationType.LOCATION_TYPE_ABSOLUTE_START);
   addGradientColoredArea(gcas, "A9C0C2", 2, //66CC66
         LocationType.LOCATION_TYPE_ABSOLUTE_START, "A9C0C2", 3, //66CC66
         LocationType.LOCATION_TYPE_ABSOLUTE_START);
   addGradientColoredArea(gcas, "A1BFC2", 3, //00CC00
         LocationType.LOCATION_TYPE_ABSOLUTE_START, "A9C0C2", 2, //00CC66
         LocationType.LOCATION_TYPE_ABSOLUTE_END);
   addGradientColoredArea(gcas, "A9C0C2", 2, //00CC99
         LocationType.LOCATION_TYPE_ABSOLUTE_END, "A9C0C2", 0, //00CC99
         LocationType.LOCATION_TYPE_ABSOLUTE_END);
   return gradientColoredAreas;
}

private static GradientColoredAreas getNiceGreySecondarySelectedAreas() {
   final GradientColoredAreas gradientColoredAreas =
         StylesFactory.eINSTANCE.createGradientColoredAreas();
   gradientColoredAreas.setStyleAdaption
         (IPredefinedRenderingStyle.STYLE_ADAPTATION_SECONDARY_SELECTED);
   final EList<GradientColoredArea> gcas =
         gradientColoredAreas.getGradientColor();

   addGradientColoredArea(gcas, "556566", 0, //33CCCC
         LocationType.LOCATION_TYPE_ABSOLUTE_START, "556566", 1, //33CCCC
         LocationType.LOCATION_TYPE_ABSOLUTE_START);
   addGradientColoredArea(gcas, "556566", 1, //33CC99
         LocationType.LOCATION_TYPE_ABSOLUTE_START, "556566", 2, //33CC99
         LocationType.LOCATION_TYPE_ABSOLUTE_START);
   addGradientColoredArea(gcas, "556566", 2, //33CC66
         LocationType.LOCATION_TYPE_ABSOLUTE_START, "556566", 3, //33CC66
         LocationType.LOCATION_TYPE_ABSOLUTE_START);
   addGradientColoredArea(gcas, "556566", 3, //33CC00
         LocationType.LOCATION_TYPE_ABSOLUTE_START, "556566", 2, //33CC99
         LocationType.LOCATION_TYPE_ABSOLUTE_END);
   addGradientColoredArea(gcas, "556566", 2, //66CC99
         LocationType.LOCATION_TYPE_ABSOLUTE_END, "556566", 0, //66CC99
         LocationType.LOCATION_TYPE_ABSOLUTE_END);
   return gradientColoredAreas;
}

public static AdaptedGradientColoredAreas getNiceGreyAdaptions() {
   final AdaptedGradientColoredAreas agca =
         StylesFactory.eINSTANCE.createAdaptedGradientColoredAreas();
   agca.setDefinedStyleId(NICE_GREY_ID);
   agca.setGradientType(IGradientType.VERTICAL);
   agca.getAdaptedGradientColoredAreas()
        .add(IPredefinedRenderingStyle.STYLE_ADAPTATION_DEFAULT,
        getNiceGreyDefaultAreas());
   agca.getAdaptedGradientColoredAreas()
        .add(IPredefinedRenderingStyle.STYLE_ADAPTATION_PRIMARY_SELECTED,
         getNiceGreyPrimarySelectedAreas());
   agca.getAdaptedGradientColoredAreas()
       .add(IPredefinedRenderingStyle.STYLE_ADAPTATION_SECONDARY_SELECTED,
        getNiceGreySecondarySelectedAreas());
   return agca;
}
} 
