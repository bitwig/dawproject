package com.bitwig.dawproject;

import com.bitwig.dawproject.timeline.Timeline;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlRootElement;

/** Represents a clip launcher Scene of a DAW. */
@XmlRootElement(name = "Scene")
public class Scene extends Referenceable
{
   /** Content timeline of this scene. */
   @XmlElementRef(name = "Timeline")
   public Timeline content;
}
