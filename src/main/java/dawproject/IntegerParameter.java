package dawproject;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class IntegerParameter extends Parameter
{
   @XmlAttribute
   public Integer value;

   @XmlAttribute
   public Integer min;

   @XmlAttribute
   public Integer max;
}
