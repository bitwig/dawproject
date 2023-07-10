#pragma once

#include "common.h"
#include "Lane.h"
#include "Send.h"
#include "BoolParameter.h"
#include "RealParameter.h"
#include "Device.h"

namespace DawProject
{
/* Represents a mixer channel. It provides the ability to route signals to other channels and can contain
 Device/Plug-in for processing. */
struct Channel
    : public Lane
{
    std::optional<MixerRole> role; // Role of this channel in the mixer.
    /* Number of audio-channels of this mixer channel. (1=mono, 2=stereo…) */
    std::optional<int> audioChannels;
    std::optional<RealParameter> volume; // Channel volume
    std::optional<RealParameter> pan; // Channel pan/balance
    std::optional<BoolParameter> mute; // Channel mute
    std::optional<bool> solo; // Channel solo
    Channel* destination; // Output channel routing
    std::vector<Send> sends; // Send levels & destination
    std::vector<Device> devices; // Devices & plug-ins of this channel
};
} // namespace DawProject

