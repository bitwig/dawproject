package dawproject.timeline;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RootTimeline extends Timeline
{
   @XmlElementWrapper(name="cue-markers")
   @XmlElementRef
   public List<MarkerEvent> markers;

   /** Lanes representing nested content */

   @XmlElementWrapper(name="lanes")
   @XmlElementRef
   public List<Timeline> lanes = new ArrayList<>();
}
