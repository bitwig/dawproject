package dawproject;

import java.util.ArrayList;
import java.util.List;

import dawproject.timeline.RootTimeline;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

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

}
