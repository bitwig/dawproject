package com.bitwig.dawproject.device;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

import com.bitwig.dawproject.BoolParameter;
import com.bitwig.dawproject.RealParameter;


/** The parameters of a band of a generic 'built-in' equalizer. */
public class EqBand
{
    /** The frequency setting of the band. */
    @XmlElement(name = "Freq", required = true)
    public RealParameter freq;

    /** The gain setting of the band. */
    @XmlElement(name = "Gain")
    public RealParameter gain;

    /** The Q setting of the band. */
    @XmlElement(name = "Q")
    public RealParameter Q;

    /** The enabled state of the band. */
    @XmlElement(name = "Enabled")
    public BoolParameter enabled;

    /** The filter type of the band. */
    @XmlAttribute(required = true)
    public EqBandType    type;

    /** The index of the band. */
    @XmlAttribute
    public Integer       order;
}
