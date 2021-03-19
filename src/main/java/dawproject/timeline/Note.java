package dawproject.timeline;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Note extends EnduringEvent
{
   @XmlAttribute
   public int channel;
   @XmlAttribute
   public int key;

   @XmlAttribute
   public double velocity;
   @XmlAttribute
   public double releaseVelocity;
}
