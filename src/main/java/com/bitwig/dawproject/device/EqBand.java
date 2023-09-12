package com.bitwig.dawproject.device;

import com.bitwig.dawproject.BoolParameter;
import com.bitwig.dawproject.RealParameter;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

public class EqBand
{
   @XmlElement(name = "Freq", required = true)
   public RealParameter freq;

   @XmlElement(name = "Gain")
   public RealParameter gain;

   @XmlElement(name = "Q")
   public RealParameter Q;

   @XmlElement(name = "Enabled")
   public BoolParameter enabled;

   @XmlAttribute(required = false)
   public EqBandType type;

   @XmlAttribute
   public Integer order;
}
