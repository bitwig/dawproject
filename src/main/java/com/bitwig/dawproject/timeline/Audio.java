package com.bitwig.dawproject.timeline;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Representation of an audio file as a timeline. Duration should be the entire
 * length of the file, any clipping should be done by placing the Audio element
 * within a Clip element. The timeUnit attribute should always be set to
 * seconds.
 */

@XmlRootElement(name = "Audio")
public class Audio extends MediaFile {
	/** Sample-rate of audio-file. */
	@XmlAttribute(required = true)
	public int sampleRate;

	/** Number of channels of audio-file (1=mono, 2=stereo...). */
	@XmlAttribute(required = true)
	public int channels;

	/** Playback algorithm used to warp audio (vendor-specific). */
	@XmlAttribute(required = false)
	public String algorithm;
}
