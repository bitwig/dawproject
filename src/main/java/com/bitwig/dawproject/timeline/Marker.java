package com.bitwig.dawproject.timeline;

import com.bitwig.dawproject.Nameable;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Marker")
public class Marker extends Nameable
{
   @XmlAttribute(required = true)
   public double time;
}
