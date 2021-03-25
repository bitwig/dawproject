package com.bitwig.dawproject.timeline;

import com.bitwig.dawproject.ExpressionType;
import com.bitwig.dawproject.Parameter;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlIDREF;

public class AutomationTarget
{
   @XmlIDREF
   @XmlAttribute(required = false)
   public Parameter parameter;

   @XmlAttribute(required = false)
   public ExpressionType expression;

   /** MIDI channel */
   @XmlAttribute(required = false)
   public Integer channel;

   /** MIDI key. Used when expression="polyPressure". */
   @XmlAttribute(required = false)
   public Integer key;

   /** MIDI key. Used when expression="channelController". */
   @XmlAttribute(required = false)
   public Integer controller;
}
