package com.bitwig.dawproject;

import jakarta.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum ContentType {
	audio, automation, notes, video, markers, tracks,
}
