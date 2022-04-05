package com.bitwig.dawproject;

import java.util.List;

import com.bitwig.dawproject.device.Device;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlIDREF;
import jakarta.xml.bind.annotation.XmlRootElement;

/** Represents a mixer channel.  */
@XmlRootElement(name = "Channel")
public class Channel extends Lane
{
   /** Role of this channel in the mixer. */
   @XmlAttribute(required = false)
   public MixerRole role;

   @XmlAttribute(required = false)
   public Integer audioChannels = 2;

   @XmlElement(name = "Volume", required = false)
   public RealParameter volume;

   @XmlElement(name = "Pan", required = false)
   public RealParameter pan;

   @XmlElement(name = "Mute", required = false)
   public BoolParameter mute;

   @XmlAttribute(required = false)
   public Boolean solo;

   /** Output channel routing */
   @XmlIDREF
   @XmlAttribute()
   public Channel destination;

   @XmlElementWrapper(name="Sends")
   @XmlElement(name="Send", type = Send.class)
   public List<Send> sends;

   @XmlElementWrapper(name="Devices")
   @XmlElementRef
   public List<Device> devices;
}
