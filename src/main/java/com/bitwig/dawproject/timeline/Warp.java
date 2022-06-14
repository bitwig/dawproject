package com.bitwig.dawproject.timeline;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Warp")
public class Warp
{
   /**
    * The time this point represent to the 'outside' of the Warps element.
    * The timeUnit is defined by the parent Warps element timeBase attribute
    * or inherited from the parent element of the Warps container
    */
   @XmlAttribute(required = true)
   public double time;

   /**
    * The time this point represent to the 'inside' of the Warps element.
    * The time-unit is defined by the parent Warps element contentTimeBase attribute
    */
   @XmlAttribute(required = true)
   public double contentTime;
}
