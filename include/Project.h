#pragma once

#include "common.h"
#include "Application.h"
#include "Transport.h"
#include "Lane.h"
#include "Arrangement.h"
#include "Scene.h"

namespace DawProject
{
/* The main root element of the DAWPROJECT format. This is stored in the file project.xml file inside the container. */
struct Project
{
    /* Version of DAWPROJECT format this file was saved as. */
    std::string version;
    /* Metadata (name/version) about the application that saved this file. */
    Application application;
    /* Transport element containing playback parameters such as Tempo and Time-signature. */
    std::optional<Transport> transport;
    std::vector<Lane> structure; // Track/Channel structure of this file.
    std::optional<Arrangement> arrangement; // The main Arrangement timeline of this file.
    std::vector<Scene> scenes; // Clip Launcher scenes of this file.
};
} // namespace DawProject

