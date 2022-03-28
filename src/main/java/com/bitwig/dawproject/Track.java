package com.bitwig.dawproject;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

import jakarta.xml.bind.annotation.XmlList;
import jakarta.xml.bind.annotation.XmlRootElement;

/** Represents a sequencer track.  */
@XmlRootElement
public class Track extends TrackOrFolder
{
   /** Role of this track in timelines & arranger. */
   @XmlAttribute(required = false)
   @XmlList()
   public ContentType[] contentType;

   @XmlAttribute
   public Boolean loaded;

   @XmlElement(name = "channel", required = true)
   public Channel channel;
}
