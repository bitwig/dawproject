package dawproject.timeline;

import dawproject.DoubleAdapter;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class RealPoint
{
   @XmlJavaTypeAdapter(DoubleAdapter.class)
   @XmlAttribute(required = true)
   public Double time;

   @XmlJavaTypeAdapter(DoubleAdapter.class)
   @XmlAttribute(required = true)
   public Double value;
}
