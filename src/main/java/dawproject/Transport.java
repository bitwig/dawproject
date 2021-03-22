package dawproject;

import jakarta.xml.bind.annotation.XmlElement;

public class Transport
{
   @XmlElement
   public RealParameter tempo;

   @XmlElement
   public TimeSignatureParameter timeSignature;
}
