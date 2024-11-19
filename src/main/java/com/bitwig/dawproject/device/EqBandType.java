package com.bitwig.dawproject.device;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;


/**
 * A type of a band of a generic 'built-in' equalizer.
 */
@XmlEnum
public enum EqBandType
{
    /** A high pass filter. */
    @XmlEnumValue("highPass")
    HIGH_PASS,
    /** A low pass filter. */
    @XmlEnumValue("lowPass")
    LOW_PASS,
    /** A band pass filter. */
    @XmlEnumValue("bandPass")
    BAND_PASS,
    /** A high shelf filter. */
    @XmlEnumValue("highShelf")
    HIGH_SHELF,
    /** A low shelf filter. */
    @XmlEnumValue("lowShelf")
    LOW_SHELF,
    /** A bell filter. */
    @XmlEnumValue("bell")
    BELL,
    /** A notch filter. */
    @XmlEnumValue("notch")
    NOTCH
}
