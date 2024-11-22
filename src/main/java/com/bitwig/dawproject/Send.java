package com.bitwig.dawproject;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlIDREF;

/** A single send of a mixer channel. */
public class Send extends Referenceable {
	/** Send level. */
	@XmlElement(required = true, name = "Volume")
	public RealParameter volume;

	/** Send pan/balance. */
	@XmlElement(required = false, name = "Pan")
	public RealParameter pan;

	/** Send enable. */
	@XmlElement(required = false, name = "Enable")
	public BoolParameter enable;

	/** Send type. */
	@XmlAttribute
	public SendType type = SendType.POST;

	/** Send destination. */
	@XmlAttribute
	@XmlIDREF
	public Channel destination;
}
