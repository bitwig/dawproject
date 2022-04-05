package com.bitwig.dawproject.device;

import com.bitwig.dawproject.Parameter;
import com.bitwig.dawproject.RealParameter;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Compressor")
public class Compressor extends BuiltinDevice
{
   @XmlElement(name = "Threshold")
   public Parameter threshold;

   @XmlElement(name = "Ratio")
   public Parameter ratio;

   @XmlElement(name = "Attack")
   public Parameter attack;

   @XmlElement(name = "Release")
   public Parameter release;

   @XmlElement(name = "InputGain")
   public RealParameter inputGain;

   @XmlElement(name = "OutputGain")
   public RealParameter outputGain;
}
