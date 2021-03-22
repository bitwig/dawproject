package dawproject;

import dawproject.timeline.Timeline;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Scene extends Referencable
{
   @XmlElementRef
   public Timeline content;
}
