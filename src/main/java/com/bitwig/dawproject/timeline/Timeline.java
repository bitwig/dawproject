package com.bitwig.dawproject.timeline;

import com.bitwig.dawproject.Referencable;
import com.bitwig.dawproject.Track;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlIDREF;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement
@XmlSeeAlso({Note.class, Notes.class, Lanes.class, Clip.class, Clips.class, ClipSlot.class, Marker.class,
   Markers.class, Warps.class, Audio.class, Video.class, Point.class, Points.class})
public abstract class Timeline extends Referencable
{
   /** When present, the timeline is local to this track. */
   @XmlAttribute(required = false)
   @XmlIDREF
   public Track track;

   /** The Timebase used by this and nested timelines */
   @XmlAttribute(required = false)
   public Timebase timebase;
}
