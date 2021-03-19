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

   @XmlAttribute
   public double value;

   @XmlAttribute
   public Unit unit;

   @XmlAttribute
   public Double min;

   @XmlAttribute
   public Double max;
}
