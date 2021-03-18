package dawproject;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;

public class Send extends RealParameter
{
   public Send(final double v, final Unit u)
   {
      super(v, u);
   }

   public SendType type = SendType.post;

   @XmlAttribute
   @XmlIDREF
   public Channel destination;
}
