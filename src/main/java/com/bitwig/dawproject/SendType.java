package com.bitwig.dawproject;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;

/** The type of a send. */
@XmlEnum
public enum SendType {
	/** A pre-fader send. */
	@XmlEnumValue("pre")
	PRE,

	/** A post-fader send. */
	@XmlEnumValue("post")
	POST
}
