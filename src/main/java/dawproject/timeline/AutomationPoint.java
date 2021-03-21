package dawproject.timeline;

import jakarta.xml.bind.annotation.XmlAttribute;

public class AutomationPoint
{
   @XmlAttribute(required = true)
   public double time;

   @XmlAttribute(required = true)
   public double value;
}
