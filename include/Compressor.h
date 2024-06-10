#pragma once

#include "common.h"
#include "BuiltinDevice.h"
#include "BoolParameter.h"
#include "RealParameter.h"

namespace DawProject
{
struct Compressor
    : public BuiltinDevice
{
    std::optional<RealParameter> threshold;
    std::optional<RealParameter> ratio;
    std::optional<RealParameter> attack;
    std::optional<RealParameter> release;
    std::optional<RealParameter> inputGain;
    std::optional<RealParameter> outputGain;
    std::optional<BoolParameter> autoMakeup;
};
} // namespace DawProject

