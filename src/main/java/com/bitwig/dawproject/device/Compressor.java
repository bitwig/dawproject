package com.bitwig.dawproject.device;

import com.bitwig.dawproject.Parameter;
import com.bitwig.dawproject.RealParameter;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Compressor extends BuiltinDevice
{
   @XmlElement
   public Parameter threshold;

   @XmlElement
   public Parameter ratio;

   @XmlElement
   public Parameter attack;

   @XmlElement
   public Parameter release;

   @XmlElement
   public RealParameter inputGain;

   @XmlElement
   public RealParameter outputGain;
}
