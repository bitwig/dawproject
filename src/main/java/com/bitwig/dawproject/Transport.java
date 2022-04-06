package com.bitwig.dawproject;

import jakarta.xml.bind.annotation.XmlElement;

public class Transport
{
   @XmlElement(name = "Tempo")
   public RealParameter tempo;

   @XmlElement(name = "TimeSignature")
   public TimeSignatureParameter timeSignature;
}
