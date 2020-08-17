package dawproject;

/** Represents the sequencer track of the DAW, */
public class Track extends DawObject
{
   /** A parent track (when present) can be used for visual organization of the tracks. */
   public ObjectReference<Track> parent;

   /** Target channel this track will play back into. */
   public ObjectReference<Channel> output;
}
