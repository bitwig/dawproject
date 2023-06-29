package com.bitwig.dawproject.timeline;

import com.bitwig.dawproject.FileReference;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/** Similar to the Audio timeline but references a Video file instead. */
@XmlRootElement(name = "Video")
public class Video extends Timeline
{
   @XmlElement(name = "File", required = true)
   public FileReference file = new FileReference();

   /** duration in seconds/beats (TimeUnit dependent) of video-file */
   @XmlAttribute(required = true)
   public double duration;

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
