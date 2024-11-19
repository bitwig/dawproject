package com.bitwig.dawproject.timeline;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;


/** The unit of time. */
@XmlEnum
public enum TimeUnit
{
    /** Time is represented in beats (quarter-notes). */
    @XmlEnumValue("beats")
    BEATS,
    /** Time is represented in seconds. */
    @XmlEnumValue("seconds")
    SECONDS;
}
