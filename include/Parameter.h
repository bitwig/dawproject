#pragma once

#include "common.h"

namespace DawProject
{
/* Represents a parameter which can provide a value and be used as an automation target. */
struct Parameter
{
    /* Parameter ID as used by VST2 (index), VST3(ParamID) */
    std::optional<int> parameterID;
};
} // namespace DawProject

