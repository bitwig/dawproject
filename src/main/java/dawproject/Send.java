package dawproject;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlIDREF;

public class Send extends RealParameter
{
   public static Send create(final double v, final Unit u)
   {
      final var p = new Send();
      p.value = v;
      p.unit = u;
      return p;
   }

   @XmlAttribute
   public SendType type = SendType.post;

   @XmlAttribute
   @XmlIDREF
   public Channel destination;
}
