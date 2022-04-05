package com.bitwig.dawproject.timeline;

import com.bitwig.dawproject.DoubleAdapter;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "RealPoint")
public class RealPoint extends Point
{
   @XmlJavaTypeAdapter(DoubleAdapter.class)
   @XmlAttribute(required = true)
   public Double value;
}
