package dawproject.timeline;

import java.util.ArrayList;
import java.util.List;
import dawproject.Parameter;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlIDREF;

public class Points extends Timeline
{
   @XmlElementWrapper(name="points", required = true)
   @XmlElement(name="point", type = AutomationPoint.class)
   public List<AutomationPoint> points = new ArrayList<>();

   @XmlIDREF
   @XmlAttribute(required = true)
   public Parameter parameter;
}
