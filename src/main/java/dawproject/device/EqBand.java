package dawproject.device;

import dawproject.RealParameter;
import jakarta.xml.bind.annotation.XmlElement;

public class EqBand
{
   @XmlElement
   public RealParameter freq;

   @XmlElement
   public RealParameter gain;

   @XmlElement
   public RealParameter Q;

   @XmlElement
   public EqBandType type = EqBandType.peak;
}
