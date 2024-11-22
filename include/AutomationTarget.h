#pragma once

#include "common.h"

namespace DawProject
{
struct Parameter;

struct AutomationTarget
{
    Parameter* parameter;
    std::optional<ExpressionType> expression;
    std::optional<int> channel;
    std::optional<int> key;
    std::optional<int> controller;
};
} // namespace DawProject

