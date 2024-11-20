package com.bitwig.dawproject.device;

import jakarta.xml.bind.annotation.XmlAttribute;


/** Abstract base class for all plug-in formats. */
public abstract class Plugin extends Device
{
    /** Version of the plug-in */
    @XmlAttribute
    public String pluginVersion;
}
