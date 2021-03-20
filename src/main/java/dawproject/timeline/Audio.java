package dawproject.timeline;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAttribute;

public class Audio extends Timeline
{
   /** relative path to audio-file within the container */
   @XmlAttribute
   public String path;

   /** duration in seconds of audio-file */
   @XmlAttribute
   public double duration;

   /** sample-rate of audio-file */
   @XmlAttribute
   public int samplerate;

   /** number of channels of audio-file (1=mono...) */
   @XmlAttribute
   public int channels;

   /** Markers describing the translation between beats and seconds for the audio-file. */

   public List<WarpMarker> warpMarkers;
}
