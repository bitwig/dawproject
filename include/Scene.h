#pragma once

#include "common.h"
#include "Timeline.h"

namespace DawProject
{
/* Represents a clip launcher Scene of a DAW. */
struct Scene
{
    /* Content timeline of this scene, will typically be structured like this:
     <pre><code>
     &lt;Scene&gt;
       &lt;Lanes&gt;
         &lt;ClipSlot track=&quot;...&quot;&gt;
            &lt;Clip&gt;
               ...
            &lt;/Clip&gt;
         &lt;/ClipSlot&gt;
          ...
       &lt;/Lanes&gt;
     &lt;/Scene&gt;
     </code></pre> */
    Timeline content;
};
} // namespace DawProject

