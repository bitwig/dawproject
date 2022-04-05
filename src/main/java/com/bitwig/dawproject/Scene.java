package com.bitwig.dawproject;

import com.bitwig.dawproject.timeline.Timeline;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Scene")
public class Scene extends Referenceable
{
   @XmlElementRef(name = "Timeline")
   public Timeline content;
}
