package dawproject.timeline;

import dawproject.DoubleAdapter;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class BoolPoint
{
   @XmlJavaTypeAdapter(DoubleAdapter.class)
   @XmlAttribute(required = true)
   public Double time;

   @XmlAttribute(required = true)
   public Boolean value;
}
