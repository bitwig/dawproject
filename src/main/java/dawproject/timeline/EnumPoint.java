package dawproject.timeline;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EnumPoint extends Point
{
   @XmlAttribute(required = true)
   public Integer value;
}
