package dawproject;

import dawproject.timeline.Timeline;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Arrangement extends Referencable
{
   @XmlElementRef
   public Timeline content;
}
