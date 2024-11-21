package com.bitwig.dawproject.device;

import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * A CLAP Plug-in instance.
 * <p>
 * The CLAP plug-in state should be stored in .clap-preset format.
 * </p>
 */
@XmlRootElement(name = "ClapPlugin")
public class ClapPlugin extends Plugin {
}
