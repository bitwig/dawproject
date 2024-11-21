package com.bitwig.dawproject;

import jakarta.xml.bind.annotation.XmlElement;

/**
 * Transport element containing playback parameters such as Tempo and
 * Time-signature.
 */
public class Transport {
	/** Tempo parameter for setting and/or automating the tempo. */
	@XmlElement(name = "Tempo")
	public RealParameter tempo;

	/** Time-signature parameter. */
	@XmlElement(name = "TimeSignature")
	public TimeSignatureParameter timeSignature;
}
