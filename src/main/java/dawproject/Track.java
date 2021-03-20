package dawproject;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlIDREF;
import jakarta.xml.bind.annotation.XmlList;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;

/** Represents the sequencer track of the DAW, */

@XmlRootElement(name = "Track")
@XmlSeeAlso(ContentType.class)
public class Track extends Referencable
{
   /** A parent track (when present) can be used for visual organization of the tracks. */
   @XmlIDREF
   @XmlAttribute()
   public Track parent;

   /** Target channel this track will play back into. */
   @XmlIDREF
   @XmlAttribute()
   public Channel channel;

   @XmlAttribute
   @XmlList
   public ContentType[] contentType;
}
