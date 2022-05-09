package com.bitwig.dawproject.device;

import com.bitwig.dawproject.Parameter;
import com.bitwig.dawproject.RealParameter;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Compressor")
public class Compressor extends BuiltinDevice
{
   @XmlElement(name = "Threshold")
   public RealParameter threshold;

   @XmlElement(name = "Ratio")
   public RealParameter ratio;

   @XmlElement(name = "Attack")
   public RealParameter attack;

   @XmlElement(name = "Release")
   public RealParameter release;

   @XmlElement(name = "InputGain")
   public RealParameter inputGain;

   @XmlElement(name = "OutputGain")
   public RealParameter outputGain;
}
