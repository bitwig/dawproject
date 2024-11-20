package com.bitwig.dawproject.device;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import com.bitwig.dawproject.RealParameter;


/** A generic 'built-in' equalizer. */
@XmlRootElement(name = "Equalizer")
public class Equalizer extends BuiltinDevice
{
    /** The bands of the equalizer. */
    @XmlElement(name = "Band")
    public List<EqBand>  bands = new ArrayList<> ();

    /** The input gain of the equalizer in dB. */
    @XmlElement(name = "InputGain")
    public RealParameter inputGain;

    /** The output gain of the equalizer in dB. */
    @XmlElement(name = "OutputGain")
    public RealParameter outputGain;
}
