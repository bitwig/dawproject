package com.bitwig.dawproject.device;

import jakarta.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum EqBandType
{
   peak,
   lowCut,
   highCut,
   lowShelf,
   highShelf
}
