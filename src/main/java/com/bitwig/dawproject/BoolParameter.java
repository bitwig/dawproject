package com.bitwig.dawproject;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "BoolParameter")
public class BoolParameter extends Parameter
{
   @XmlAttribute(required = false)
   public Boolean value;
}
