package com.bitwig.dawproject.timeline;

import com.bitwig.dawproject.FileReference;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/** Representation of an audio file as a timeline. Duration should be the entire length of the file, any clipping
 * should be done by placing the Audio element within a Clip element. The timeUnit attribute should always be set to
 * seconds. */

@XmlRootElement(name = "Audio")
public class Audio extends Timeline
{
   /** The file that should be played back. */
   @XmlElement(name = "File", required = true)
   public FileReference file = new FileReference();

   /** Duration in seconds of audio-file. */
   @XmlAttribute(required = true)
   public double duration;

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
