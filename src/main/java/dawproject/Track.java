package dawproject;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;

/** Represents the sequencer track of the DAW, */

@XmlRootElement(name = "Track")
public class Track extends DawObject
{
   /** A parent track (when present) can be used for visual organization of the tracks. */
   @XmlIDREF
   @XmlAttribute()
   public Track parent;

   /** Target channel this track will play back into. */
   @XmlIDREF
   @XmlAttribute()
   public Channel channel;
}
