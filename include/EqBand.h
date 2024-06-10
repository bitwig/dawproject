#pragma once

#include "common.h"
#include "BoolParameter.h"
#include "RealParameter.h"

namespace DawProject
{
struct EqBand
{
    RealParameter freq;
    std::optional<RealParameter> gain;
    std::optional<RealParameter> Q;
    std::optional<BoolParameter> enabled;
    EqBandType type;
    std::optional<int> order;
};
} // namespace DawProject

