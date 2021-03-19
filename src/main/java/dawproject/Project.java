package dawproject;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

import dawproject.timeline.AudioTimeline;
import dawproject.timeline.AutomationTimeline;
import dawproject.timeline.ClipTimeline;
import dawproject.timeline.NoteTimeline;
import dawproject.timeline.RootTimeline;
import dawproject.timeline.TrackTimeline;

@XmlRootElement(name = "project")
public class Project
{
   public Transport transport;

   @XmlElementWrapper(name="tracks")
   @XmlElement(name="track", type = Track.class)
   public List<Track> tracks = new ArrayList<>();

   @XmlElementWrapper(name="channels")
   @XmlElement(name="channel", type = Channel.class)
   public List<Channel> channels = new ArrayList<>();

   @XmlElement(name="arrangement", type = RootTimeline.class)
   public RootTimeline arrangement;

   @XmlElementWrapper(name="scenes")
   @XmlElement(name="scene", type = RootTimeline.class)
   public List<RootTimeline> scenes = new ArrayList<>();

   @XmlElementWrapper(name="track-timelines")
   @XmlElement(name="track-timeline", type = TrackTimeline.class)
   public List<TrackTimeline> trackTimelines = new ArrayList<>();

   public List<ClipTimeline> clipTimelines = new ArrayList<>();
   public List<NoteTimeline> noteTimelines = new ArrayList<>();
   public List<AudioTimeline> audioTimelines = new ArrayList<>();
   public List<AutomationTimeline> automationTimelines = new ArrayList<>();

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
