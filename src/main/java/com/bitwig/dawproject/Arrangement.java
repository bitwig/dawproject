package com.bitwig.dawproject;

import com.bitwig.dawproject.timeline.Lanes;
import com.bitwig.dawproject.timeline.Markers;
import com.bitwig.dawproject.timeline.Points;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Arrangement extends Referenceable
{
   /** Automation data for time-signature inside this Arrangement */
   @XmlElement(required = false, name = "timeSignature", type = Points.class)
   public Points timeSignature;

   /** Automation data for tempo inside this Arrangement, which will define the conversion between seconds and beats
    * at the root level. */
   @XmlElement(required = false, name = "tempo", type = Points.class)
   public Points tempo;

   /** Cue markers inside this arrangement */
   @XmlElement(required = false, name = "markers", type = Markers.class)
   public Markers markers;

   @XmlElementRef(name = "content", type = Lanes.class)
   public Lanes content;
}
