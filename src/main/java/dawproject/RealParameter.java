package dawproject;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class RealParameter extends Parameter
{
   @XmlAttribute
   @XmlJavaTypeAdapter(DoubleAdapter.class)
   public Double value;

   @XmlAttribute(required = true)
   public Unit unit = Unit.linear;

   @XmlAttribute
   @XmlJavaTypeAdapter(DoubleAdapter.class)
   public Double min;

   @XmlAttribute
   @XmlJavaTypeAdapter(DoubleAdapter.class)
   public Double max;
}
