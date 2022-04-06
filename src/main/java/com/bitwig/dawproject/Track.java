package com.bitwig.dawproject;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlIDREF;
import jakarta.xml.bind.annotation.XmlList;
import jakarta.xml.bind.annotation.XmlRootElement;

/** Represents a sequencer track.  */
@XmlRootElement(name = "Track")
public class Track extends Lane
{
   /** Role of this track in timelines & arranger. */
   @XmlAttribute(required = false)
   @XmlList()
   public ContentType[] contentType;

   @XmlAttribute
   public Boolean loaded;

   @XmlElement(name = "Channel", required = false)
   public Channel channel;

   @XmlElementRef(name = "Tracks")
   public List<Track> tracks = new ArrayList<>();
}
