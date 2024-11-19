package com.bitwig.dawproject.timeline;

import com.bitwig.dawproject.DoubleAdapter;
import com.bitwig.dawproject.Interpolation;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/** A point with a double resolution and additional interpolation information. */
@XmlRootElement(name = "RealPoint")
public class RealPoint extends Point
{
    /** The value of the point. */
    @XmlJavaTypeAdapter(DoubleAdapter.class)
    @XmlAttribute(required = true)
    public Double        value;

    /**
     * Interpolation mode used for the segment starting at this point. Default to 'hold' when
     * unspecified.
     */
    @XmlAttribute(required = false)
    public Interpolation interpolation;
}
