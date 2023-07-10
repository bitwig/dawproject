#pragma once

#include "common.h"
#include "MediaFile.h"

namespace DawProject
{
struct Audio
    : public MediaFile
{
    int sampleRate;
    int channels;
    std::optional<std::string> algorithm;
};
} // namespace DawProject

