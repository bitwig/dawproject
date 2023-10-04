#pragma once

#include "common.h"
#include "RealParameter.h"

namespace DawProject
{
struct Channel;

/* A single send of a mixer channel. */
struct Send
{
    RealParameter volume; // Send level.
    std::optional<RealParameter> pan; // Send pan/balance.
    std::optional<SendType> type; // Send type.
    Channel* destination; // Send destination.
};
} // namespace DawProject

