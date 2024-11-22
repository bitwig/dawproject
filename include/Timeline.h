#pragma once

#include "common.h"

namespace DawProject
{
struct Track;

struct Timeline
{
    Track* track;
    std::optional<TimeUnit> timeUnit;
};
} // namespace DawProject

