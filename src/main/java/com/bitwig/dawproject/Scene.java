package com.bitwig.dawproject;

import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlRootElement;

import com.bitwig.dawproject.timeline.Timeline;


/** Represents a clip launcher Scene of a DAW. */
@XmlRootElement(name = "Scene")
public class Scene extends Referenceable
{
    /**
     * Content timeline of this scene, will typically be structured like this:
     *
     * <pre>{@code
     * <Scene>
     *   <Lanes>
     *     <ClipSlot track="...">
     *        <Clip>
     *           ...
     *        </Clip>
     *     </ClipSlot>
     *      ...
     *   </Lanes>
     * </Scene>
     * }</pre>
     */
    @XmlElementRef(name = "Timeline")
    public Timeline content;
}
