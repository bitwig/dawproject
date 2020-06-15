package dawproject;

import java.util.ArrayList;
import java.util.List;

public class AutomationTimeline extends Timeline
{
   public List<AutomationPoint> points = new ArrayList<>();

   public ParameterReference parameter;

   /** Numeric unit which the automation point values represent. */
   public Unit unit = Unit.normalized;
}
