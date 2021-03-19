package dawproject.timeline;

import dawproject.Loop;
import jakarta.xml.bind.annotation.XmlIDREF;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "clip")
public class Clip extends EnduringEvent
{
   /** Time inside the target timeline where the clip starts playing. */
   public double playStart;

   /** Duration of fade-in */
   public double fadeInTime;

   /** Duration of fade-out */
   public double fadeOutTime;

   public Loop loop = null;

   @XmlIDREF
   public Timeline content;
}
