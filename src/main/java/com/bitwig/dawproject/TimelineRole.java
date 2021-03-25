package com.bitwig.dawproject;

import jakarta.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum TimelineRole
{
   audio,
   automation,
   notes,
   video,
   markers,
}
