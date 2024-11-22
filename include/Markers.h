#pragma once

#include "common.h"
#include "Timeline.h"
#include "Marker.h"

namespace DawProject
{
struct Markers
    : public Timeline
{
    std::vector<Marker> markers;
};
} // namespace DawProject

