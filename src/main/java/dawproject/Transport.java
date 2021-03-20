package dawproject;

import jakarta.xml.bind.annotation.XmlElement;

public class Transport
{
   @XmlElement
   public RealParameter tempo;

   public TimeSignature signature = new TimeSignature();

   @XmlElement
   public Loop loop;
}
