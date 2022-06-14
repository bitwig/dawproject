package com.bitwig.dawproject.timeline;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Warp")
public class Warp
{
   /**
    * The time this point represent to the 'outside' of the Warps element.
    * The TimeUnit is defined by the parent Warps element timeUnit attribute
    * or inherited from the parent element of the Warps container
    */
   @XmlAttribute(required = true)
   public double time;

   /**
    * The time this point represent to the 'inside' of the Warps element.
    * The TimeUnit is defined by the parent Warps element contentTimeUnit attribute
    */
   @XmlAttribute(required = true)
   public double contentTime;
}
