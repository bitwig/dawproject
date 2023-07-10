#pragma once

#include "common.h"
#include "Parameter.h"

namespace DawProject
{
/* Represents an enumerated parameter which can provide a value and be used as an automation target. */
struct EnumParameter
    : public Parameter
{
    std::optional<int> value; // Index of the enum value.
    /* Number of entries in enum value. value will be in the range [0 .. count-1]. */
    int count;
    std::vector<std::string> labels; // Labels of the individual enum values.
};
} // namespace DawProject

