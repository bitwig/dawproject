package dawproject.timeline;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ClipSlot extends Timeline
{
   @XmlAttribute(required = false)
   public Boolean hasStop;

   @XmlElementRef(required = false)
   public Clip clip;
}
