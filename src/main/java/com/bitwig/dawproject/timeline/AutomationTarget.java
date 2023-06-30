package com.bitwig.dawproject.timeline;

import com.bitwig.dawproject.ExpressionType;
import com.bitwig.dawproject.Parameter;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlIDREF;

/**
 * <p>Defines the target of automation or expression, usually used within a Points element.</p>
 *
 * <p>Either it points directly ot a parameter or an expression, and in the expression case
 * it can either be monophonic (such as MIDI CCs) or per-note/polyphonic (such as poly pressure)</p>
 */

public class AutomationTarget
{
   /** Parameter to automate. */
   @XmlIDREF
   @XmlAttribute(required = false)
   public Parameter parameter;

   /** Expression type to control. */
   @XmlAttribute(required = false)
   public ExpressionType expression;

   /** MIDI channel */
   @XmlAttribute(required = false)
   public Integer channel;

   /** MIDI key. <p>Used when expression="polyPressure".</p> */
   @XmlAttribute(required = false)
   public Integer key;

   /** MIDI Channel Controller Number (0 based index). <p>Used when expression="channelController".</p> */
   @XmlAttribute(required = false)
   public Integer controller;
}
