package dawproject;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;

public class Send extends DawObject
{
   public double level;

   @XmlAttribute
   @XmlIDREF
   public Channel output;

   public SendType type = SendType.post;
}
