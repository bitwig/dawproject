package dawproject.timeline;

import dawproject.Nameable;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement
@XmlSeeAlso({TrackTimeline.class, NoteTimeline.class, RootTimeline.class, CompositeTimeline.class, ClipTimeline.class})

public abstract class Timeline extends Nameable
{
   /** The Timebase used by this and nested timelines */
   public Timebase timebase;
}
