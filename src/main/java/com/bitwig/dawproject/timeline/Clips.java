package com.bitwig.dawproject.timeline;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/** Represents a timeline of clips. Each contained Clip have its time and duration that defines its location on this
 * timeline (defined by timeUnit of the Clips element). */

@XmlRootElement(name = "Clips")
public class Clips extends Timeline
{

   /** Clips of this timeline. */
   @XmlElement(name = "Clip")
   public List<Clip> clips = new ArrayList<>();
}
