package com.bitwig.dawproject.timeline;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;


/** A single automation point for a time-signature value. */
@XmlRootElement(name = "TimeSignaturePoint")
public class TimeSignaturePoint extends Point
{
    /** Numerator of the time-signature. (3/4 &rarr; 3, 4/4 &rarr; 4) */
    @XmlAttribute(required = true)
    public Integer numerator;

    /** Denominator of the time-signature. (3/4 &rarr; 4, 7/8 &rarr; 8) */
    @XmlAttribute(required = true)
    public Integer denominator;
}
