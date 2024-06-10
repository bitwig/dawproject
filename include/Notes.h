#pragma once

#include "common.h"
#include "Timeline.h"
#include "Note.h"

namespace DawProject
{
struct Notes
    : public Timeline
{
    std::vector<Note> notes;
};
} // namespace DawProject

