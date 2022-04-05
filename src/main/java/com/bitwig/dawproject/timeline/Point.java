package com.bitwig.dawproject.timeline;

import com.bitwig.dawproject.DoubleAdapter;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "Point")
@XmlSeeAlso({RealPoint.class, EnumPoint.class, BoolPoint.class, IntegerPoint.class, TimeSignaturePoint.class})
public class Point
{
   @XmlJavaTypeAdapter(DoubleAdapter.class)
   @XmlAttribute(required = true)
   public Double time;
}
