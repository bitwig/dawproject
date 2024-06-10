#pragma once

#include "common.h"
#include "Timeline.h"

namespace DawProject
{
struct Lanes
    : public Timeline
{
    std::vector<Timeline> lanes;
};
} // namespace DawProject

