package com.bitwig.dawproject.device;

import com.bitwig.dawproject.RealParameter;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;


/**
 * A generic 'built-in' limiter.
 */
@XmlRootElement(name = "Limiter")
public class Limiter extends BuiltinDevice
{
    /** The threshold setting of the limiter in dB. */
    @XmlElement(name = "Threshold")
    public RealParameter threshold;

    /** The input gain of the limiter in dB. */
    @XmlElement(name = "InputGain")
    public RealParameter inputGain;

    /** The output gain of the limiter in dB. */
    @XmlElement(name = "OutputGain")
    public RealParameter outputGain;

    /** The attack setting of the limiter in seconds. */
    @XmlElement(name = "Attack")
    public RealParameter attack;

    /** The release setting of the limiter in seconds. */
    @XmlElement(name = "Release")
    public RealParameter release;
}
