#pragma once

#include <optional>
#include <string>
#include <vector>

namespace DawProject
{
//constants
static constexpr std::string_view CURRENT_VERSION = "1.0";

//enums
enum TimeUnit: int
{
    TIME_UNIT_BEATS = 0,
    TIME_UNIT_SECONDS = 1,
};

enum Interpolation: int
{
    INTERPOLATION_HOLD = 0,
    INTERPOLATION_LINEAR = 1,
};

enum Unit: int
{
    UNIT_LINEAR = 0,
    UNIT_NORMALIZED = 1,
    UNIT_PERCENT = 2,
    UNIT_DECIBEL = 3,
    UNIT_HERTZ = 4,
    UNIT_SEMITONES = 5,
    UNIT_SECONDS = 6,
    UNIT_BEATS = 7,
    UNIT_BPM = 8,
};

enum SendType: int
{
    SEND_TYPE_PRE = 0,
    SEND_TYPE_POST = 1,
};

enum MixerRole: int
{
    MIXER_ROLE_REGULAR = 0,
    MIXER_ROLE_MASTER = 1,
    MIXER_ROLE_EFFECTTRACK = 2,
    MIXER_ROLE_SUBMIX = 3,
    MIXER_ROLE_VCA = 4,
};

enum DeviceRole: int
{
    DEVICE_ROLE_INSTRUMENT = 0,
    DEVICE_ROLE_NOTEFX = 1,
    DEVICE_ROLE_AUDIOFX = 2,
    DEVICE_ROLE_ANALYZER = 3,
};

enum ContentType: int
{
    CONTENT_TYPE_AUDIO = 0,
    CONTENT_TYPE_AUTOMATION = 1,
    CONTENT_TYPE_NOTES = 2,
    CONTENT_TYPE_VIDEO = 3,
    CONTENT_TYPE_MARKERS = 4,
    CONTENT_TYPE_TRACKS = 5,
};

enum ExpressionType: int
{
    EXPRESSION_TYPE_GAIN = 0,
    EXPRESSION_TYPE_PAN = 1,
    EXPRESSION_TYPE_TRANSPOSE = 2,
    EXPRESSION_TYPE_TIMBRE = 3,
    EXPRESSION_TYPE_FORMANT = 4,
    EXPRESSION_TYPE_PRESSURE = 5,
    EXPRESSION_TYPE_CHANNELCONTROLLER = 6,
    EXPRESSION_TYPE_CHANNELPRESSURE = 7,
    EXPRESSION_TYPE_POLYPRESSURE = 8,
    EXPRESSION_TYPE_PITCHBEND = 9,
    EXPRESSION_TYPE_PROGRAMCHANGE = 10,
};

enum EqBandType: int
{
    EQ_BAND_TYPE_HIGHPASS = 0,
    EQ_BAND_TYPE_LOWPASS = 1,
    EQ_BAND_TYPE_BANDPASS = 2,
    EQ_BAND_TYPE_HIGHSHELF = 3,
    EQ_BAND_TYPE_LOWSHELF = 4,
    EQ_BAND_TYPE_BELL = 5,
    EQ_BAND_TYPE_NOTCH = 6,
};
} // namespace DawProject

