package com.bitwig.dawproject.timeline;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlIDREF;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "clip")
public class Clip
{
   @XmlAttribute(required = true)
   public double time;

   @XmlAttribute(required = true)
   public double duration;

   @XmlAttribute(required = false)
   public String name;

   @XmlAttribute(required = false)
   public String color;

   /** Time inside the target timeline where the clip starts playing. */
   @XmlAttribute(required = false)
   public Double playStart;

   @XmlAttribute(required = false)
   public Double loopStart;

   @XmlAttribute(required = false)
   public Double loopEnd;

   /** Duration of fade-in */
   @XmlAttribute(required = false)
   public Double fadeInTime;

   /** Duration of fade-out */
   @XmlAttribute(required = false)
   public Double fadeOutTime;

   @XmlElementRef(required = false)
   public Timeline content;

   @XmlAttribute(required = false)
   @XmlIDREF
   public Timeline reference;
}
