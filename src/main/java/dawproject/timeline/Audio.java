package dawproject.timeline;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Audio extends Timeline
{
   /** relative path to audio-file within the container */
   @XmlAttribute(required = true)
   public String path;

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
