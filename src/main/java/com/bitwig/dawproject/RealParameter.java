package com.bitwig.dawproject;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/** Represents a real valued (double) parameter which can provide a value and be used as an automation target. */
@XmlRootElement(name = "RealParameter")
@XmlSeeAlso({Unit.class})
public class RealParameter extends Parameter
{
   /** Real (double) value for this parameter.
    * <p>When serializing value to text for XML, infinite values are allowed and should be represented as inf and -inf. </p>*/
   @XmlAttribute
   @XmlJavaTypeAdapter(DoubleAdapter.class)
   public Double value;

   /** Unit in which value, min and max are defined.
    * <p>Using this rather than normalized value ranges allows transfer of parameter values and automation data.</p> */
   @XmlAttribute(required = true)
   public Unit unit;

   /** Minimum value this parameter can have (inclusive). */
   @XmlAttribute
   @XmlJavaTypeAdapter(DoubleAdapter.class)
   public Double min;

   /** Maximum value this parameter can have (inclusive). */
   @XmlAttribute
   @XmlJavaTypeAdapter(DoubleAdapter.class)
   public Double max;
}
