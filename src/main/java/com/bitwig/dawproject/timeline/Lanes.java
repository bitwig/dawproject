package com.bitwig.dawproject.timeline;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlRootElement;

/** The Lanes element provides the ability to contain multiple parallel timelines inside it, and is the main layering
 *  element of the format. It is also a natural fit for defining the scope of contained timelines to a specific track.  */
@XmlRootElement(name = "Lanes")
public class Lanes extends Timeline
{
   /** Lanes representing nested content. */
   @XmlElementRef
   public List<Timeline> lanes = new ArrayList<>();
}
