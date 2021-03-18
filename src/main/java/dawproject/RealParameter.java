package dawproject;

import javax.xml.bind.annotation.XmlAttribute;

public class RealParameter extends Parameter
{
   public RealParameter(final double v, Unit u)
   {
      value = v;
      unit = u;
   }

   @XmlAttribute
   public double value;

   @XmlAttribute
   public Unit unit;

   @XmlAttribute
   public Double min;

   @XmlAttribute
   public Double max;
}
