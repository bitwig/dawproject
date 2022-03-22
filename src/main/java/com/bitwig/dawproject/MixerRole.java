package com.bitwig.dawproject;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum MixerRole
{
   @XmlEnumValue("regular") regular,
   @XmlEnumValue("master") master,
   @XmlEnumValue("effect") effectTrack,
   @XmlEnumValue("bus") bus,
   @XmlEnumValue("vca") vca
}
