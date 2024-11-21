package com.bitwig.dawproject;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Represents an enumerated parameter which can provide a value and be used as
 * an automation target.
 */
@XmlRootElement(name = "IntegerParameter")
public class IntegerParameter extends Parameter {
	/** Integer value for this parameter. */
	@XmlAttribute
	public Integer value;

	/** Minimum value this parameter can have (inclusive). */
	@XmlAttribute
	public Integer min;

	/** Maximum value this parameter can have (inclusive). */
	@XmlAttribute
	public Integer max;
}
