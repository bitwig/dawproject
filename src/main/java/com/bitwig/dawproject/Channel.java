package com.bitwig.dawproject;

import java.util.List;

import com.bitwig.dawproject.device.Device;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlIDREF;
import jakarta.xml.bind.annotation.XmlRootElement;


/**
 * Represents a mixer channel. It provides the ability to route signals to other channels and can
 * contain Device/Plug-in for processing.
 */
@XmlRootElement(name = "Channel")
public class Channel extends Lane
{
    /** Role of this channel in the mixer. */
    @XmlAttribute(required = false)
    public MixerRole     role;

    /** Number of audio-channels of this mixer channel. (1=mono, 2=stereo…) */
    @XmlAttribute(required = false)
    public Integer       audioChannels = Integer.valueOf (2);

    /** Channel volume */
    @XmlElement(name = "Volume", required = false)
    public RealParameter volume;

    /** Channel pan/balance */
    @XmlElement(name = "Pan", required = false)
    public RealParameter pan;

    /** Channel mute */
    @XmlElement(name = "Mute", required = false)
    public BoolParameter mute;

    /** Channel solo */
    @XmlAttribute(required = false)
    public Boolean       solo;

    /** Output channel routing */
    @XmlIDREF
    @XmlAttribute()
    public Channel       destination;

    /** Send levels & destination */
    @XmlElementWrapper(name = "Sends")
    @XmlElement(name = "Send", type = Send.class)
    public List<Send>    sends;

    /** Devices & plug-ins of this channel */
    @XmlElementWrapper(name = "Devices")
    @XmlElementRef
    public List<Device>  devices;
}
