package com.bitwig.dawproject;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Represents a parameter which can provide a boolean (true/false) value and be
 * used as an automation target.
 */
@XmlRootElement(name = "BoolParameter")
public class BoolParameter extends Parameter {
	/** Boolean value for this parameter. */
	@XmlAttribute(required = false)
	public Boolean value;
}
