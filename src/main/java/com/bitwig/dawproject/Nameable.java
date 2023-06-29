package com.bitwig.dawproject;

import jakarta.xml.bind.annotation.XmlAttribute;

public abstract class Nameable
{
   /** Name of this object */
   @XmlAttribute
   public String name;

   /** Color of this object in HTML-style format. (#rrggbb) */
   @XmlAttribute
   public String color;

   /** Comment describing this object */
   @XmlAttribute
   public String comment;
}
