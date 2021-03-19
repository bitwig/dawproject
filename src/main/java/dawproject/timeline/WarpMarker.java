package dawproject.timeline;

import jakarta.xml.bind.annotation.XmlAttribute;

public class WarpMarker
{
   @XmlAttribute
   public double time;

   @XmlAttribute
   public double seconds;
}
