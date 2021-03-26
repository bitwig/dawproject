package com.bitwig.dawproject.device;

import jakarta.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum EqBandType
{
   highPass,
   lowPass,
   bandPass,
   highShelf,
   lowShelf,
   bell,
   notch,
}
