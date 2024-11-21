package com.bitwig.dawproject.timeline;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Representation of a video file as a timeline. Duration should be the entire
 * length of the file, any clipping should be done by placing the Audio element
 * within a Clip element. The timeUnit attribute should always be set to
 * seconds.
 */
@XmlRootElement(name = "Video")
public class Video extends MediaFile {
	/** sample-rate of audio (if present) */
	@XmlAttribute(required = false)
	public int sampleRate;

	/** number of channels of audio (1=mono..., if present) */
	@XmlAttribute(required = false)
	public int channels;

	/** Playback algorithm used to warp audio (vendor-specific, if present) */
	@XmlAttribute(required = false)
	public String algorithm;
}
