package com.bitwig.dawproject.device;

import jakarta.xml.bind.annotation.XmlAttribute;

public abstract class Plugin extends Device
{
   /** Version of the plug-in */
   @XmlAttribute
   public String pluginVersion;
}
