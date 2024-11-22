#pragma once

#include "common.h"
#include "MediaFile.h"

namespace DawProject
{
struct Video
    : public MediaFile
{
    std::optional<int> sampleRate;
    std::optional<int> channels;
    std::optional<std::string> algorithm;
};
} // namespace DawProject

