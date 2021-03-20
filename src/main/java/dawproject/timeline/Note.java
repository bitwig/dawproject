package dawproject.timeline;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Note
{
   @XmlAttribute
   public double time;

   @XmlAttribute
   public double duration;

   @XmlAttribute
   public int channel;
   @XmlAttribute
   public int key;

   @XmlAttribute(name = "vel")
   public double velocity;
   @XmlAttribute(name = "rel")
   public double releaseVelocity;
}
