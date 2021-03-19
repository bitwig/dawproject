package dawproject.timeline;

import dawproject.Track;
import jakarta.xml.bind.annotation.XmlIDREF;
import jakarta.xml.bind.annotation.XmlRootElement;

/** Associates a timeline with a track. */

@XmlRootElement
public class TrackTimeline extends Timeline
{
   @XmlIDREF
   public Track track;

   @XmlIDREF
   public Timeline timeline;
}
