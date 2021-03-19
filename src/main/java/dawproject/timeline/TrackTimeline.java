package dawproject.timeline;

import dawproject.Track;
import jakarta.xml.bind.annotation.XmlIDREF;

/** Associates a timeline with a track. */
public class TrackTimeline extends Timeline
{
   @XmlIDREF
   public Track track;

   @XmlIDREF
   public Timeline timeline;
}
