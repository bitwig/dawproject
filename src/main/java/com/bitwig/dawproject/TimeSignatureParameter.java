package com.bitwig.dawproject;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "TimeSignatureParameter")
public class TimeSignatureParameter extends Parameter
{
   @XmlAttribute(required = true)
   public Integer numerator;

   @XmlAttribute(required = true)
   public Integer denominator;
}
