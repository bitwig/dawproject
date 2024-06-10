#pragma once

#include "common.h"
#include "Point.h"

namespace DawProject
{
struct RealPoint
    : public Point
{
    double value;
    std::optional<Interpolation> interpolation;
};
} // namespace DawProject

