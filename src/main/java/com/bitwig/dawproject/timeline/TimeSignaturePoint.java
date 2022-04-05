package com.bitwig.dawproject.timeline;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "TimeSignaturePoint")
public class TimeSignaturePoint extends Point
{
   @XmlAttribute(required = true)
   public Integer numerator;

   @XmlAttribute(required = true)
   public Integer denominator;
}
