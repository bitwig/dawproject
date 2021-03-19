package dawproject.timeline;

import dawproject.Referencable;
import dawproject.Track;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlIDREF;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement
@XmlSeeAlso({Notes.class, RootTimeline.class, Lanes.class, Clips.class})

public abstract class Timeline extends Referencable
{
   @XmlAttribute
   @XmlIDREF
   public Track track;

   /** The Timebase used by this and nested timelines */
   @XmlAttribute
   public Timebase timebase;
}
