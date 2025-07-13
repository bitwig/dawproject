#pragma once

#include "common.h"

namespace DawProject
{
/* References a file either within a DAWPROJECT container or on disk. */
struct FileReference
{
    /* File path. either
     <li>path within the container</li>
     <li>relative to .dawproject file (when external = "true")</li>
     <li>absolute path (when external = "true" and path starts with a slash or windows drive letter)</li> */
    std::string path;
    /* When true, the path is relative to the .dawproject file. Default value is false. */
    std::optional<bool> external;
};
} // namespace DawProject

