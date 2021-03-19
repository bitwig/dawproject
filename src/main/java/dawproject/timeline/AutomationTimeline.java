package dawproject.timeline;

import java.util.ArrayList;
import java.util.List;
import dawproject.Parameter;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlIDREF;

public class AutomationTimeline extends Timeline
{
   @XmlElementWrapper(name="points")
   @XmlElement(name="point", type = AutomationPoint.class)
   public List<AutomationPoint> points = new ArrayList<>();

   @XmlIDREF
   @XmlAttribute
   public Parameter parameter;
}
