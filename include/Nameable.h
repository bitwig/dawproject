#pragma once

#include "common.h"

namespace DawProject
{
struct Nameable
{
    std::optional<std::string> name; // Name/label of this object.
    /* Color of this object in HTML-style format. (#rrggbb) */
    std::optional<std::string> color;
    std::optional<std::string> comment; // Comment/description of this object.
};
} // namespace DawProject

