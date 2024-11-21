package com.bitwig.dawproject.timeline;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlIDREF;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;

import com.bitwig.dawproject.Referenceable;
import com.bitwig.dawproject.Track;

/** Abstract base class for all timeline structures. */
@XmlRootElement(name = "Timeline")
@XmlSeeAlso({Note.class, Notes.class, Lanes.class, Clip.class, Clips.class, ClipSlot.class, Marker.class, Markers.class,
		Warps.class, Audio.class, Video.class, Point.class, Points.class})
public abstract class Timeline extends Referenceable {
	/** When present, the timeline is local to this track. */
	@XmlAttribute(required = false)
	@XmlIDREF
	public Track track;

	/**
	 * The TimeUnit used by this and nested timelines. If no TimeUnit is provided by
	 * this or the parent scope then 'beats' will be used.
	 */
	@XmlAttribute(required = false)
	public TimeUnit timeUnit;
}
