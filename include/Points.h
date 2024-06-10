#pragma once

#include "common.h"
#include "Timeline.h"
#include "AutomationTarget.h"
#include "Point.h"

namespace DawProject
{
struct Points
    : public Timeline
{
    AutomationTarget target;
    std::vector<Point> points;
    std::optional<Unit> unit;
};
} // namespace DawProject

