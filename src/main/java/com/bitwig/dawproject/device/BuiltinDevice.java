package com.bitwig.dawproject.device;

import jakarta.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({Equalizer.class, Compressor.class, NoiseGate.class, Limiter.class})
public class BuiltinDevice extends Device
{
}
