#pragma once

#include "common.h"
#include "Timeline.h"
#include "FileReference.h"

namespace DawProject
{
struct MediaFile
    : public Timeline
{
    FileReference file;
    double duration;
};
} // namespace DawProject

