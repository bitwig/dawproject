package com.bitwig.dawproject.timeline;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Represent a clip launcher slot within a Scene which can contain a Clip. It is
 * generally set to a specific track.
 */
@XmlRootElement(name = "ClipSlot")
public class ClipSlot extends Timeline {
	/**
	 * Whether launching this slot should stop the track playback when this slot is
	 * empty.
	 */
	@XmlAttribute(required = false)
	public Boolean hasStop;

	/** Contained clip. */
	@XmlElement(name = "Clip", required = false)
	public Clip clip;
}
