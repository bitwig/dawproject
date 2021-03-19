package dawproject;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

import dawproject.device.Device;
import dawproject.timeline.AudioTimeline;
import dawproject.timeline.AutomationTimeline;
import dawproject.timeline.ClipTimeline;
import dawproject.timeline.NoteTimeline;
import dawproject.timeline.RootTimeline;
import dawproject.timeline.TrackTimeline;

@XmlRootElement(name = "Project")
public class Project
{
   public Transport transport;

   @XmlElementWrapper(name="Tracks")
   @XmlElement(name="Track", type = Track.class)
   public List<Track> tracks = new ArrayList<>();

   @XmlElementWrapper(name="Channels")
   @XmlElement(name="Channel", type = Channel.class)
   public List<Channel> channels = new ArrayList<>();

   @XmlElement(name="Arrangement", type = RootTimeline.class)
   public RootTimeline arrangement;

   @XmlElementWrapper(name="Scenes")
   @XmlElement(name="Scene", type = RootTimeline.class)
   public List<RootTimeline> scenes = new ArrayList<>();

   public List<TrackTimeline> trackTimelines = new ArrayList<>();
   public List<ClipTimeline> clipTimelines = new ArrayList<>();
   public List<NoteTimeline> noteTimelines = new ArrayList<>();
   public List<AudioTimeline> audioTimelines = new ArrayList<>();
   public List<AutomationTimeline> automationTimelines = new ArrayList<>();

   /* Utility methods below */

   public <T extends Object> ObjectReference<T> createReference(T object)
   {
      if (object instanceof Transport)
         return new ObjectReference<>(Location.transport, 0);
      else if (object instanceof Track)
         return new ObjectReference<>(Location.tracks, tracks.indexOf(object));
      else if (object instanceof Channel)
         return new ObjectReference<>(Location.channels, channels.indexOf(object));
      else if (object instanceof TrackTimeline)
         return new ObjectReference<>(Location.trackTimelines, trackTimelines.indexOf(object));
      else if (object instanceof ClipTimeline)
         return new ObjectReference<>(Location.clipTimelines, clipTimelines.indexOf(object));
      else if (object instanceof NoteTimeline)
         return new ObjectReference<>(Location.noteTimelines, noteTimelines.indexOf(object));
      else if (object instanceof AudioTimeline)
         return new ObjectReference<>(Location.audioTimelines, audioTimelines.indexOf(object));
      else if (object instanceof AutomationTimeline)
         return new ObjectReference<>(Location.automationTimelines, automationTimelines.indexOf(object));

      return null;
   }

   public <T extends Object> T get(ObjectReference<T> ref)
   {
      switch (ref.location)
      {
         case transport:
            return (T)this.transport;
         case tracks:
            return (T)tracks.get(ref.index);
         case channels:
            return (T)channels.get(ref.index);
         case trackTimelines:
            return (T)trackTimelines.get(ref.index);
         case clipTimelines:
            return (T)clipTimelines.get(ref.index);
         case noteTimelines:
            return (T)noteTimelines.get(ref.index);
         case audioTimelines:
            return (T)audioTimelines.get(ref.index);
         case automationTimelines:
            return (T)automationTimelines.get(ref.index);
      }
      return null;
   }

   public Track createTrack()
   {
      var track = new Track();
      tracks.add(track);
      return track;
   }

   public Channel createChannel()
   {
      var channel = new Channel();
      channels.add(channel);
      return channel;
   }

   public Device createDevice()
   {
      var device = new Device();
      return device;
   }

   public TrackTimeline createTrackTimeline()
   {
      var timeline = new TrackTimeline();
      trackTimelines.add(timeline);
      return timeline;
   }

   public ClipTimeline createClipTimeline()
   {
      var timeline = new ClipTimeline();
      clipTimelines.add(timeline);
      return timeline;
   }

   public NoteTimeline createNoteTimeline()
   {
      var timeline = new NoteTimeline();
      noteTimelines.add(timeline);
      return timeline;
   }

   public AudioTimeline createAudioTimeline()
   {
      var timeline = new AudioTimeline();
      audioTimelines.add(timeline);
      return timeline;
   }

   public AutomationTimeline createAutomationTimeline()
   {
      var timeline = new AutomationTimeline();
      automationTimelines.add(timeline);
      return timeline;
   }
}
