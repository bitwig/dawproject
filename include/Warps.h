#pragma once

#include "common.h"
#include "Timeline.h"
#include "Warp.h"

namespace DawProject
{
struct Warps
    : public Timeline
{
    std::vector<Warp> events;
    Timeline content;
    TimeUnit contentTimeUnit;
};
} // namespace DawProject

