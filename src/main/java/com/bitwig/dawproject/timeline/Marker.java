package com.bitwig.dawproject.timeline;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

import com.bitwig.dawproject.Nameable;


/** A single cue-marker. */
@XmlRootElement(name = "Marker")
public class Marker extends Nameable
{
    /** Time on the parent timeline of this marker. */
    @XmlAttribute(required = true)
    public double time;
}
