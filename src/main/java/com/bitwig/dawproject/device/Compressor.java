package com.bitwig.dawproject.device;

import com.bitwig.dawproject.BoolParameter;
import com.bitwig.dawproject.RealParameter;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;


/**
 * A generic 'built-in' compressor.
 */
@XmlRootElement(name = "Compressor")
public class Compressor extends BuiltinDevice
{
    /** The threshold setting of the compressor in dB. */
    @XmlElement(name = "Threshold")
    public RealParameter threshold;

    /** The ratio setting of the compressor in percent (0-100). */
    @XmlElement(name = "Ratio")
    public RealParameter ratio;

    /** The attack setting of the compressor in seconds. */
    @XmlElement(name = "Attack")
    public RealParameter attack;

    /** The release setting of the compressor in seconds. */
    @XmlElement(name = "Release")
    public RealParameter release;

    /** Pre-compression gain stage. (input/gain/drive) in dB. */
    @XmlElement(name = "InputGain")
    public RealParameter inputGain;

    /** Post-compression gain stage. (output/makeup gain) in dB. */
    @XmlElement(name = "OutputGain")
    public RealParameter outputGain;

    /** Should auto makeup be applied? */
    @XmlElement(name = "AutoMakeup")
    public BoolParameter autoMakeup;
}
