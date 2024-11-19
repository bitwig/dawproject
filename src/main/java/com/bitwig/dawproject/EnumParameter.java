package com.bitwig.dawproject;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;


/**
 * Represents an enumerated parameter which can provide a value and be used as an automation target.
 */
@XmlRootElement(name = "EnumParameter")
public class EnumParameter extends Parameter
{
    /** Index of the enumeration value. */
    @XmlAttribute
    public Integer   value;

    /** Number of entries in enumeration value. value will be in the range [0 .. count-1]. */
    @XmlAttribute(required = true)
    public Integer   count;

    /** Labels of the individual enumeration values. */
    @XmlAttribute(required = false)
    public String [] labels;
}
