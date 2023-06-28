package com.bitwig.dawproject;

import jakarta.xml.bind.annotation.XmlAttribute;

public class FileReference
{
   /**
    * Relative path. either
    * 1) within the container
    * 2) relative to .dawproject file (when external = "true")
    * */
   @XmlAttribute(required = true)
   public String path;

   /** When true, the path is relative to the .dawproject file. Default value is false. */
   @XmlAttribute(required = false)
   public Boolean external;
}
