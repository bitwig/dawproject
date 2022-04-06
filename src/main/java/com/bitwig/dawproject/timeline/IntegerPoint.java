package com.bitwig.dawproject.timeline;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "IntegerPoint")
public class IntegerPoint extends Point
{
   @XmlAttribute(required = true)
   public Integer value;
}
