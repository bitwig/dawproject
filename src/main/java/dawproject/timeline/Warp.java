package dawproject.timeline;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Warp
{
   @XmlAttribute
   public double time;

   @XmlAttribute
   public double warped;
}
