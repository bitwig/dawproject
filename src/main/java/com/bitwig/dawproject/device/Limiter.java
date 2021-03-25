package com.bitwig.dawproject.device;

import com.bitwig.dawproject.RealParameter;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Limiter extends BuiltinDevice
{
   @XmlElement
   public RealParameter threshold;

   @XmlElement
   public RealParameter inputGain;

   @XmlElement
   public RealParameter outputGain;

   @XmlElement
   public RealParameter attack;

   @XmlElement
   public RealParameter release;
}
