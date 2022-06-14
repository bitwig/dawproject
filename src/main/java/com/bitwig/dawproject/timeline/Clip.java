package com.bitwig.dawproject.timeline;

import com.bitwig.dawproject.Nameable;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlIDREF;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Clip")
public class Clip extends Nameable
{
   /** Time on the parent timeline where this clips starts playing. */
   @XmlAttribute(required = true)
   public double time;

   /** Duration on the parent timeline of this clip. */
   @XmlAttribute(required = true)
   public double duration;

   /** The TimeUnit used by the scope inside this timeline. This affects the content/reference, playStart, playStop,
    *  loopStart, loopEnd but not time and duration which are using the TimeUnit of the parent scope. */
   @XmlAttribute(required = false)
   public TimeUnit timeUnit;

   /** Time inside the content timeline (or reference) where the clip starts playing. */
   @XmlAttribute(required = false)
   public Double playStart;

   /** Time inside the content timeline (or reference) where the clip stops playing. */
   @XmlAttribute(required = false)
   public Double playStop;

   @XmlAttribute(required = false)
   public Double loopStart;

   @XmlAttribute(required = false)
   public Double loopEnd;

   /** The TimeUnit used by the fadeInTime and fadeOutTime. */
   @XmlAttribute(required = false)
   public TimeUnit fadeTimeUnit;

   /** Duration of fade-in */
   @XmlAttribute(required = false)
   public Double fadeInTime;

   /** Duration of fade-out */
   @XmlAttribute(required = false)
   public Double fadeOutTime;

   /** Content Timeline this clip is playing. */
   @XmlElementRef(required = false)
   public Timeline content;

   /**
    * Reference to a Content Timeline this clip is playing, in case of linked/alias clips. You can use either content
    * or reference for one clip, but not both.
    */
   @XmlAttribute(required = false)
   @XmlIDREF
   public Timeline reference;
}
