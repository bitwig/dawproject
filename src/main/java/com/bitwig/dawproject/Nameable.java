package com.bitwig.dawproject;

import jakarta.xml.bind.annotation.XmlAttribute;

public abstract class Nameable
{
   @XmlAttribute
   public String name;

   @XmlAttribute
   public String color;

   @XmlAttribute
   public String comment;
}
