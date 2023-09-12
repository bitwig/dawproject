package com.bitwig.dawproject.device;

import com.bitwig.dawproject.BoolParameter;
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

   /** Pre-compression gain stage. (input/gain/drive) */
   @XmlElement(name = "InputGain")
   public RealParameter inputGain;

   /** Post-compression gain stage. (output/makeup gain) */
   @XmlElement(name = "OutputGain")
   public RealParameter outputGain;

   @XmlElement(name = "AutoMakeup")
   public BoolParameter autoMakeup;
}
