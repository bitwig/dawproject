#pragma once

#include "common.h"
#include "Parameter.h"

namespace DawProject
{
/* Represents a (the) time-signature parameter which can provide a value and be used as an automation target. */
struct TimeSignatureParameter
    : public Parameter
{
    /* Numerator of the time-signature. (3/4 &rarr; 3, 4/4 &rarr; 4) */
    int numerator;
    /* Denominator of the time-signature. (3/4 &rarr; 4, 7/8 &rarr; 8) */
    int denominator;
};
} // namespace DawProject

