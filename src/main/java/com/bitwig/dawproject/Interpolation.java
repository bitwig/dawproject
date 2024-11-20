package com.bitwig.dawproject;

import jakarta.xml.bind.annotation.XmlEnumValue;


/** Interpolation variations. */
public enum Interpolation
{
    /** Hold interpolation. */
    @XmlEnumValue("hold")
    HOLD,

    /** Linear interpolation. */
    @XmlEnumValue("linear")
    LINEAR
}
