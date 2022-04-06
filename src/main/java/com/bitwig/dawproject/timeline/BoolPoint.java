package com.bitwig.dawproject.timeline;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "BoolPoint")
public class BoolPoint extends Point
{
   @XmlAttribute(required = true)
   public Boolean value;
}
