package dawproject.timeline;

import dawproject.Loop;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlIDREF;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "clip")
public class Clip extends EnduringEvent
{
   /** Time inside the target timeline where the clip starts playing. */
   @XmlAttribute
   public Double playStart;

   /** Duration of fade-in */
   @XmlAttribute
   public Double fadeInTime;

   /** Duration of fade-out */
   @XmlAttribute
   public Double fadeOutTime;

   @XmlElement
   public Loop loop = null;

   @XmlElementRef
   public Timeline content;

   @XmlAttribute
   @XmlIDREF
   public Timeline reference;
}
