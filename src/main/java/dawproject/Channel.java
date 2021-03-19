package dawproject;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlIDREF;
import jakarta.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.List;

import dawproject.device.Device;

/** Represents a mixer channel. May represent the same entity as the Track, depending on the DAW */
public class Channel extends DawObject
{
   /* When true, this Channel represents the same user object as a Track. */
   @XmlTransient
   public boolean isTrackChannel;

   @XmlAttribute
   public int audioChannels = 2;

   public RealParameter volume;

   public RealParameter pan;

   public BoolParameter mute;

   /** Output channel routing */
   @XmlIDREF
   @XmlAttribute()
   public Channel destination;

   @XmlElementWrapper(name="Sends")
   @XmlElement(name="Send", type = Send.class)
   public List<Send> Sends;

   @XmlElementWrapper(name="Devices")
   @XmlElement(name="Device", type = Device.class)
   public List<Device> devices = new ArrayList<>();
}
