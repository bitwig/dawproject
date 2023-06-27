package com.bitwig.dawproject.timeline;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Warps the time of nested content as defined by a list of Warp points.
 *
 * A typical use case would be to warp an audio-file (contentTimeUnit = seconds) onto a timeline defined in beats
 * (timeUnit = beats) as defined by a set of Warp events.
 */

@XmlRootElement(name = "Warps")
public class Warps extends Timeline
{
   @XmlElementRef(required = true)
   public List<Warp> events = new ArrayList<>();

   /**
    * Content timeline to be warped.
    */
   @XmlElementRef(name = "Content", required = true)
   public Timeline content;

   /**
    * The TimeUnit used by the content (nested) timeline and the contentTime attribute of the Warp events
    * */
   @XmlAttribute(required = true)
   public TimeUnit contentTimeUnit;
}
