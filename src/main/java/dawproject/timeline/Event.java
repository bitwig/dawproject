package dawproject.timeline;

import dawproject.Nameable;
import jakarta.xml.bind.annotation.XmlAttribute;

public class Event extends Nameable
{
   @XmlAttribute
   public double time;
}
