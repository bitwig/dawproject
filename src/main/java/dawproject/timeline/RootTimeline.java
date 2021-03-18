package dawproject.timeline;

import java.util.ArrayList;
import java.util.List;

import dawproject.ObjectReference;

/** Timeline which combines */
public class RootTimeline extends Timeline
{
   /** Cue markers */
   public List<Event> markers;

   /** Lanes representing nested content */
   public List<ObjectReference<Timeline>> lanes = new ArrayList<>();
}
