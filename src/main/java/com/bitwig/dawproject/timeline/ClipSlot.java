package com.bitwig.dawproject.timeline;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ClipSlot")
public class ClipSlot extends Timeline
{
   @XmlAttribute(required = false)
   public Boolean hasStop;

   @XmlElementRef(name = "Clip", required = false)
   public Clip clip;
}
