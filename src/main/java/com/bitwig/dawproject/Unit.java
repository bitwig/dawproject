package com.bitwig.dawproject;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;


/** Units for parameter values. */
@XmlEnum
public enum Unit
{
    /** Linear. */
    @XmlEnumValue("linear")
    LINEAR,

    /** A normalized value (0-1). */
    @XmlEnumValue("normalized")
    NORMALIZED,

    /** A percentage value (0-100). */
    @XmlEnumValue("percent")
    PERCENT,

    /** A decibel value (dB). */
    @XmlEnumValue("decibel")
    DECIBEL,

    /** A frequency value in Hertz. */
    @XmlEnumValue("hertz")
    HERTZ,

    /** A semi-tone value. */
    @XmlEnumValue("semitones")
    SEMITONES,

    /** A time value in seconds. */
    @XmlEnumValue("seconds")
    SECONDS,

    /** A time value in beats (quarter notes). */
    @XmlEnumValue("beats")
    BEATS,

    /** A beats-per-minute value. */
    @XmlEnumValue("bpm")
    BPM
}
