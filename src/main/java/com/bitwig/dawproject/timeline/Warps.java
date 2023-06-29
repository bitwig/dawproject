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
 *
 * At least two Warp events need to present in order to define a usable beats/seconds conversion. For a plain
 * fixed-speed mapping, provide two event: One at (0,0) and a second event with the desired beat-time length along
 * with the length of the contained Audio file in seconds.
 * */

@XmlRootElement(name = "Warps")
public class Warps extends Timeline
{
   @XmlElement(required = true, name = "Warp")
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
