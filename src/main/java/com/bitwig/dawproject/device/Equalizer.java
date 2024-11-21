package com.bitwig.dawproject.device;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import com.bitwig.dawproject.RealParameter;

@XmlRootElement(name = "Equalizer")
public class Equalizer extends BuiltinDevice {
	@XmlElement(name = "Band")
	public List<EqBand> bands = new ArrayList<>();

	@XmlElement(name = "InputGain")
	public RealParameter inputGain;

	@XmlElement(name = "OutputGain")
	public RealParameter outputGain;
}
