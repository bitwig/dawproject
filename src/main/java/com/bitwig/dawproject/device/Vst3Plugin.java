package com.bitwig.dawproject.device;

import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * A VST3 Plug-in instance.
 * <p>
 * The VST3 plug-in state should be stored in .vstpreset format.
 * </p>
 */
@XmlRootElement(name = "Vst3Plugin")
public class Vst3Plugin extends Plugin {
}
