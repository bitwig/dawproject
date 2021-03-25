package com.bitwig.dawproject.device;

import com.bitwig.dawproject.RealParameter;
import jakarta.xml.bind.annotation.XmlElement;

public class EqBand
{
   @XmlElement(required = true)
   public RealParameter freq;

   @XmlElement
   public RealParameter gain;

   @XmlElement
   public RealParameter Q;

   @XmlElement
   public EqBandType type = EqBandType.peak;
}
