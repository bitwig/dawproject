package dawproject;

import java.util.ArrayList;
import java.util.List;

import dawproject.device.Device;

/** Represents a mixer channel. May represent the same entity as the Track, depending on the DAW */
public class Channel extends DawObject
{
   /* When true, this Channel represents the same user object as a Track. */
   public boolean isTrackChannel;

   public Integer audioChannels;

   /** Track volume (linear) */
   public double volume = 1;

   /** Track pan [-1 ... 1] */
   public double pan;

   public boolean mute = false;

   public List<Send> sends;

   public List<ObjectReference<Device>> devices = new ArrayList<>();

   public ObjectReference<Channel> output;
}
