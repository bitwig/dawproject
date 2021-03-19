package dawproject.timeline;

import dawproject.DawObject;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public abstract class Timeline extends DawObject
{
   /** The Timebase used by this and nested timelines */
   public Timebase timebase;
}
