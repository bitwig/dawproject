package dawproject;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlIDREF;
import java.util.ArrayList;
import java.util.List;

import dawproject.device.Device;

/** Represents a mixer channel. May represent the same entity as the Track, depending on the DAW */
public class Channel extends Referencable
{
   /* When true, this MixerChannel represents the same user object as a Track. */
   @XmlAttribute
   public boolean belongsToTrack;

   /** Role of this channel (regular/return/master) */
   @XmlAttribute
   public ChannelRole role;

   @XmlAttribute
   public int audioChannels = 2;

   public RealParameter volume;

   public RealParameter pan;

   public BoolParameter mute;

   /** Output channel routing */
   @XmlIDREF
   @XmlAttribute()
   public Channel destination;

   @XmlElementWrapper(name="sends")
   @XmlElement(name="send", type = Send.class)
   public List<Send> sends;

   @XmlElementWrapper(name="devices")
   @XmlElementRef
   public List<Device> devices = new ArrayList<>();
}
