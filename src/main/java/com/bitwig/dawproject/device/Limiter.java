package com.bitwig.dawproject.device;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import com.bitwig.dawproject.RealParameter;

@XmlRootElement(name = "Limiter")
public class Limiter extends BuiltinDevice {
	@XmlElement(name = "Threshold")
	public RealParameter threshold;

	@XmlElement(name = "InputGain")
	public RealParameter inputGain;

	@XmlElement(name = "OutputGain")
	public RealParameter outputGain;

	@XmlElement(name = "Attack")
	public RealParameter attack;

	@XmlElement(name = "Release")
	public RealParameter release;
}
