#pragma once

#include "common.h"
#include "Parameter.h"

namespace DawProject
{
/* Represents a real valued (double) parameter which can provide a value and be used as an automation target. */
struct RealParameter
    : public Parameter
{
    /* Real (double) value for this parameter.
     <p>When serializing value to text for XML, infinite values are allowed and should be represented as inf and -inf. </p> */
    std::optional<double> value;
    /* Unit in which value, min and max are defined.
     <p>Using this rather than normalized value ranges allows transfer of parameter values and automation data.</p> */
    Unit unit;
    std::optional<double> min; // Minimum value this parameter can have (inclusive).
    std::optional<double> max; // Maximum value this parameter can have (inclusive).
};
} // namespace DawProject

