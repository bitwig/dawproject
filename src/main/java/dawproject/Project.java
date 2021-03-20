package dawproject;

import java.util.ArrayList;
import java.util.List;

import dawproject.device.Device;
import dawproject.timeline.Timeline;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name = "project")
@XmlSeeAlso({Device.class, Timeline.class})
public class Project
{
   @XmlElement
   public Transport transport;

   @XmlAttribute
   public String application;

   @XmlAttribute
   public String version;

   @XmlElementWrapper(name="tracks")
   @XmlElement(name="track", type = Track.class)
   public List<Track> tracks = new ArrayList<>();

   @XmlElementWrapper(name="channels")
   @XmlElement(name="channel", type = Channel.class)
   public List<Channel> channels = new ArrayList<>();

   @XmlElementRef(name="arrangement", type = Timeline.class)
   public Timeline arrangement;

   @XmlElementWrapper(name="scenes")
   @XmlElement(name="scene", type = Timeline.class)
   public List<Timeline> scenes = new ArrayList<>();
}
