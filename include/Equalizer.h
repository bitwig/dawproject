#pragma once

#include "common.h"
#include "BuiltinDevice.h"
#include "RealParameter.h"
#include "EqBand.h"

namespace DawProject
{
struct Equalizer
    : public BuiltinDevice
{
    std::vector<EqBand> bands;
    std::optional<RealParameter> inputGain;
    std::optional<RealParameter> outputGain;
};
} // namespace DawProject

