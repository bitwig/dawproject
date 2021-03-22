package dawproject.timeline;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TimeSignaturePoint extends Point
{
   @XmlAttribute(required = true)
   public Integer numerator;

   @XmlAttribute(required = true)
   public Integer denominator;
}
