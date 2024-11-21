package com.bitwig.dawproject.device;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;

/** The role of a device. */
@XmlEnum
public enum DeviceRole {
	/** An instrument device. */
	@XmlEnumValue("instrument")
	INSTRUMENT,

	/** A note/MIDI effect device. */
	@XmlEnumValue("noteFX")
	NOTE_FX,

	/** An audio effect device. */
	@XmlEnumValue("audioFX")
	AUDIO_FX,

	/** An analyzer device. */
	@XmlEnumValue("analyzer")
	ANALYZER
}
