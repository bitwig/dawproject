#pragma once

#include "common.h"
#include "Parameter.h"

namespace DawProject
{
/* Represents a parameter which can provide a boolean (true/false) value and be used as an automation target. */
struct BoolParameter
    : public Parameter
{
    std::optional<bool> value; // Boolean value for this parameter.
};
} // namespace DawProject

