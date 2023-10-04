#pragma once

#include "common.h"
#include "BuiltinDevice.h"
#include "RealParameter.h"

namespace DawProject
{
struct NoiseGate
    : public BuiltinDevice
{
    std::optional<RealParameter> threshold;
    std::optional<RealParameter> ratio;
    std::optional<RealParameter> attack;
    std::optional<RealParameter> release;
    std::optional<RealParameter> range;
};
} // namespace DawProject

