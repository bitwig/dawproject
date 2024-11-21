package com.bitwig.dawproject.timeline;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.bitwig.dawproject.DoubleAdapter;

/**
 * A single Note (MIDI-style). It can additionally contain child timelines to
 * hold per-note expression.
 */
@XmlRootElement(name = "Note")
public final class Note {
	/** Time on the parent timeline where this note starts playing. */
	@XmlAttribute(required = true)
	@XmlJavaTypeAdapter(DoubleAdapter.class)
	public Double time;

	/** Duration on the parent timeline of this note. */
	@XmlAttribute(required = true)
	@XmlJavaTypeAdapter(DoubleAdapter.class)
	public Double duration;

	/** MIDI channel of this note. */
	@XmlAttribute(required = false)
	public int channel;

	/** MIDI key of this note. */
	@XmlAttribute(required = true)
	public int key;

	/** Note On Velocity of this note. (normalized) */
	@XmlAttribute(name = "vel")
	@XmlJavaTypeAdapter(DoubleAdapter.class)
	public Double velocity;

	/** Note Off Velocity of this note. (normalized) */
	@XmlAttribute(name = "rel")
	@XmlJavaTypeAdapter(DoubleAdapter.class)
	public Double releaseVelocity;

	/** Per-note expressions can be stored within the note object as timelines. */
	@XmlElementRef(name = "Content", required = false)
	public Timeline content;
}
