package com.bitwig.dawproject.device;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import com.bitwig.dawproject.RealParameter;

/** A generic 'built-in' noise gate. */
@XmlRootElement(name = "NoiseGate")
public class NoiseGate extends BuiltinDevice {
	/** The threshold of the noise gate in dB. */
	@XmlElement(name = "Threshold")
	public RealParameter threshold;

	/** The ratio of the noise gate in percent (0-100). */
	@XmlElement(name = "Ratio")
	public RealParameter ratio;

	/** The attack of the noise gate in seconds. */
	@XmlElement(name = "Attack")
	public RealParameter attack;

	/** The release of the noise gate in seconds. */
	@XmlElement(name = "Release")
	public RealParameter release;

	/** Range or amount of maximum gain reduction. Possible range [-inf to 0] */
	@XmlElement(name = "Range")
	public RealParameter range;
}
