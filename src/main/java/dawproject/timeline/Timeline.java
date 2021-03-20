package dawproject.timeline;

import dawproject.Referencable;
import dawproject.Track;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlIDREF;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement
@XmlSeeAlso({Note.class, Notes.class, Lanes.class, Clip.class, Clips.class, Marker.class, Markers.class, Audio.class, Video.class})
public abstract class Timeline extends Referencable
{
   /** When present, the timeline is local to this track. */
   @XmlAttribute
   @XmlIDREF
   public Track track;

   /** The Timebase used by this and nested timelines */
   @XmlAttribute
   public Timebase timebase;
}
