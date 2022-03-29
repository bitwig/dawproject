package com.bitwig.dawproject;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlIDREF;

public class Send extends Referenceable
{
   @XmlElement(required = true)
   public RealParameter volume;

   @XmlElement(required = false)
   public RealParameter pan;

   @XmlAttribute
   public SendType type = SendType.post;

   @XmlAttribute
   @XmlIDREF
   public Channel destination;
}
