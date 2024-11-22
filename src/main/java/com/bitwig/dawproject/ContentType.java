package com.bitwig.dawproject;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;

/** The type of the content. */
@XmlEnum
public enum ContentType {
	/** Audio content. */
	@XmlEnumValue("audio")
	AUDIO,

	/** Automation content. */
	@XmlEnumValue("automation")
	AUTOMATION,

	/** Notes content. */
	@XmlEnumValue("notes")
	NOTES,

	/** Video content. */
	@XmlEnumValue("video")
	VIDEO,

	/** Markers content. */
	@XmlEnumValue("markers")
	MARKERS,

	/** Tracks content. */
	@XmlEnumValue("tracks")
	TRACKS
}
