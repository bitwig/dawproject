#pragma once

#include "common.h"
#include "Parameter.h"

namespace DawProject
{
/* Represents an enumerated parameter which can provide a value and be used as an automation target. */
struct IntegerParameter
    : public Parameter
{
    std::optional<int> value; // Integer value for this parameter.
    std::optional<int> min; // Minimum value this parameter can have (inclusive).
    std::optional<int> max; // Maximum value this parameter can have (inclusive).
};
} // namespace DawProject

