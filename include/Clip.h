#pragma once

#include "common.h"
#include "Nameable.h"
#include "Timeline.h"

namespace DawProject
{
struct Clip
    : public Nameable
{
    double time;
    std::optional<double> duration;
    std::optional<TimeUnit> contentTimeUnit;
    std::optional<double> playStart;
    std::optional<double> playStop;
    std::optional<double> loopStart;
    std::optional<double> loopEnd;
    std::optional<TimeUnit> fadeTimeUnit;
    std::optional<double> fadeInTime;
    std::optional<double> fadeOutTime;
    std::optional<Timeline> content;
    Timeline* reference;
};
} // namespace DawProject

