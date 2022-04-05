package com.bitwig.dawproject.timeline;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Warp")
public class Warp
{
   @XmlAttribute(required = true)
   public double time;

   @XmlAttribute(required = true)
   public double warped;
}
