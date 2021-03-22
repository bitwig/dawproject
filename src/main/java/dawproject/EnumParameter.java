package dawproject;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EnumParameter extends Parameter
{
   @XmlAttribute
   public Integer value;

   /** Number of entries in enum value. value will be in the range [0 .. count-1] */
   @XmlAttribute(required = true)
   public Integer count;

   /** Labels of the individual enum values */
   @XmlAttribute(required = false)
   public String[] labels;
}
