package com.bitwig.dawproject.timeline;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

/** A single automation point for an enumerated value. */
@XmlRootElement(name = "EnumPoint")
public class EnumPoint extends Point
{
   /** Integer value of the Enum index for this point. */
   @XmlAttribute(required = true)
   public Integer value;
}
