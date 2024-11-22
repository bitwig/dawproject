#pragma once

#include "common.h"

namespace DawProject
{
/* Metadata root element of the DAWPROJECT format. This is stored in the file metadata.xml file inside the container. */
struct MetaData
{
    std::optional<std::string> title; // Title of the song/project.
    std::optional<std::string> artist; // Recording Artist.
    std::optional<std::string> album; // Album.
    std::optional<std::string> originalArtist; // Original Artist.
    std::optional<std::string> composer; // Composer.
    std::optional<std::string> songwriter; // Songwriter.
    std::optional<std::string> producer; // Producer.
    std::optional<std::string> arranger; // Arranger.
    std::optional<std::string> year; // Year this project/song was recorded.
    std::optional<std::string> genre; // Genre/style
    std::optional<std::string> copyright; // Copyright notice.
    std::optional<std::string> website; // URL to website related to this project.
    std::optional<std::string> comment; // General comment or description.
};
} // namespace DawProject

