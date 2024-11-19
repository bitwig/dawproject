package com.bitwig.dawproject;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;


/** An expression type. */
@XmlEnum
public enum ExpressionType
{
    /** The gain expression. */
    @XmlEnumValue("gain")
    GAIN,

    /** The panorama expression. */
    @XmlEnumValue("pan")
    PAN,

    /** The transpose expression. */
    @XmlEnumValue("transpose")
    TRANSPOSE,

    /** The timbre expression. */
    @XmlEnumValue("timbre")
    TIMBRE,

    /** The formant expression. */
    @XmlEnumValue("formant")
    FORMANT,

    /** The pressure expression. */
    @XmlEnumValue("pressure")
    PRESSURE,

    /** The MIDI channel controller expression. */
    @XmlEnumValue("channelController")
    CHANNEL_CONTROLLER,

    /** The MIDI channel pressure expression. */
    @XmlEnumValue("channelPressure")
    CHANNEL_PRESSURE,

    /** The MIDI poly-pressure expression. */
    @XmlEnumValue("polyPressure")
    POLY_PRESSURE,

    /** The MIDI pitch-bend expression. */
    @XmlEnumValue("pitchBend")
    PITCH_BEND,

    /** The MIDI program change expression. */
    @XmlEnumValue("programChange")
    PROGRAM_CHANGE
}
