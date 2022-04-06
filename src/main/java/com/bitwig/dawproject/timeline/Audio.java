package com.bitwig.dawproject.timeline;

import com.bitwig.dawproject.FileReference;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Audio")
public class Audio extends Timeline
{
   @XmlElement(name = "File", required = true)
   public FileReference file = new FileReference();

   /** duration in seconds/beats (timebase) of audio-file */
   @XmlAttribute(required = true)
   public double duration;

   /** sample-rate of audio-file */
   @XmlAttribute(required = true)
   public int samplerate;

   /** number of channels of audio-file (1=mono...) */
   @XmlAttribute(required = true)
   public int channels;

   /** Playback algorithm used to warp audio (vendor-specific) */
   @XmlAttribute(required = false)
   public String algorithm;
}
