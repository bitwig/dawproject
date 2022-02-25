package com.bitwig.dawproject;

import com.bitwig.dawproject.timeline.Markers;
import com.bitwig.dawproject.timeline.Points;
import com.bitwig.dawproject.timeline.Timeline;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Arrangement extends Referenceable
{
   @XmlElementRef
   public Timeline content;

   /** Automation data for time-signature inside this Arrangement */
   @XmlElement(required = false)
   public Points timeSignature;

   /** Automation data for tempo inside this Arrangement, which will define the conversion between seconds and beats
    * at the root level. */
   @XmlElement(required = false)
   public Points tempo;

   /** Cue markers inside this arrangement */
   @XmlElement(required = false)
   public Markers markers;
}
