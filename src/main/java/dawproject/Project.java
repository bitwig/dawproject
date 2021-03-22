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
   public static String CURRENT_VERSION = "0.1";

   @XmlAttribute(required = true)
   public String version = CURRENT_VERSION;

   @XmlElement(required = true)
   public Application application = new Application();

   @XmlElement
   public Transport transport;

   @XmlElementWrapper(name="tracks")
   @XmlElement(name="track", type = Track.class)
   public List<Track> tracks = new ArrayList<>();

   @XmlElementWrapper(name="channels")
   @XmlElement(name="channel", type = Channel.class)
   public List<Channel> channels = new ArrayList<>();

   @XmlElementRef(name="arrangement", type = Arrangement.class, required = false)
   public Arrangement arrangement;

   @XmlElementWrapper(name="scenes")
   @XmlElementRef
   public List<Scene> scenes = new ArrayList<>();
}
