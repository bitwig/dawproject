package com.bitwig.dawproject.device;

import jakarta.xml.bind.annotation.XmlRootElement;


/**
 * A VST2 Plug-in instance.
 *
 * <p>
 * The VST2 plug-in state should be stored in FXB or FXP format.
 */
@XmlRootElement(name = "Vst2Plugin")
public class Vst2Plugin extends Plugin
{
    // Intentionally empty
}
