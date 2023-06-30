package com.bitwig.dawproject;

import com.bitwig.dawproject.timeline.Lanes;
import com.bitwig.dawproject.timeline.Markers;
import com.bitwig.dawproject.timeline.Points;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/** Represents the main Arrangement timeline of a DAW. */

@XmlRootElement(name = "Arrangement")
public class Arrangement extends Referenceable
{
   /** Automation data for time-signature inside this Arrangement.
    * <pre>{@code
    * <Arrangement>
    *   <TimeSignatureAutomation target="id-of-TimeSignatureParameter" ... >
    *     <TimeSignaturePoint time="0" numerator="7", denominator="8"/>
    *     <TimeSignaturePoint time="21" numerator="4", denominator="4"/>
    *        ...
    *   </TimeSignatureAutomation>
    * </Arrangement>
    * }</pre>
    *  */
   @XmlElement(required = false, name = "TimeSignatureAutomation", type = Points.class)
   public Points timeSignatureAutomation;

   /** Automation data for tempo inside this Arrangement, which will define the conversion between seconds and beats
    * at the root level. */
   @XmlElement(required = false, name = "TempoAutomation", type = Points.class)
   public Points tempoAutomation;

   /** Cue markers inside this arrangement */
   @XmlElement(required = false, name = "Markers", type = Markers.class)
   public Markers markers;

   /** The lanes of this arrangement. Generally this would contain another Lanes timeline for (and scoped to) each
    * track which would then contain all Note, Audio, and Automation timelines. */
   @XmlElement(name = "Lanes", type = Lanes.class)
   public Lanes lanes;
}
