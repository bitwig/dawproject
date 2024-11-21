package com.bitwig.dawproject;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
/**
 * Represents a (the) time-signature parameter which can provide a value and be
 * used as an automation target.
 */
@XmlRootElement(name = "TimeSignatureParameter")
public class TimeSignatureParameter extends Parameter {
	/** Numerator of the time-signature. (3/4 &rarr; 3, 4/4 &rarr; 4) */
	@XmlAttribute(required = true)
	public Integer numerator;

	/** Denominator of the time-signature. (3/4 &rarr; 4, 7/8 &rarr; 8) */
	@XmlAttribute(required = true)
	public Integer denominator;
}
