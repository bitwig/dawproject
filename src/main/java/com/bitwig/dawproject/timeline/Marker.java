package com.bitwig.dawproject.timeline;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Marker")
public class Marker
{
   @XmlAttribute(required = true)
   public double time;

   @XmlAttribute(required = false)
   public String name;

   @XmlAttribute(required = false)
   public String color;
}
