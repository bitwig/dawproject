package com.bitwig.dawproject.timeline;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/** Represents a timeline of cue-markers. */
@XmlRootElement
public class Markers extends Timeline {
	/** Markers of this timeline. */
	@XmlElement(required = true, name = "Marker")
	public List<Marker> markers = new ArrayList<>();
}
