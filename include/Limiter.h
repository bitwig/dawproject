#pragma once

#include "common.h"
#include "BuiltinDevice.h"
#include "RealParameter.h"

namespace DawProject
{
struct Limiter
    : public BuiltinDevice
{
    std::optional<RealParameter> threshold;
    std::optional<RealParameter> inputGain;
    std::optional<RealParameter> outputGain;
    std::optional<RealParameter> attack;
    std::optional<RealParameter> release;
};
} // namespace DawProject

