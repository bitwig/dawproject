package com.bitwig.dawproject;

import jakarta.xml.bind.annotation.XmlAttribute;

/** Base class for everything with a name. */
public abstract class Nameable {
	/** Name/label of this object. */
	@XmlAttribute
	public String name;

	/** Color of this object in HTML-style format. (#rrggbb) */
	@XmlAttribute
	public String color;

	/** Comment/description of this object. */
	@XmlAttribute
	public String comment;
}
