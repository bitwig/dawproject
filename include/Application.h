#pragma once

#include "common.h"

namespace DawProject
{
/* Metadata about the application which saved the DAWPROJECT file. */
struct Application
{
    std::string name; // Name of the application.
    std::string version; // Version number of the application.
};
} // namespace DawProject

