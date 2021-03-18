package dawproject;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.ArrayList;
import java.util.List;

import dawproject.device.Device;

/** Represents a mixer channel. May represent the same entity as the Track, depending on the DAW */
public class Channel extends DawObject
{
   /* When true, this Channel represents the same user object as a Track. */
   @XmlAttribute
   public boolean isTrackChannel;

   @XmlAttribute
   public Integer audioChannels;

   public RealParameter Volume;

   /** Track pan [-1 ... 1] */
   public RealParameter Pan;

   public BoolParameter Mute;

   public List<Send> Sends;

   public List<ObjectReference<Device>> devices = new ArrayList<>();

   /** Output channel routing */
   @XmlIDREF
   @XmlAttribute()
   public Channel output;
}
