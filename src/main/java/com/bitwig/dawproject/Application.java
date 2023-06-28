package com.bitwig.dawproject;

import jakarta.xml.bind.annotation.XmlAttribute;

/** Metadata about the application which saved the DAWPROJECT file. */
public class Application
{
   /** Name of the application. */
   @XmlAttribute(required = true)
   public String name;

   /** Version number of the application. */
   @XmlAttribute(required = true)
   public String version;
}
