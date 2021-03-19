package dawproject;

import jakarta.xml.bind.annotation.XmlAttribute;

public class RealParameter extends Parameter
{
   public static RealParameter create(final double v, Unit u)
   {
      final var p = new RealParameter();
      p.value = v;
      p.unit = u;
      return p;
   }

   @XmlAttribute(required = true)
   public double value;

   @XmlAttribute(required = true)
   public Unit unit = Unit.linear;

   @XmlAttribute
   public Double min;

   @XmlAttribute
   public Double max;
}
