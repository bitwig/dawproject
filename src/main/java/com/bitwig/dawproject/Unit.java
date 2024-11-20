package com.bitwig.dawproject;

import jakarta.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum Unit {
	linear, normalized, percent, decibel, hertz, semitones, seconds, beats, bpm,
}
