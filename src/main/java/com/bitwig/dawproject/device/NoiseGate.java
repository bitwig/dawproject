package com.bitwig.dawproject.device;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import com.bitwig.dawproject.RealParameter;

@XmlRootElement(name = "NoiseGate")
public class NoiseGate extends BuiltinDevice {
	@XmlElement(name = "Threshold")
	public RealParameter threshold;

	@XmlElement(name = "Ratio")
	public RealParameter ratio;

	@XmlElement(name = "Attack")
	public RealParameter attack;

	@XmlElement(name = "Release")
	public RealParameter release;

	/** Range or amount of maximum gain reduction. Possible range [-inf to 0] */
	@XmlElement(name = "Range")
	public RealParameter range;
}
