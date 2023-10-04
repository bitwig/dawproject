#pragma once

#include "common.h"
#include "Timeline.h"
#include "Clip.h"

namespace DawProject
{
struct Clips
    : public Timeline
{
    std::vector<Clip> clips;
};
} // namespace DawProject

