package dawproject.timeline;

import jakarta.xml.bind.annotation.XmlAttribute;

public class EnduringEvent extends Event
{
   @XmlAttribute
   public double duration;
}
