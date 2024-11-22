#pragma once

#include "common.h"
#include "RealParameter.h"
#include "TimeSignatureParameter.h"

namespace DawProject
{
/* Transport element containing playback parameters such as Tempo and Time-signature. */
struct Transport
{
    /* Tempo parameter for setting and/or automating the tempo. */
    std::optional<RealParameter> tempo;
    std::optional<TimeSignatureParameter> timeSignature; // Time-signature parameter.
};
} // namespace DawProject

