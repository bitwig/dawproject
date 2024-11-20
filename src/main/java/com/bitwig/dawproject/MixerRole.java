package com.bitwig.dawproject;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;


/** The role of a track or channel in the mixer. */
@XmlEnum
public enum MixerRole
{
    /** The 'default' role. */
    @XmlEnumValue("regular")
    REGULAR,
    /** E.g. the role of a master track. */
    @XmlEnumValue("master")
    MASTER,
    /** E.g. the role of an effect track. */
    @XmlEnumValue("effect")
    EFFECT_TRACK,
    /** E.g. the role of a group track. */
    @XmlEnumValue("submix")
    SUBMIX,
    /** The role of a VCA track. */
    @XmlEnumValue("vca")
    VCA
}
