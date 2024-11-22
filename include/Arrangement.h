#pragma once

#include "common.h"
#include "Lanes.h"
#include "Markers.h"
#include "Points.h"

namespace DawProject
{
/* Represents the main Arrangement timeline of a DAW. */
struct Arrangement
{
    /* Automation data for time-signature inside this Arrangement.
     <pre><code>
     &lt;Arrangement&gt;
       &lt;TimeSignatureAutomation target=&quot;id-of-TimeSignatureParameter&quot; ... &gt;
         &lt;TimeSignaturePoint time=&quot;0&quot; numerator=&quot;7&quot;, denominator=&quot;8&quot;/&gt;
         &lt;TimeSignaturePoint time=&quot;21&quot; numerator=&quot;4&quot;, denominator=&quot;4&quot;/&gt;
            ...
       &lt;/TimeSignatureAutomation&gt;
     &lt;/Arrangement&gt;
     </code></pre> */
    std::optional<Points> timeSignatureAutomation;
    /* Automation data for tempo inside this Arrangement, which will define the conversion between seconds and beats
     at the root level. */
    std::optional<Points> tempoAutomation;
    std::optional<Markers> markers; // Cue markers inside this arrangement
    /* The lanes of this arrangement. Generally this would contain another Lanes timeline for (and scoped to) each
     track which would then contain all Note, Audio, and Automation timelines. */
    std::optional<Lanes> lanes;
};
} // namespace DawProject

