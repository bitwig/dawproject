#pragma once

#include "common.h"
#include "Timeline.h"
#include "Clip.h"

namespace DawProject
{
struct ClipSlot
    : public Timeline
{
    std::optional<bool> hasStop;
    std::optional<Clip> clip;
};
} // namespace DawProject

