package dawproject.timeline;

import dawproject.DawObject;
import jakarta.xml.bind.annotation.XmlAttribute;

public class Event extends DawObject
{
   @XmlAttribute
   public double time;
}
