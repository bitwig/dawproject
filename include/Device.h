#pragma once

#include "common.h"
#include "FileReference.h"
#include "Parameter.h"
#include "BoolParameter.h"

namespace DawProject
{
struct Device
{
    std::optional<BoolParameter> enabled;
    DeviceRole deviceRole;
    std::optional<bool> loaded;
    std::string deviceName;
    std::optional<std::string> deviceID;
    std::optional<std::string> deviceVendor;
    std::optional<FileReference> state;
    std::vector<Parameter> automatedParameters;
};
} // namespace DawProject

