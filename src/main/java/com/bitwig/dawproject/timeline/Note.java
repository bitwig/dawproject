package com.bitwig.dawproject.timeline;

import com.bitwig.dawproject.DoubleAdapter;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
public class Note
{
   @XmlAttribute(required = true)
   @XmlJavaTypeAdapter(DoubleAdapter.class)
   public Double time;

   @XmlAttribute(required = true)
   @XmlJavaTypeAdapter(DoubleAdapter.class)
   public Double duration;

   @XmlAttribute(required = false)
   public int channel;

   @XmlAttribute(required = true)
   public int key;

   @XmlAttribute(name = "vel")
   @XmlJavaTypeAdapter(DoubleAdapter.class)
   public Double velocity;

   @XmlAttribute(name = "rel")
   @XmlJavaTypeAdapter(DoubleAdapter.class)
   public Double releaseVelocity;

   /** Per-note expressions can be stored within the note object as timelines. */
   @XmlElementRef(required = false)
   public Timeline content;
}
