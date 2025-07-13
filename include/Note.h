#pragma once

#include "common.h"
#include "Timeline.h"

namespace DawProject
{
struct Note
{
    double time;
    double duration;
    std::optional<int> channel;
    int key;
    std::optional<double> velocity;
    std::optional<double> releaseVelocity;
    std::optional<Timeline> content;
};
} // namespace DawProject

