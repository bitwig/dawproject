package dawproject;

import java.util.List;

public class AudioTimeline extends Timeline
{
   /** relative path to audio-file within the container */
   public String audioFile;

   /** duration in seconds of audio-file */
   public double duration;

   /** sample-rate of audio-file */
   public int sampleRate;

   /** number of channels of audio-file (1=mono...) */
   public int channelCount;

   /** Markers describing the translation between beats and seconds for the audio-file. */
   public List<WarpMarker> warpMarkers;
}
