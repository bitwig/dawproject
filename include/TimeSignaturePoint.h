#pragma once

#include "common.h"
#include "Point.h"

namespace DawProject
{
struct TimeSignaturePoint
    : public Point
{
    int numerator;
    int denominator;
};
} // namespace DawProject

