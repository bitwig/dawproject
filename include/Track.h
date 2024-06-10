#pragma once

#include "common.h"
#include "Lane.h"
#include "Channel.h"

namespace DawProject
{
/* Represents a sequencer track. */
struct Track
    : public Lane
{
    /* Role of this track in timelines & arranger. Can be multiple (comma-separated). */
    std::vector<ContentType> contentType;
    std::optional<bool> loaded; // If this track is loaded/active of not.
    std::optional<Channel> channel; // Mixer channel used for the output of this track.
    /* Child tracks, typically used to represent group/folder tracks with contentType="tracks". */
    std::vector<Track> tracks;
};
} // namespace DawProject

