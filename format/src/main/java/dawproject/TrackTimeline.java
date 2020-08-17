package dawproject;

/** Associates a timeline with a track. */
public class TrackTimeline extends Timeline
{
   public ObjectReference<Track> track;
   public ObjectReference<Timeline> timeline;
}
