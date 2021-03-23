package dawproject;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlIDREF;

import java.util.List;

import dawproject.device.Device;
import jakarta.xml.bind.annotation.XmlList;
import jakarta.xml.bind.annotation.XmlRootElement;

/** Represents a sequencer track and/or mixer channel.  */
@XmlRootElement
public class Track extends AbstractTrack
{
   /** Role of this track in timelines & arranger.
    * An empty list implies this track is hidden in the arranger.
    * */
   @XmlAttribute(required = false)
   @XmlList()
   public TimelineRole[] timelineRole;

   /** Role of this track in the mixer
    * An empty list implies this track is hidden in the mixer.
    * */
   @XmlAttribute(required = false)
   public MixerRole mixerRole;

   @XmlAttribute(required = false)
   public Integer audioChannels = 2;

   @XmlElement(required = false)
   public RealParameter volume;

   @XmlElement(required = false)
   public RealParameter pan;

   @XmlElement(required = false)
   public BoolParameter mute;

   @XmlAttribute
   public Boolean loaded;

   @XmlAttribute(required = false)
   public Boolean solo;

   /** Output channel routing */
   @XmlIDREF
   @XmlAttribute()
   public Track destination;

   @XmlElementWrapper(name="sends")
   @XmlElement(name="send", type = Send.class)
   public List<Send> sends;

   @XmlElementWrapper(name="devices")
   @XmlElementRef
   public List<Device> devices;
}
