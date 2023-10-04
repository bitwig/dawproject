#pragma once

#include "common.h"
#include "Device.h"

namespace DawProject
{
struct Plugin
    : public Device
{
    std::optional<std::string> pluginVersion;
};
} // namespace DawProject

