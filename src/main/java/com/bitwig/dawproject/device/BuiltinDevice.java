package com.bitwig.dawproject.device;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name = "BuiltinDevice")
@XmlSeeAlso({Equalizer.class, Compressor.class, NoiseGate.class, Limiter.class})
public class BuiltinDevice extends Device {
}
