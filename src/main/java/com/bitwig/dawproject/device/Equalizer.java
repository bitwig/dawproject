package com.bitwig.dawproject.device;

import java.util.ArrayList;
import java.util.List;

import com.bitwig.dawproject.RealParameter;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Equalizer extends BuiltinDevice
{
   @XmlElementWrapper(name="bands")
   @XmlElement(name="band")
   public List<EqBand> bands = new ArrayList<>();

   @XmlElement
   public RealParameter inputGain;

   @XmlElement
   public RealParameter outputGain;
}
