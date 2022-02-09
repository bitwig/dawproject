package com.bitwig.dawproject;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
@XmlSeeAlso({Unit.class})
public class RealParameter extends Parameter
{
   @XmlAttribute
   @XmlJavaTypeAdapter(DoubleAdapter.class)
   public Double value;

   @XmlAttribute(required = true)
   public Unit unit;

   @XmlAttribute
   @XmlJavaTypeAdapter(DoubleAdapter.class)
   public Double min;

   @XmlAttribute
   @XmlJavaTypeAdapter(DoubleAdapter.class)
   public Double max;
}
