package dawproject.timeline;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CompositeTimeline extends Timeline
{
   /** Lanes representing nested content */

   @XmlElementWrapper(name="lanes")
   @XmlElementRef
   public List<Timeline> lanes = new ArrayList<>();
}
