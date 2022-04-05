package com.bitwig.dawproject;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement
@XmlSeeAlso({RealParameter.class, BoolParameter.class, IntegerParameter.class, EnumParameter.class, TimeSignatureParameter.class})
public abstract class Parameter extends Referenceable
{

   /** Parameter ID as used by VST2 (index), VST3(ParamID) */
   @XmlAttribute(required = false)
   public Integer parameterID;
}
