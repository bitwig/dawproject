package dawproject.timeline;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ClipSlot extends Timeline
{
   @XmlAttribute
   public Boolean hasStopButton;

   @XmlElementRef
   public Clip clip;
}
